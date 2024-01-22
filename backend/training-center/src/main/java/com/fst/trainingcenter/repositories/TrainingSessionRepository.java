package com.fst.trainingcenter.repositories;

import com.fst.trainingcenter.entities.TrainingSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingSessionRepository extends JpaRepository<TrainingSession,Long> {
}
