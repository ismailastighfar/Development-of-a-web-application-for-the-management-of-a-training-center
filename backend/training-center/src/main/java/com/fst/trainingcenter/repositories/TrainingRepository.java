package com.fst.trainingcenter.repositories;

import com.fst.trainingcenter.entities.Trainer;
import com.fst.trainingcenter.entities.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainingRepository extends JpaRepository<Training,Long>, JpaSpecificationExecutor<Training> {

    Training findTrainingByTitle(String title);
    Optional<Training> findByCode(String code);
    List<Training> findByIsForCompany(boolean isForCompany);

}
