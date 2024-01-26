package com.fst.trainingcenter.services.impl;


import com.fst.trainingcenter.dtos.TrainingSessionDTO;
import com.fst.trainingcenter.entities.Training;
import com.fst.trainingcenter.entities.TrainingSession;
import com.fst.trainingcenter.exceptions.InvalidTrainingSessionException;
import com.fst.trainingcenter.exceptions.MaximumSessionsReachedException;
import com.fst.trainingcenter.exceptions.TrainingNotFoundException;
import com.fst.trainingcenter.exceptions.TrainingSessionNotFoundException;
import com.fst.trainingcenter.mappers.MappersImpl;
import com.fst.trainingcenter.repositories.TrainingRepository;
import com.fst.trainingcenter.repositories.TrainingSessionRepository;
import com.fst.trainingcenter.services.TrainingSessionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class TrainingSessionServiceImpl implements TrainingSessionService {

    private TrainingSessionRepository trainingSessionRepository;
    private TrainingRepository trainingRepository;
    private MappersImpl mappers;

    @Override
    public List<TrainingSessionDTO> getAllTrainingSessions() {
        List<TrainingSession> trainingSessions = trainingSessionRepository.findAll();
        List<TrainingSessionDTO> trainingSessionDTOS = trainingSessions.stream().
                map(trainingSession -> mappers.fromTrainingSession(trainingSession)).
                toList();
        return trainingSessionDTOS;
    }

    @Override
    public TrainingSessionDTO getTrainingSession(Long id) throws TrainingSessionNotFoundException {
        TrainingSession trainingSession = trainingSessionRepository.findById(id).orElseThrow(
                () -> new TrainingSessionNotFoundException("trainingSession with id : " + id + "not found")
        );
        return mappers.fromTrainingSession(trainingSession);
    }

    @Override
    public List<TrainingSessionDTO> getAllTrainingSessionsForTraining(Long trainingId) throws TrainingNotFoundException {
        Training training = trainingRepository.findById(trainingId).orElseThrow(
                () -> new TrainingNotFoundException("Training not found with id: " + trainingId));

        List<TrainingSession> trainingSessions = training.getTrainingSessions();
        return trainingSessions.stream()
                .map(mappers::fromTrainingSession)
                .collect(Collectors.toList());
    }


    @Override
    public TrainingSessionDTO createTrainingSession(TrainingSessionDTO trainingSessionDTO) throws InvalidTrainingSessionException, MaximumSessionsReachedException, TrainingNotFoundException {
        TrainingSession trainingSession = mappers.fromTrainingSessionDTO(trainingSessionDTO);

        Training associatedTraining = trainingRepository.findById(trainingSessionDTO.getTrainingId()).orElseThrow(
                () -> new TrainingNotFoundException("training not found id :" + trainingSessionDTO.getTrainingId()));
        trainingSession.setTraining(associatedTraining);

        LocalDate trainingStartDate = associatedTraining.getStartDate();
        if (trainingSession.getSessionDate().isBefore(trainingStartDate)) {
            throw new InvalidTrainingSessionException("Session date must be on or after the training start date.");
        }

        // Check if the time is already assigned to the same training at the same date
        LocalTime newSessionTime = trainingSession.getSessionTime();
        for (TrainingSession existingSession : associatedTraining.getTrainingSessions()) {
            if (existingSession.getSessionDate().equals(trainingSession.getSessionDate()) &&
                    existingSession.getSessionTime().equals(newSessionTime)) {
                throw new InvalidTrainingSessionException("Time is already assigned to the same training at the same date.");
            }
        }

        if (associatedTraining.getTrainingSessions().size() >= associatedTraining.getMaxSessions()) {
            throw new MaximumSessionsReachedException("Maximum number of sessions reached for training.");
        }

        TrainingSession trainingSessionSaved = trainingSessionRepository.save(trainingSession);
        associatedTraining.getTrainingSessions().add(trainingSessionSaved);
        return mappers.fromTrainingSession(trainingSessionSaved);
    }

    @Override
    public TrainingSessionDTO updateTrainingSession(Long id, TrainingSessionDTO trainingSessionDTO) throws TrainingSessionNotFoundException, InvalidTrainingSessionException, TrainingNotFoundException, MaximumSessionsReachedException {
        TrainingSession existingSession = trainingSessionRepository.findById(id)
                .orElseThrow(() -> new TrainingSessionNotFoundException("TrainingSession not found with id: " + id));

        Training associatedTraining = trainingRepository.findById(trainingSessionDTO.getTrainingId()).orElseThrow(
                () -> new TrainingNotFoundException("training not found id :" + trainingSessionDTO.getTrainingId()));

        LocalDate trainingStartDate = associatedTraining.getStartDate();

        if (trainingSessionDTO.getSessionDate().isBefore(trainingStartDate)) {
            throw new InvalidTrainingSessionException("Session date must be on or after the training start date.");
        }
        Long idexistingTraining =  existingSession.getTraining().getId();

        if (idexistingTraining != associatedTraining.getId()){
            existingSession.getTraining().getTrainingSessions().remove(existingSession);
            existingSession.setTraining(associatedTraining);
            // Check if the time is already assigned to the same training at the same date
            LocalTime newSessionTime = trainingSessionDTO.getSessionTime();
            for (TrainingSession otherSession : associatedTraining.getTrainingSessions()) {
                if (otherSession.getSessionDate().equals(trainingSessionDTO.getSessionDate()) &&
                        otherSession.getSessionTime().equals(newSessionTime) &&
                        !otherSession.getId().equals(existingSession.getId())) {
                    throw new InvalidTrainingSessionException("Time is already assigned to the same training at the same date.");
                }
            }
            if (associatedTraining.getTrainingSessions().size() >= associatedTraining.getMaxSessions()) {
                throw new MaximumSessionsReachedException("Maximum number of sessions reached for training.");
            }
            associatedTraining.getTrainingSessions().add(existingSession);
        }

        existingSession.setSessionDate(trainingSessionDTO.getSessionDate());
        existingSession.setSessionTime(trainingSessionDTO.getSessionTime());
        existingSession.setDuration(trainingSessionDTO.getDuration());

        TrainingSession updatedSession = trainingSessionRepository.save(existingSession);

        return mappers.fromTrainingSession(updatedSession);
    }

    @Override
    public void deleteTrainingSession(Long id) throws TrainingSessionNotFoundException {
        TrainingSession trainingSession = trainingSessionRepository.findById(id).orElseThrow(
                () -> new TrainingSessionNotFoundException("trainingSession with id : " + id + "not found")
        );
        trainingSessionRepository.delete(trainingSession);
    }
}
