package com.fst.trainingcenter.repositories;


import com.fst.trainingcenter.entities.Trainer;
import com.fst.trainingcenter.security.repositories.AppUserRepository;

public interface TrainerRepository extends AppUserRepository<Trainer> {
    Trainer findTrainerById(Long id);
    Trainer findTrainerByEmail(String email);
}
