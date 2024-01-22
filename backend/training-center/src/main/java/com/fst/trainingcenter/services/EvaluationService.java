package com.fst.trainingcenter.services;

import com.fst.trainingcenter.dtos.EvaluationDTO;
import com.fst.trainingcenter.exceptions.*;

import java.util.List;

public interface EvaluationService {

    List<EvaluationDTO> getAllEvaluations();
    EvaluationDTO getEvaluation(Long id) throws EvaluationNotFoundException;
    EvaluationDTO createEvaluation(EvaluationDTO evaluationDTO) throws TrainerNotFoundException, IndividualNotFoundException, TrainingNotFoundException, InvalidEvaluationException;
    EvaluationDTO updateEvaluation(Long id,EvaluationDTO evaluationDTO) throws EvaluationNotFoundException, TrainingNotFoundException, InvalidEvaluationException;
    void deleteEvaluation(Long id) throws EvaluationNotFoundException;


}
