package com.fst.trainingcenter.services;

import com.fst.trainingcenter.dtos.TrainingSessionDTO;
import com.fst.trainingcenter.entities.TrainingSession;
import com.fst.trainingcenter.exceptions.InvalidTrainingSessionException;
import com.fst.trainingcenter.exceptions.MaximumSessionsReachedException;
import com.fst.trainingcenter.exceptions.TrainingNotFoundException;
import com.fst.trainingcenter.exceptions.TrainingSessionNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TrainingSessionService {

    List<TrainingSessionDTO> getAllTrainingSessions();
    TrainingSessionDTO getTrainingSession(Long id) throws TrainingSessionNotFoundException;
    List<TrainingSessionDTO> getAllTrainingSessionsForTraining(Long trainingId) throws TrainingNotFoundException;
    TrainingSessionDTO createTrainingSession(TrainingSessionDTO trainingSession) throws InvalidTrainingSessionException, MaximumSessionsReachedException, TrainingNotFoundException;
    TrainingSessionDTO updateTrainingSession(Long id , TrainingSessionDTO trainingSession) throws TrainingSessionNotFoundException, InvalidTrainingSessionException, TrainingNotFoundException, MaximumSessionsReachedException;
    void deleteTrainingSession(Long id) throws TrainingSessionNotFoundException;
}
