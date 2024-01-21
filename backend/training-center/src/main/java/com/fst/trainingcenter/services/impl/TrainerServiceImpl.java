package com.fst.trainingcenter.services.impl;

import com.fst.trainingcenter.dtos.TrainerDTO;
import com.fst.trainingcenter.dtos.TrainerRequestDTO;
import com.fst.trainingcenter.entities.Trainer;
import com.fst.trainingcenter.exceptions.TrainerAlreadyExistsException;
import com.fst.trainingcenter.exceptions.TrainerNotFoundException;
import com.fst.trainingcenter.mappers.MappersImpl;
import com.fst.trainingcenter.repositories.TrainerRepository;
import com.fst.trainingcenter.security.entities.AppRole;
import com.fst.trainingcenter.security.entities.AppUser;
import com.fst.trainingcenter.security.services.ISecurityService;
import com.fst.trainingcenter.services.TrainerService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TrainerServiceImpl implements TrainerService {

    private TrainerRepository trainerRepository;
    private ISecurityService securityService;
    private PasswordEncoder passwordEncoder;
    private MappersImpl mappers;

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
        Trainer trainer =  trainerRepository.findById(id).orElseThrow(
                () -> new TrainerNotFoundException("Trainer  With Id = `"+id+ "` Does Not Exist!")
        );
        return mappers.fromTrainer(trainer);
    }

    @Override
    public TrainerDTO createTrainer(TrainerDTO trainerDTO) throws TrainerAlreadyExistsException {
        Trainer trainer = mappers.fromTrainerDTO(trainerDTO);
        AppUser user = securityService.findUserByEmail(trainer.getEmail());
        if (user != null)
          throw new TrainerAlreadyExistsException("Trainer  With Id = `"+trainer.getId()+ "` Already Exists!");
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
        securityService.addRoleToUser(role.getRoleName(),savedTrainer.getEmail());
        return mappers.fromTrainer(savedTrainer);
    }

    @Override
    public TrainerDTO updateTrainer(Long id, TrainerDTO trainerDTO) throws TrainerNotFoundException {
        Trainer trainer = mappers.fromTrainerDTO(trainerDTO);
        Trainer existTrainer = trainerRepository.findTrainerById(id);
        if (existTrainer == null)
            throw new TrainerNotFoundException("trainer with id "+ trainer.getId() + "not found");
        existTrainer.setNom(trainer.getNom());
        existTrainer.setSurname(trainer.getSurname());
        existTrainer.setEmail(trainer.getEmail());
        existTrainer.setPhone(trainer.getPhone());
        String password = trainer.getPassword();
        existTrainer.setPassword(passwordEncoder.encode(password));
        existTrainer.setKeywords(trainer.getKeywords());
        existTrainer.setDescription(trainer.getDescription());
        Trainer savedTrainer = trainerRepository.save(existTrainer);
        return mappers.fromTrainer(savedTrainer);
    }

    @Override
    public boolean deleteTrainer(Long id) {
        Trainer trainer = trainerRepository.findTrainerById(id);
        if (trainer == null)
            return  false;
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
        return  mappers.fromTrainer(trainersaved);
    }
}
