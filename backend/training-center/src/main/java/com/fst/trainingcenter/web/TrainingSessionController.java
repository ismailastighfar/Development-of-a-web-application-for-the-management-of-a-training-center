package com.fst.trainingcenter.web;

import com.fst.trainingcenter.dtos.TrainingSessionDTO;
import com.fst.trainingcenter.exceptions.InvalidTrainingSessionException;
import com.fst.trainingcenter.exceptions.MaximumSessionsReachedException;
import com.fst.trainingcenter.exceptions.TrainingNotFoundException;
import com.fst.trainingcenter.exceptions.TrainingSessionNotFoundException;
import com.fst.trainingcenter.services.TrainingSessionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/trainingSession")
public class TrainingSessionController {

    private TrainingSessionService trainingSessionService;

    @GetMapping
    public ResponseEntity<List<TrainingSessionDTO>> getAllSessions(){
        return new ResponseEntity<>(
               trainingSessionService.getAllTrainingSessions(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainingSessionDTO> getSession(@PathVariable Long id) throws TrainingSessionNotFoundException {
        return new ResponseEntity<>(
                trainingSessionService.getTrainingSession(id),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}/training")
    public ResponseEntity<List<TrainingSessionDTO>> getAllTrainingSessionsForTraining(@PathVariable Long id) throws TrainingNotFoundException {
        return new ResponseEntity<>(
                trainingSessionService.getAllTrainingSessionsForTraining(id),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<TrainingSessionDTO> saveSession(@RequestBody TrainingSessionDTO trainingSessionDTO) throws MaximumSessionsReachedException, InvalidTrainingSessionException, TrainingNotFoundException {
        return new ResponseEntity<>(
                trainingSessionService.createTrainingSession(trainingSessionDTO),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrainingSessionDTO> updateSession(@PathVariable Long id,@RequestBody TrainingSessionDTO trainingSessionDTO) throws TrainingSessionNotFoundException, InvalidTrainingSessionException, TrainingNotFoundException, MaximumSessionsReachedException {
        return new ResponseEntity<>(
                trainingSessionService.updateTrainingSession(id,trainingSessionDTO),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSession(@PathVariable Long id) throws TrainingSessionNotFoundException {
        trainingSessionService.deleteTrainingSession(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
