package com.fst.trainingcenter.mappers;


import com.fst.trainingcenter.dtos.*;
import com.fst.trainingcenter.entities.*;
import com.fst.trainingcenter.security.entities.AppUser;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


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

    public AssistantDTO fromAssistant(AppUser assistant){
        AssistantDTO assistantDTO = new AssistantDTO();
        BeanUtils.copyProperties(assistant,assistantDTO);
        return  assistantDTO;
    }
    public AppUser fromAssistantDTO(AssistantDTO assistantDTO){
        AppUser user = new AppUser();
        BeanUtils.copyProperties(assistantDTO,user);
        return  user;
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
        return training;
    }

    public TrainingDTO fromTraining(Training training){
        TrainingDTO trainingDTO = new TrainingDTO();
        BeanUtils.copyProperties(training,trainingDTO);
        if(training.getCompany() != null)
          trainingDTO.setCompanyId(training.getCompany().getId());
        if(training.getTrainer() != null)
           trainingDTO.setTrainerId(training.getTrainer().getId());
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
