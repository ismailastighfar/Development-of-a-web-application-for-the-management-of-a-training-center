package com.fst.trainingcenter.repositories;


import com.fst.trainingcenter.entities.Individual;
import com.fst.trainingcenter.security.repositories.AppUserRepository;

public interface IndividualRepository extends AppUserRepository<Individual> {
    Individual findIndividualById(Long id);
}
