package com.fst.trainingcenter.services.impl;

import com.fst.trainingcenter.dtos.IndividualDTO;
import com.fst.trainingcenter.dtos.TrainingDTO;
import com.fst.trainingcenter.entities.Company;
import com.fst.trainingcenter.entities.Individual;
import com.fst.trainingcenter.entities.Trainer;
import com.fst.trainingcenter.entities.Training;
import com.fst.trainingcenter.enums.Category;
import com.fst.trainingcenter.exceptions.*;
import com.fst.trainingcenter.mappers.MappersImpl;
import com.fst.trainingcenter.repositories.CompanyRepository;
import com.fst.trainingcenter.repositories.IndividualRepository;
import com.fst.trainingcenter.repositories.TrainerRepository;
import com.fst.trainingcenter.repositories.TrainingRepository;
import com.fst.trainingcenter.services.TrainingService;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class TrainingServiceImpl implements TrainingService {

    private TrainingRepository trainingRepository;
    private IndividualRepository individualRepository;
    private TrainerRepository trainerRepository;
    private CompanyRepository companyRepository;
    private MappersImpl mappers;

    @Override
    public List<TrainingDTO> getAllTrainings() {
        List<Training> trainings = trainingRepository.findAll();
        List<TrainingDTO> trainingDTOS = trainings.stream().
                map(training -> mappers.fromTraining(training)).
                toList();
        return  trainingDTOS;
    }

    @Override
    public TrainingDTO getTraining(Long id) throws TrainingNotFoundException {
        Training training = trainingRepository.findById(id).orElseThrow(
                () -> new TrainingNotFoundException("training not found id : " + id)
        );
        return mappers.fromTraining(training) ;
    }

    @Override
    public List<TrainingDTO> getTrainingsByIsCompany(boolean isCompany) {
        List<Training> trainings = trainingRepository.findByIsForCompany(isCompany);
        List<TrainingDTO> trainingDTOS = trainings.stream().
                map(training -> mappers.fromTraining(training)).
                toList();
        return  trainingDTOS;
    }

    @Override
    public List<IndividualDTO> getIndividualsByTraining(Long id) throws TrainingNotFoundException {
        Training training = trainingRepository.findById(id).orElseThrow(
                () -> new TrainingNotFoundException("training not found id : " + id)
        );
        List<Individual> individuals = training.getIndividuals();
        return individuals.stream()
                .map(individual -> mappers.fromIndividual(individual))
                .collect(Collectors.toList());
    }

    @Override
    public TrainingDTO createTraining(TrainingDTO trainingDTO) throws TrainingAlreadyExistsException {
        Training training = mappers.fromTrainingDTO(trainingDTO);
        Training existTraining = trainingRepository.findTrainingByTitle(training.getTitle());
        if (existTraining != null)
            throw new TrainingAlreadyExistsException("training already Exists : " + training.getTitle());
        training.setCode(UUID.randomUUID().toString());
        training.setCategory(trainingDTO.getCategory());
        Training trainingSaved = trainingRepository.save(training);
        return mappers.fromTraining(trainingSaved);
    }

    @Override
    public TrainingDTO updateTraining(Long id, TrainingDTO trainingDTO) throws TrainingNotFoundException {
        trainingRepository.findById(id).orElseThrow(
                () -> new TrainingNotFoundException("training not found id : " + id)
        );
        Training training = mappers.fromTrainingDTO(trainingDTO);
        training.setId(id);
        Training trainingSaved = trainingRepository.save(training);
        return mappers.fromTraining(trainingSaved);
    }

    @Override
    public boolean deleteTraining(Long id) throws TrainingNotFoundException {
        Training training = trainingRepository.findById(id).orElseThrow(
                () -> new TrainingNotFoundException("training not found id : " + id)
        );
        trainingRepository.delete(training);
        return true;
    }

    @Override
    public TrainingDTO enrollIndividual(Long idIndividual, Long idTraining) throws IndividualNotFoundException, TrainingNotFoundException, NoAvailableSeatsException, IndividualAlreadyEnrolledException, IndividualTrainingException {
        Individual individual = individualRepository.findById(idIndividual).orElseThrow(
                () -> new IndividualNotFoundException("individual not found with id : " + idIndividual)
        );
        Training training = trainingRepository.findById(idTraining).orElseThrow(
                () -> new TrainingNotFoundException("Training not found with id : "+ idTraining)
        );

        if (training.isForCompany()) {
            throw new IndividualTrainingException("Cannot enroll individual in a training intended for a company.");
        }
        if (training.getAvailableSeats() == 0) {
            throw new NoAvailableSeatsException("No available seats for training with id: " + idTraining);
        }
        if (training.getIndividuals().contains(individual)) {
            throw new IndividualAlreadyEnrolledException("Individual with id: " + idIndividual + " is already enrolled in training with id: " + idTraining);
        }

            training.getIndividuals().add(individual);
            individual.getTrainings().add(training);
            training.setAvailableSeats(training.getAvailableSeats()-1);

        return mappers.fromTraining(training);
    }

    @Override
    public TrainingDTO cancelEnrollment(Long idIndividual, Long idTraining) throws IndividualNotFoundException, TrainingNotFoundException, CancelEnrollmentException {
        Individual individual = individualRepository.findById(idIndividual).orElseThrow(
                () -> new IndividualNotFoundException("Individual not found with id: " + idIndividual)
        );
        Training training = trainingRepository.findById(idTraining).orElseThrow(
                () -> new TrainingNotFoundException("Training not found with id: " + idTraining)
        );

        if (training.isForCompany()) {
            throw new CancelEnrollmentException("Cannot cancel enrollment for an individual in a training intended for a company.");
        }

        if (!training.getIndividuals().contains(individual)) {
            throw new CancelEnrollmentException("Individual with id: " + idIndividual + " is not enrolled in training with id: " + idTraining);
        }

        training.getIndividuals().remove(individual);
        individual.getTrainings().remove(training);
        training.setAvailableSeats(training.getAvailableSeats() + 1);
        if (training.getTrainer() != null){
            if (training.getIndividuals().size() < 2) {
               training.setTrainer(null);
            }
        }
        return mappers.fromTraining(training);
    }

    public Page<TrainingDTO> searchTrainings(Category category, String city, String startDate, Pageable pageable) {
        Page<Training> trainingPage = trainingRepository.findAll((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (category != null) {
                predicates.add(builder.like(root.get("category"), "%" + category + "%"));
            }

            if (city != null && !city.isEmpty()) {
                predicates.add(builder.like(root.get("city"), "%" + city + "%"));
            }

            if (startDate != null && !startDate.isEmpty()) {
                LocalDate parsedDate = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                predicates.add(builder.like(root.get("startDate"), "%" + parsedDate + "%"));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        }, pageable);

        return trainingPage.map(training -> mappers.fromTraining(training));
    }

    @Override
    public TrainingDTO assignCompanyAndTrainer(Long trainingId, Long companyId, Long trainerId) throws TrainingNotFoundException, TrainerNotFoundException, CompanyNotFoundException, TrainingNotForCompanyException {
        Training training = trainingRepository.findById(trainingId).orElseThrow(
                () -> new TrainingNotFoundException("Training not found with id: " + trainingId)
        );

        if(!training.isForCompany())
            throw new TrainingNotForCompanyException("Training with id: " + trainingId + " is not intended for a company.");

        Trainer trainer = trainerRepository.findById(trainerId).orElseThrow(
                () -> new TrainerNotFoundException("Trainer not found with id: " + trainerId)
        );

        Company company = companyRepository.findById(companyId).orElseThrow(
                () -> new CompanyNotFoundException("Company not found with id: " + companyId)
        );

        training.setTrainer(trainer);
        training.setCompany(company);
        //trainer.getTrainings().add(training);
        //company.getTrainings().add(training);
        return mappers.fromTraining(training);
    }

    @Override
    public TrainingDTO assignTrainerToIndividuals(Long trainingId, Long trainerId) throws TrainingNotFoundException, TrainerNotFoundException, NotEnoughIndividualsException, TrainingNotForCompanyException {
        Training training = trainingRepository.findById(trainingId).orElseThrow(
                () -> new TrainingNotFoundException("Training not found with id: " + trainingId)
        );

        if(training.isForCompany())
            throw new TrainingNotForCompanyException("Training with id: " + trainingId + " is intended for a company.");


        Trainer trainer = trainerRepository.findById(trainerId).orElseThrow(
                () -> new TrainerNotFoundException("Trainer not found with id: " + trainerId)
        );

        List<Individual> individuals = training.getIndividuals();

        if (individuals.size() < 2) {
            throw new NotEnoughIndividualsException("individuals are required for the training assignment.");
        }

        training.setTrainer(trainer);
        //trainer.getTrainings().add(training);

        return mappers.fromTraining(training);
    }

}


