package com.fst.trainingcenter.web;
;
import com.fst.trainingcenter.dtos.TrainerDTO;
import com.fst.trainingcenter.dtos.TrainerRequestDTO;
import com.fst.trainingcenter.exceptions.TrainerAlreadyExistsException;
import com.fst.trainingcenter.exceptions.TrainerNotFoundException;
import com.fst.trainingcenter.services.TrainerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class TrainerController {

    private TrainerService trainerService;

    @GetMapping("/trainers")
    public ResponseEntity<List<TrainerDTO>> getAllTrainers(){
        return new ResponseEntity<>(
                trainerService.getAllTrainers(),
                HttpStatus.OK
        );
    }

    @GetMapping("/trainers/{id}")
    public ResponseEntity<TrainerDTO> getTrainer(@PathVariable Long id) throws TrainerNotFoundException {
        return new ResponseEntity<>(
                trainerService.getTrainer(id),
                HttpStatus.OK
        ) ;
    }

    @PostMapping("/trainers")
    public ResponseEntity<TrainerDTO> saveTrainer(@RequestBody TrainerDTO trainerDTO) throws TrainerAlreadyExistsException {
        return new ResponseEntity<>(
                trainerService.createTrainer(trainerDTO),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/trainers/{id}")
    public ResponseEntity<TrainerDTO> updateTrainer(@PathVariable Long id,@RequestBody TrainerDTO trainerDTO) throws TrainerAlreadyExistsException, TrainerNotFoundException {
        return new ResponseEntity<>(
                trainerService.updateTrainer(id,trainerDTO),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/trainers/{id}")
    public ResponseEntity<Void> deleteTrainer(@PathVariable Long id) {
        boolean deleted = trainerService.deleteTrainer(id);
        if (!deleted)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/trainers/apply")
    public ResponseEntity<TrainerDTO> applyAsTrainer(@RequestBody TrainerRequestDTO trainerRequestDTO) throws TrainerAlreadyExistsException {
        return new ResponseEntity<>(
                trainerService.applyAsTrainer(trainerRequestDTO),
                HttpStatus.OK
        );
    }
}
