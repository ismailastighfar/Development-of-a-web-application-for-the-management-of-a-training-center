package com.fst.trainingcenter.repositories;


import com.fst.trainingcenter.entities.Individual;
import com.fst.trainingcenter.security.repositories.AppUserRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IndividualRepository extends AppUserRepository<Individual>, JpaSpecificationExecutor<Individual> {
    Individual findIndividualById(Long id);
}
