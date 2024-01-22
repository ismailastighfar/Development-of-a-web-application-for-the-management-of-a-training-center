package com.fst.trainingcenter.mappers;


import com.fst.trainingcenter.dtos.*;
import com.fst.trainingcenter.entities.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MappersImpl {

    public TrainerDTO fromTrainer(Trainer trainer){
        TrainerDTO trainerDTO = new TrainerDTO();
        BeanUtils.copyProperties(trainer,trainerDTO);
        return  trainerDTO;
    }
    public Trainer fromTrainerDTO(TrainerDTO trainerDTO){
        Trainer trainer = new Trainer();
        BeanUtils.copyProperties(trainerDTO,trainer);
        return  trainer;
    }

    public TrainingSessionDTO fromTrainingSession(TrainingSession trainingSession){
        TrainingSessionDTO trainingSessionDTO = new TrainingSessionDTO();
        BeanUtils.copyProperties(trainingSession,trainingSessionDTO);
        trainingSessionDTO.setTrainingId(trainingSession.getTraining().getId());
        return  trainingSessionDTO;
    }
    public TrainingSession fromTrainingSessionDTO(TrainingSessionDTO trainingSessionDTO){
        TrainingSession trainingSession = new TrainingSession();
        BeanUtils.copyProperties(trainingSessionDTO,trainingSession);
        return  trainingSession;
    }

    public IndividualDTO fromIndividual(Individual individual){
        IndividualDTO individualDTO = new IndividualDTO();
        BeanUtils.copyProperties(individual,individualDTO);
        return  individualDTO;
    }
    public Individual fromIndividualDTO(IndividualDTO individualDTO){
       Individual individual = new Individual();
        BeanUtils.copyProperties(individualDTO,individual);
        return  individual;
    }

    public Company fromCompanyDTO(CompanyDTO companyDTO){
        Company company = new Company();
        BeanUtils.copyProperties(companyDTO,company);
        return  company;
    }

    public CompanyDTO fromCompany(Company company){
        CompanyDTO companyDTO = new CompanyDTO();
        BeanUtils.copyProperties(company,companyDTO);
        return  companyDTO;
    }

    public Training fromTrainingDTO(TrainingDTO trainingDTO){
        Training training = new Training();
        BeanUtils.copyProperties(trainingDTO,training);
        if (trainingDTO.getIndividuals() != null) {
            List<Individual> individuals = trainingDTO.getIndividuals()
                    .stream()
                    .map(this::fromIndividualDTO)
                    .collect(Collectors.toList());
            training.setIndividuals(individuals);
        }
        if(trainingDTO.getCompany() != null)
          training.setCompany(fromCompanyDTO(trainingDTO.getCompany()));
        if (trainingDTO.getTrainer() != null)
           training.setTrainer(fromTrainerDTO(trainingDTO.getTrainer()));
        return training;
    }

    public TrainingDTO fromTraining(Training training){
        TrainingDTO trainingDTO = new TrainingDTO();
        BeanUtils.copyProperties(training,trainingDTO);
        if (training.getIndividuals() != null) {
            List<IndividualDTO> individualDTOs = training.getIndividuals()
                    .stream()
                    .map(this::fromIndividual)
                    .collect(Collectors.toList());
            trainingDTO.setIndividuals(individualDTOs);
        }
        if(training.getCompany() != null)
          trainingDTO.setCompany(fromCompany(training.getCompany()));
        if(training.getTrainer() != null)
           trainingDTO.setTrainer(fromTrainer(training.getTrainer()));
        return trainingDTO;
    }

    public EvaluationDTO fromEvaluation(Evaluation evaluation){
        EvaluationDTO evaluationDTO = new EvaluationDTO();
        BeanUtils.copyProperties(evaluation,evaluationDTO);
        evaluationDTO.setIndividualId(evaluation.getIndividual().getId());
        evaluationDTO.setTrainerId(evaluation.getTrainer().getId());
       return evaluationDTO;
    }

    public Evaluation fromEvaluationDTO(EvaluationDTO evaluationDTO){
        Evaluation evaluation = new Evaluation();
        BeanUtils.copyProperties(evaluationDTO,evaluation);
        return evaluation;
    }







}
