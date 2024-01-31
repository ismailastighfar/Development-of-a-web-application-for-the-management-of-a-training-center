package com.fst.trainingcenter.services.impl;

import com.fst.trainingcenter.dtos.TrainerDTO;
import com.fst.trainingcenter.dtos.TrainerRequestDTO;
import com.fst.trainingcenter.dtos.TrainingDTO;
import com.fst.trainingcenter.entities.Individual;
import com.fst.trainingcenter.entities.Trainer;
import com.fst.trainingcenter.entities.Training;
import com.fst.trainingcenter.exceptions.TrainerAlreadyExistsException;
import com.fst.trainingcenter.exceptions.TrainerNotFoundException;
import com.fst.trainingcenter.exceptions.TrainingNotFoundException;
import com.fst.trainingcenter.mappers.MappersImpl;
import com.fst.trainingcenter.repositories.TrainerRepository;
import com.fst.trainingcenter.security.entities.AppRole;
import com.fst.trainingcenter.security.entities.AppUser;
import com.fst.trainingcenter.security.services.ISecurityService;
import com.fst.trainingcenter.services.EmailService;
import com.fst.trainingcenter.services.TrainerService;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.fst.trainingcenter.services.impl.TrainerServiceImpl.PasswordGenerator.generatePassword;

@Service
@Transactional
@AllArgsConstructor
public class TrainerServiceImpl implements TrainerService {

    private TrainerRepository trainerRepository;
    private ISecurityService securityService;
    private PasswordEncoder passwordEncoder;
    private MappersImpl mappers;
    private EmailService emailService;

    @Override
    public List<TrainerDTO> getAllTrainers() {
        List<Trainer> trainers = trainerRepository.findAll();
        List<TrainerDTO> trainerDTO = trainers.stream().
                map(trainer -> mappers.fromTrainer(trainer)).
                toList();
        return trainerDTO;
    }

    @Override
    public TrainerDTO getTrainer(Long id) throws TrainerNotFoundException {
        Trainer trainer = trainerRepository.findById(id).orElseThrow(
                () -> new TrainerNotFoundException("Trainer  With Id = `" + id + "` Does Not Exist!")
        );
        return mappers.fromTrainer(trainer);
    }

    public List<TrainingDTO> getTrainingsForTrainer(Long trainerId) throws TrainerNotFoundException {
        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new TrainerNotFoundException("Trainer not found with id: " + trainerId));

        List<Training> trainings = trainer.getTrainings();

        return trainings.stream()
                .map(training -> mappers.fromTraining(training))
                .collect(Collectors.toList());
    }


    @Override
    public Page<TrainerDTO> searchTrainers(String name, String email, String keywords, Pageable pageable) {
        Page<Trainer> trainerPage = trainerRepository.findAll((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null && !name.isEmpty()) {
                predicates.add(builder.like(root.get("name"), "%" + name + "%"));
            }

            if (email != null && !email.isEmpty()) {
                predicates.add(builder.like(root.get("email"), "%" + email + "%"));
            }

            if (keywords != null && !keywords.isEmpty()) {
                predicates.add(builder.like(root.get("keywords"), "%" + keywords + "%"));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        }, pageable);

        return trainerPage.map(trainer -> mappers.fromTrainer(trainer));
    }

    @Override
    public List<TrainerDTO> getTrainerByAccepted(boolean accepted) {
        List<Trainer> trainers = trainerRepository.findByIsAccepted(accepted);
        List<TrainerDTO> trainerDTO = trainers.stream().
                map(trainer -> mappers.fromTrainer(trainer)).
                toList();
        return trainerDTO;
    }

    @Override
    public TrainerDTO createTrainer(TrainerDTO trainerDTO) throws TrainerAlreadyExistsException {
        Trainer trainer = mappers.fromTrainerDTO(trainerDTO);
        AppUser user = securityService.findUserByEmail(trainer.getEmail());
        if (user != null)
            throw new TrainerAlreadyExistsException("Trainer  With Id = `" + user.getId() + "` Already Exists!");
        Trainer newTrainer = new Trainer();
        newTrainer.setNom(trainer.getNom());
        newTrainer.setSurname(trainer.getSurname());
        newTrainer.setEmail(trainer.getEmail());
        newTrainer.setPhone(trainer.getPhone());
        String password = trainer.getPassword();
        newTrainer.setPassword(passwordEncoder.encode(password));
        newTrainer.setKeywords(trainer.getKeywords());
        newTrainer.setDescription(trainer.getDescription());
        newTrainer.setAccepted(true);
        Trainer savedTrainer = trainerRepository.save(newTrainer);
        AppRole role = securityService.findRoleByRoleName("TRAINER");
        securityService.addRoleToUser(role.getRoleName(), savedTrainer.getEmail());
        return mappers.fromTrainer(savedTrainer);
    }

    @Override
    public TrainerDTO updateTrainer(Long id, TrainerDTO trainerDTO) throws TrainerNotFoundException {
        Trainer trainer = mappers.fromTrainerDTO(trainerDTO);
        Trainer existTrainer = trainerRepository.findTrainerById(id);
        if (existTrainer == null)
            throw new TrainerNotFoundException("trainer with id " + trainer.getId() + "not found");
        existTrainer.setNom(trainer.getNom());
        existTrainer.setSurname(trainer.getSurname());
        existTrainer.setEmail(trainer.getEmail());
        existTrainer.setPhone(trainer.getPhone());
        existTrainer.setKeywords(trainer.getKeywords());
        existTrainer.setDescription(trainer.getDescription());
        return mappers.fromTrainer(existTrainer);
    }

    @Override
    public boolean deleteTrainer(Long id) {
        Trainer trainer = trainerRepository.findTrainerById(id);
        if (trainer == null)
            return false;
        trainerRepository.delete(trainer);
        return true;
    }

    @Override
    public TrainerDTO applyAsTrainer(TrainerRequestDTO trainerRequestDTO) throws TrainerAlreadyExistsException {
        Trainer trainerExist = trainerRepository.findTrainerByEmail(trainerRequestDTO.getEmail());
        if (trainerExist != null)
            throw new TrainerAlreadyExistsException("trainer already exists !!");
        Trainer trainer = new Trainer();
        trainer.setAccepted(false);
        trainer.setNom(trainerRequestDTO.getNom());
        trainer.setSurname(trainerRequestDTO.getSurname());
        trainer.setPhone(trainerRequestDTO.getPhone());
        trainer.setEmail(trainerRequestDTO.getEmail());
        trainer.setKeywords(trainerRequestDTO.getKeywords());
        trainer.setDescription(trainerRequestDTO.getDescription());
        Trainer trainersaved = trainerRepository.save(trainer);
        try {
            emailService.senEmailApplyTrainer(trainersaved.getId());
        } catch (TrainingNotFoundException e) {
            throw new RuntimeException(e);
        }
        return mappers.fromTrainer(trainersaved);
    }

    @Override
    public TrainerDTO acceptTrainer(Long trainerId) throws TrainerNotFoundException, TrainerAlreadyExistsException {
        Trainer trainer = trainerRepository.findById(trainerId).orElseThrow(
                () -> new TrainerNotFoundException("trainer with id " + trainerId + " not found")
        );
        if (trainer.isAccepted())
            throw new TrainerAlreadyExistsException("trainer with id " + trainer.getId() + " already accepted");
        trainer.setAccepted(true);
        String password = generatePassword();
        trainer.setPassword(passwordEncoder.encode(password));
        try {
            emailService.sendEmailAcceptTrainer(trainer.getId(), password);
        } catch (TrainingNotFoundException e) {
            throw new RuntimeException(e);
        }
        AppRole role = securityService.findRoleByRoleName("TRAINER");
        securityService.addRoleToUser(role.getRoleName(), trainer.getEmail());
        return mappers.fromTrainer(trainer);
    }

    @Override
    public boolean refuseTrainer(Long trainerId) throws TrainerNotFoundException, TrainerAlreadyExistsException {
        Trainer trainer = trainerRepository.findById(trainerId).orElseThrow(
                () -> new TrainerNotFoundException("trainer with id : " + trainerId + " not found")
        );
        if (trainer.isAccepted())
            throw new TrainerAlreadyExistsException("trainer with id : " + trainer.getId() + " already accepted");
        try {
            emailService.sendEmailRefuseTrainer(trainer.getId());
        } catch (TrainingNotFoundException e) {
            throw new RuntimeException(e);
        }

        return deleteTrainer(trainer.getId());
    }


    public class PasswordGenerator {

        // Define the password pattern
        private static final String PASSWORD_PATTERN =
                "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";

        // Characters to be used in the generated password
        private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789@#$%^&+=!";

        public static String generatePassword() {
            StringBuilder password = new StringBuilder();
            SecureRandom random = new SecureRandom();

            // Generate characters until the password matches the pattern
            while (!password.toString().matches(PASSWORD_PATTERN)) {
                password.setLength(0); // Clear the previous attempt

                // Generate a random password
                for (int i = 0; i < 12; i++) {
                    int index = random.nextInt(CHARACTERS.length());
                    password.append(CHARACTERS.charAt(index));
                }
            }

            return password.toString();
        }
    }

}
