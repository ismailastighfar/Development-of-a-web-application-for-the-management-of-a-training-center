package com.fst.trainingcenter.repositories;

import com.fst.trainingcenter.entities.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvaluationRepository extends JpaRepository<Evaluation,Long> {
}
