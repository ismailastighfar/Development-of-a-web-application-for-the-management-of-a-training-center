package com.fst.trainingcenter.web;


import com.fst.trainingcenter.dtos.IndividualDTO;
import com.fst.trainingcenter.dtos.TrainingDTO;
import com.fst.trainingcenter.enums.Category;
import com.fst.trainingcenter.exceptions.*;
import com.fst.trainingcenter.services.TrainingService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/trainings")
public class TrainingController {

    private TrainingService trainingService;

    @GetMapping
    public ResponseEntity<List<TrainingDTO>> getTrainings(){
        return new ResponseEntity<>(
                trainingService.getAllTrainings(),
                HttpStatus.OK
        );

    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainingDTO> getTraining(@PathVariable Long id) throws TrainingNotFoundException {
        return new ResponseEntity<>(
                trainingService.getTraining(id),
                HttpStatus.OK
                );
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getCategories() {
       List<Category> categories = Arrays.asList(Category.values());

        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/isCompany/{isCompany}")
    public ResponseEntity<List<TrainingDTO>> getTrainingsByIsCompany(@PathVariable boolean isCompany) {
        return new ResponseEntity<>(
                trainingService.getTrainingsByIsCompany(isCompany),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}/individuals")
    public ResponseEntity<List<IndividualDTO>> getIndividuals(@PathVariable Long id) throws TrainingNotFoundException {
        return new ResponseEntity<>(
                trainingService.getIndividualsByTraining(id),
                HttpStatus.OK);
    }



    @PostMapping
    public ResponseEntity<TrainingDTO> saveTraining(@RequestBody TrainingDTO trainingDTO) throws TrainingAlreadyExistsException {
        return new ResponseEntity<>(
                trainingService.createTraining(trainingDTO),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrainingDTO> updateTraining(@PathVariable Long id,@RequestBody TrainingDTO trainingDTO) throws TrainingNotFoundException {
        return new ResponseEntity<>(
                trainingService.updateTraining(id, trainingDTO),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTraining(@PathVariable Long id) throws TrainingNotFoundException {
       trainingService.deleteTraining(id);
       return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/individual/{individualId}/enroll/{trainingId}")
    public ResponseEntity<TrainingDTO> enrollIndividual(@PathVariable(name = "individualId") Long individualId
            , @PathVariable(name = "trainingId") Long trainingId ) throws IndividualNotFoundException, TrainingNotFoundException, NoAvailableSeatsException, IndividualAlreadyEnrolledException, IndividualTrainingException {
        return new ResponseEntity<>(
                trainingService.enrollIndividual(individualId,trainingId),
                HttpStatus.OK
        );
    }

    @PostMapping("/individual/{individualId}/enroll/cancel/{trainingId}")
    public ResponseEntity<TrainingDTO> cancelEnrollIndividual(@PathVariable(name = "individualId") Long individualId
            , @PathVariable(name = "trainingId") Long trainingId ) throws CancelEnrollmentException, IndividualNotFoundException, TrainingNotFoundException {
        return new ResponseEntity<>(
                trainingService.cancelEnrollment(individualId,trainingId),
                HttpStatus.OK
        );
    }

    @GetMapping("/search")
    public ResponseEntity<Page<TrainingDTO>> searchTrainings(
            @RequestParam(required = false) Category category,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String endEnrollDate,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<TrainingDTO> result = trainingService.searchTrainings(category, city, endEnrollDate, pageable);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/{trainingId}/assign/company/{companyId}/trainer/{trainerId}")
    public ResponseEntity<TrainingDTO> assignTrainerAndCompanyToTraining(
            @PathVariable Long trainingId,
            @PathVariable Long companyId,
            @PathVariable Long trainerId) throws TrainingNotForCompanyException, CompanyNotFoundException, TrainingNotFoundException, TrainerNotFoundException {
        TrainingDTO result = trainingService.assignCompanyAndTrainer(trainingId, companyId, trainerId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/{trainingId}/assign/trainer/{trainerId}/individuals")
    public ResponseEntity<TrainingDTO> assignTrainerToIndividuals(
            @PathVariable Long trainingId,
            @PathVariable Long trainerId) throws NotEnoughIndividualsException, TrainingNotFoundException, TrainerNotFoundException, TrainingNotForCompanyException {
        TrainingDTO result = trainingService.assignTrainerToIndividuals(trainingId, trainerId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
