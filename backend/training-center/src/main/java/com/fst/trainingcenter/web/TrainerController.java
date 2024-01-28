package com.fst.trainingcenter.web;

import com.fst.trainingcenter.dtos.TrainerDTO;
import com.fst.trainingcenter.dtos.TrainerRequestDTO;
import com.fst.trainingcenter.dtos.TrainingDTO;
import com.fst.trainingcenter.exceptions.TrainerAlreadyExistsException;
import com.fst.trainingcenter.exceptions.TrainerNotFoundException;
import com.fst.trainingcenter.exceptions.TrainingNotFoundException;
import com.fst.trainingcenter.services.TrainerService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping("/trainers/{id}/trainings")
    public ResponseEntity<List<TrainingDTO>> getATrainerTrainings(@PathVariable Long id) throws TrainerNotFoundException {
        return new ResponseEntity<>(
                trainerService.getTrainingsForTrainer(id),
                HttpStatus.OK
        );
    }

    @GetMapping("/trainers/search")
    public ResponseEntity<Page<TrainerDTO>> searchTrainers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String keywords,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<TrainerDTO> result = trainerService.searchTrainers(name, email,keywords, pageable);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/trainers/{id}")
    public ResponseEntity<TrainerDTO> getTrainer(@PathVariable Long id) throws TrainerNotFoundException {
        return new ResponseEntity<>(
                trainerService.getTrainer(id),
                HttpStatus.OK
        ) ;
    }

    @GetMapping("/trainers/isAccepted/{accepted}")
    public ResponseEntity<List<TrainerDTO>> getTrainerByAccepted(@PathVariable boolean accepted){
        return new ResponseEntity<>(
                trainerService.getTrainerByAccepted(accepted),
                HttpStatus.OK
        );
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

    @PostMapping("/trainers/accept/{id}")
    public ResponseEntity<TrainerDTO> acceptTrainer(@PathVariable Long id) throws TrainerNotFoundException, TrainerAlreadyExistsException {
        return new ResponseEntity<>(
                trainerService.acceptTrainer(id),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/trainers/refuse/{id}")
    public ResponseEntity<Void> refuseTrainer(@PathVariable Long id) throws TrainerNotFoundException, TrainerAlreadyExistsException, TrainingNotFoundException {
        boolean deleted = trainerService.refuseTrainer(id);
        if (!deleted)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
