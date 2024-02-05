package com.fst.trainingcenter.services.impl;

import com.fst.trainingcenter.dtos.EvaluationDTO;
import com.fst.trainingcenter.entities.Evaluation;
import com.fst.trainingcenter.entities.Individual;
import com.fst.trainingcenter.entities.Trainer;
import com.fst.trainingcenter.entities.Training;
import com.fst.trainingcenter.exceptions.*;
import com.fst.trainingcenter.mappers.MappersImpl;
import com.fst.trainingcenter.repositories.EvaluationRepository;
import com.fst.trainingcenter.repositories.IndividualRepository;
import com.fst.trainingcenter.repositories.TrainerRepository;
import com.fst.trainingcenter.repositories.TrainingRepository;
import com.fst.trainingcenter.services.EvaluationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class EvaluationServiceImpl implements EvaluationService {

    private EvaluationRepository evaluationRepository;
    private TrainerRepository trainerRepository;
    private IndividualRepository individualRepository;
    private TrainingRepository trainingRepository;
    private MappersImpl mappers;

    @Override
    public List<EvaluationDTO> getAllEvaluations() {
        List<Evaluation> evaluations = evaluationRepository.findAll();
        List<EvaluationDTO> evaluationDTOS = evaluations.stream().
                map(evaluation -> mappers.fromEvaluation(evaluation)).
                toList();
        return evaluationDTOS;
    }

    @Override
    public EvaluationDTO getEvaluation(Long id) throws EvaluationNotFoundException {
        Evaluation evaluation = evaluationRepository.findById(id).orElseThrow(
                () -> new EvaluationNotFoundException("evaluation not found with id :  " + id)
        );
        return mappers.fromEvaluation(evaluation);
    }

    @Override
    public EvaluationDTO createEvaluation(EvaluationDTO evaluationDTO) throws TrainerNotFoundException, IndividualNotFoundException, TrainingNotFoundException, InvalidEvaluationException {
        Training training = trainingRepository.findByCode(evaluationDTO.getCode()).orElseThrow(
                () -> new TrainingNotFoundException("Training not found for the provided code: " + evaluationDTO.getCode())
        );

        Trainer trainer =  trainerRepository.findById(evaluationDTO.getTrainerId()).orElseThrow(
                () -> new TrainerNotFoundException("Trainer  With Id = `"+ evaluationDTO.getTrainerId()+ "` Does Not Exist!")
        );
        Individual individual = individualRepository.findById(evaluationDTO.getIndividualId()).orElseThrow(
                () -> new IndividualNotFoundException("individual not found id : " + evaluationDTO.getIndividualId())
        );

        // Check if the individual attended the specified training
        if (!training.getIndividuals().contains(individual)) {
            throw new InvalidEvaluationException("Individual did not attend the specified training.");
        }

        // Check if the trainer was associated with the specified training
        if (!training.getTrainer().equals(trainer)) {
            throw new InvalidEvaluationException("Trainer was not associated with the specified training.");
        }

        Evaluation evaluation = mappers.fromEvaluationDTO(evaluationDTO);
        evaluation.setTrainer(trainer);
        evaluation.setIndividual(individual);
        Evaluation evaluationSaved = evaluationRepository.save(evaluation);
        return mappers.fromEvaluation(evaluationSaved);
    }

    @Override
    public EvaluationDTO updateEvaluation(Long id, EvaluationDTO evaluationDTO) throws EvaluationNotFoundException, TrainingNotFoundException, InvalidEvaluationException {
        Evaluation existingEvaluation = evaluationRepository.findById(id)
                .orElseThrow(() -> new EvaluationNotFoundException("Evaluation not found with id: " + id));
        // Check if the provided code matches the training code
        Training training = trainingRepository.findByCode(evaluationDTO.getCode())
                .orElseThrow(() -> new TrainingNotFoundException("Training not found for the provided code: " + evaluationDTO.getCode()));

        // Check if the individual attended the specified training
        if (!training.getIndividuals().contains(existingEvaluation.getIndividual())) {
            throw new InvalidEvaluationException("Individual did not attend the specified training.");
        }

        // Check if the trainer was associated with the specified training
        if (!training.getTrainer().equals(existingEvaluation.getTrainer())) {
            throw new InvalidEvaluationException("Trainer was not associated with the specified training.");
        }


        existingEvaluation.setPedagogicalQuality(evaluationDTO.getPedagogicalQuality());
        existingEvaluation.setPace(evaluationDTO.getPace());
        existingEvaluation.setCourseSupport(evaluationDTO.getCourseSupport());
        existingEvaluation.setSubjectMastery(evaluationDTO.getSubjectMastery());

        return mappers.fromEvaluation(existingEvaluation);
    }

    @Override
    public void deleteEvaluation(Long id) throws EvaluationNotFoundException {
        Evaluation evaluation = evaluationRepository.findById(id)
                .orElseThrow(() -> new EvaluationNotFoundException("Evaluation not found with id: " + id));

        evaluationRepository.delete(evaluation);
    }
}
