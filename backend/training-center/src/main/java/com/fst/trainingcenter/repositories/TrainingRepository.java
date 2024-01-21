package com.fst.trainingcenter.repositories;

import com.fst.trainingcenter.entities.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingRepository extends JpaRepository<Training,Long> {
    boolean existsTrainingByTitle(String title);

}
