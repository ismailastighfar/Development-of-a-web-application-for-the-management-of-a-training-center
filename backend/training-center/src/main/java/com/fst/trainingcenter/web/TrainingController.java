package com.fst.trainingcenter.web;

import com.fst.trainingcenter.dtos.CompanyDTO;
import com.fst.trainingcenter.dtos.TrainingDTO;
import com.fst.trainingcenter.exceptions.*;
import com.fst.trainingcenter.services.TrainingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/trainings")
public class TrainingController {
    private TrainingService trainingService;
    @GetMapping
    public ResponseEntity<List<TrainingDTO>> getAllTrainings(){
        return new ResponseEntity<>(
                trainingService.getAllTrainings(),
                HttpStatus.OK
        );
    }

    @PostMapping()
    public ResponseEntity<TrainingDTO> saveTraining(@RequestBody TrainingDTO  trainingDTO) throws TrainingAlreadyExistsException, CompanyNotFoundException, TrainerNotFoundException {
        return new ResponseEntity<>(
                trainingService.createTraining(trainingDTO),
                HttpStatus.CREATED
        );
    }

    @PutMapping("{id}")
    public ResponseEntity<TrainingDTO> updateTraining(@PathVariable Long id,@RequestBody TrainingDTO trainigDTO) throws TrainingNotFoundException, CompanyNotFoundException, TrainerNotFoundException {
        return new ResponseEntity<>(
                trainingService.updateTraining(id,trainigDTO),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable Long id) throws TrainingNotFoundException {
        trainingService.deleteTraining(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
