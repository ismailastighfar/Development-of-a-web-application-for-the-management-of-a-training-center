package com.fst.trainingcenter.services;



import com.fst.trainingcenter.dtos.IndividualDTO;
import com.fst.trainingcenter.dtos.TrainerDTO;
import com.fst.trainingcenter.dtos.TrainerRequestDTO;
import com.fst.trainingcenter.dtos.TrainingDTO;
import com.fst.trainingcenter.exceptions.TrainerAlreadyExistsException;
import com.fst.trainingcenter.exceptions.TrainerNotFoundException;
import com.fst.trainingcenter.exceptions.TrainingNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TrainerService {
    List<TrainerDTO> getAllTrainers();
    TrainerDTO getTrainer(Long id) throws TrainerNotFoundException;
    Page<TrainerDTO> searchTrainers(String name, String email,String keywords, Pageable pageable);
    List<TrainerDTO> getTrainerByAccepted(boolean accepted);
    List<TrainingDTO> getTrainingsForTrainer(Long trainerId) throws TrainerNotFoundException;
    TrainerDTO createTrainer(TrainerDTO trainer) throws TrainerAlreadyExistsException;
    TrainerDTO updateTrainer(Long id , TrainerDTO trainer) throws TrainerNotFoundException, TrainerAlreadyExistsException;
    boolean deleteTrainer(Long id);
    TrainerDTO applyAsTrainer(TrainerRequestDTO trainerRequestDTO) throws TrainerAlreadyExistsException;
    TrainerDTO acceptTrainer(Long trainerId) throws TrainerNotFoundException, TrainerAlreadyExistsException;
    boolean refuseTrainer(Long trainerId) throws TrainerNotFoundException, TrainerAlreadyExistsException, TrainingNotFoundException;

}
