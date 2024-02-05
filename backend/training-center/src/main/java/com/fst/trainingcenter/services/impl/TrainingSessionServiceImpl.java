package com.fst.trainingcenter.services.impl;


import com.fst.trainingcenter.dtos.TrainingSessionDTO;
import com.fst.trainingcenter.entities.Individual;
import com.fst.trainingcenter.entities.Training;
import com.fst.trainingcenter.entities.TrainingSession;
import com.fst.trainingcenter.exceptions.InvalidTrainingSessionException;
import com.fst.trainingcenter.exceptions.MaximumSessionsReachedException;
import com.fst.trainingcenter.exceptions.TrainingNotFoundException;
import com.fst.trainingcenter.exceptions.TrainingSessionNotFoundException;
import com.fst.trainingcenter.mappers.MappersImpl;
import com.fst.trainingcenter.repositories.TrainingRepository;
import com.fst.trainingcenter.repositories.TrainingSessionRepository;
import com.fst.trainingcenter.services.EmailService;
import com.fst.trainingcenter.services.TrainingSessionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class TrainingSessionServiceImpl implements TrainingSessionService {

    private TrainingSessionRepository trainingSessionRepository;
    private TrainingRepository trainingRepository;
    private MappersImpl mappers;
    private EmailService emailService;

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

        LocalDate trainingStartDate = associatedTraining.getEndEnrollDate();
        if (trainingSession.getSessionDate().isBefore(trainingStartDate)) {
            throw new InvalidTrainingSessionException("Session date must be on or after the training start date.");
        }

        // Get the list of existing training sessions for the same training
        List<TrainingSession> existingSessions = associatedTraining.getTrainingSessions();

        LocalDate newSessionDate = trainingSessionDTO.getSessionDate();
        LocalTime newSessionStartTime = trainingSessionDTO.getSessionStartTime();
        LocalTime newSessionEndTime = trainingSessionDTO.getSessionEndTime();

        // Check for overlapping time intervals on the same date
        for (TrainingSession existingSession : existingSessions) {
            if (existingSession.getSessionDate().equals(newSessionDate)) {
                LocalTime existingStartTime = existingSession.getSessionStartTime();
                LocalTime existingEndTime = existingSession.getSessionEndTime();

                if (isOverlap(newSessionStartTime, newSessionEndTime, existingStartTime, existingEndTime)) {
                    throw new InvalidTrainingSessionException("Time is overlapping with an existing session for the same training on the same date.");
                }
            }
        }

        if (associatedTraining.getTrainingSessions().size() >= associatedTraining.getMaxSessions()) {
            throw new MaximumSessionsReachedException("Maximum number of sessions reached for training.");
        }

        TrainingSession trainingSessionSaved = trainingSessionRepository.save(trainingSession);
        associatedTraining.getTrainingSessions().add(trainingSessionSaved);

       List<Individual> individuals =  associatedTraining.getIndividuals();
        for (Individual individual : individuals) {
          emailService.sendEmailNewSession(individual,associatedTraining,trainingSessionSaved);
        }

        return mappers.fromTrainingSession(trainingSessionSaved);
    }

    // Helper method to check for overlapping time intervals
    private boolean isOverlap(LocalTime start1, LocalTime end1, LocalTime start2, LocalTime end2) {
        return start1.isBefore(end2) && end1.isAfter(start2);
    }

    @Override
    public TrainingSessionDTO updateTrainingSession(Long id, TrainingSessionDTO trainingSessionDTO) throws TrainingSessionNotFoundException, InvalidTrainingSessionException, TrainingNotFoundException, MaximumSessionsReachedException {
        TrainingSession existingSession = trainingSessionRepository.findById(id)
                .orElseThrow(() -> new TrainingSessionNotFoundException("TrainingSession not found with id: " + id));

        Training associatedTraining = trainingRepository.findById(trainingSessionDTO.getTrainingId()).orElseThrow(
                () -> new TrainingNotFoundException("training not found id :" + trainingSessionDTO.getTrainingId()));

        LocalDate trainingStartDate = associatedTraining.getEndEnrollDate();

        if (trainingSessionDTO.getSessionDate().isBefore(trainingStartDate)) {
            throw new InvalidTrainingSessionException("Session date must be on or after the training start date.");
        }
        Long idexistingTraining =  existingSession.getTraining().getId();

        if (idexistingTraining != associatedTraining.getId()){
            existingSession.getTraining().getTrainingSessions().remove(existingSession);
            existingSession.setTraining(associatedTraining);

            // Check if the time is already assigned to the same training at the same date
            LocalTime newSessionStartTime = trainingSessionDTO.getSessionStartTime();
            LocalTime newSessionEndTime = trainingSessionDTO.getSessionEndTime();

            for (TrainingSession otherSession : associatedTraining.getTrainingSessions()) {
                if (otherSession.getSessionDate().equals(trainingSessionDTO.getSessionDate()) &&
                        !otherSession.getId().equals(existingSession.getId()) &&
                        isOverlap(newSessionStartTime, newSessionEndTime, otherSession.getSessionStartTime(), otherSession.getSessionEndTime())) {
                    throw new InvalidTrainingSessionException("Time is already assigned to the same training at the same date.");
                }
            }

            if (associatedTraining.getTrainingSessions().size() >= associatedTraining.getMaxSessions()) {
                throw new MaximumSessionsReachedException("Maximum number of sessions reached for training.");
            }
            associatedTraining.getTrainingSessions().add(existingSession);
        }

        existingSession.setSessionDate(trainingSessionDTO.getSessionDate());
        existingSession.setSessionStartTime(trainingSessionDTO.getSessionStartTime());
        existingSession.setSessionEndTime(trainingSessionDTO.getSessionEndTime());
        existingSession.setDuration(trainingSessionDTO.getDuration());
        existingSession.setName(trainingSessionDTO.getName());
        existingSession.setDescription(trainingSessionDTO.getDescription());
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

    @Override
    public Optional<LocalDate> getLastSessionDay(Long trainingId) {
        List<TrainingSession> sessions = trainingSessionRepository.findByTrainingIdOrderBySessionDateDesc(trainingId);

        if (!sessions.isEmpty()) {
            return Optional.of(sessions.get(0).getSessionDate());
        }

        return Optional.empty();
    }
}
