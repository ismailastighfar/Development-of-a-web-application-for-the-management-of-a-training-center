package com.fst.trainingcenter.web;

import com.fst.trainingcenter.dtos.EvaluationDTO;
import com.fst.trainingcenter.dtos.TrainingSessionDTO;
import com.fst.trainingcenter.exceptions.*;
import com.fst.trainingcenter.services.EvaluationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/evaluations")
public class EvaluationController {

    private EvaluationService evaluationService;

    @GetMapping
    public ResponseEntity<List<EvaluationDTO>> getAllEvaluations(){
        return new ResponseEntity<>(
                evaluationService.getAllEvaluations(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<EvaluationDTO> getEvaluation(@PathVariable Long id) throws EvaluationNotFoundException {
        return new ResponseEntity<>(
                evaluationService.getEvaluation(id),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<EvaluationDTO> saveEvaluation(@RequestBody EvaluationDTO evaluationDTO) throws IndividualNotFoundException, TrainingNotFoundException, TrainerNotFoundException, InvalidEvaluationException {
        return new ResponseEntity<>(
                evaluationService.createEvaluation(evaluationDTO),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<EvaluationDTO> updateEvaluation(@PathVariable Long id,@RequestBody EvaluationDTO evaluationDTO) throws TrainingNotFoundException, EvaluationNotFoundException, InvalidEvaluationException {
        return new ResponseEntity<>(
                evaluationService.updateEvaluation(id,evaluationDTO),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvaluation(@PathVariable Long id) throws EvaluationNotFoundException {
        evaluationService.deleteEvaluation(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
