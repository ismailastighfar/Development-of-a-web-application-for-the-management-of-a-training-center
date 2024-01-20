package com.fst.trainingcenter.services;



import com.fst.trainingcenter.dtos.TrainerDTO;
import com.fst.trainingcenter.dtos.TrainerRequestDTO;
import com.fst.trainingcenter.exceptions.TrainerAlreadyExistsException;
import com.fst.trainingcenter.exceptions.TrainerNotFoundException;

import java.util.List;

public interface TrainerService {
    List<TrainerDTO> getAllTrainers();
    TrainerDTO getTrainer(Long id) throws TrainerNotFoundException;
    TrainerDTO createTrainer(TrainerDTO trainer) throws TrainerAlreadyExistsException;
    TrainerDTO updateTrainer(Long id , TrainerDTO trainer) throws TrainerNotFoundException, TrainerAlreadyExistsException;
    boolean deleteTrainer(Long id);

    TrainerDTO applyAsTrainer(TrainerRequestDTO trainerRequestDTO) throws TrainerAlreadyExistsException;

}
