package com.fst.trainingcenter.services;

import com.fst.trainingcenter.dtos.CompanyDTO;
import com.fst.trainingcenter.dtos.TrainingDTO;
import com.fst.trainingcenter.exceptions.*;

import java.util.List;

public interface TrainingService {

    List<TrainingDTO> getAllTrainings();
    TrainingDTO getTraining(Long id) throws TrainingNotFoundException;
    TrainingDTO createTraining(TrainingDTO trainingDTO ) throws TrainingAlreadyExistsException, TrainerNotFoundException,CompanyNotFoundException;
    TrainingDTO updateTraining(Long id,TrainingDTO trainingDTO) throws TrainingNotFoundException, TrainerNotFoundException, CompanyNotFoundException;
    boolean deleteTraining(Long id) throws  TrainingNotFoundException;
}
