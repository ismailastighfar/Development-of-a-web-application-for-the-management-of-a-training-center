package com.fst.trainingcenter.repositories;

import com.fst.trainingcenter.entities.Training;
import com.fst.trainingcenter.entities.TrainingSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainingSessionRepository extends JpaRepository<TrainingSession,Long> {
    List<TrainingSession> findByTrainingIdOrderBySessionDateDesc(Long trainingId);
}
