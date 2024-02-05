package com.fst.trainingcenter.repositories;

import com.fst.trainingcenter.entities.Evaluation;
import com.fst.trainingcenter.entities.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvaluationRepository extends JpaRepository<Evaluation,Long> {

    List<Evaluation> findByTrainer(Trainer trainer);
}
