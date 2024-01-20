package com.fst.trainingcenter.mappers;


import com.fst.trainingcenter.dtos.IndividualDTO;
import com.fst.trainingcenter.dtos.TrainerDTO;
import com.fst.trainingcenter.entities.Individual;
import com.fst.trainingcenter.entities.Trainer;
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
}
