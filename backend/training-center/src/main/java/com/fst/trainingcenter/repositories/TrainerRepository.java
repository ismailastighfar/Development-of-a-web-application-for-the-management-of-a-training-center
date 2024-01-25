package com.fst.trainingcenter.repositories;


import com.fst.trainingcenter.entities.Trainer;
import com.fst.trainingcenter.security.repositories.AppUserRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TrainerRepository extends AppUserRepository<Trainer>, JpaSpecificationExecutor<Trainer> {
    Trainer findTrainerById(Long id);
    Trainer findTrainerByEmail(String email);
    List<Trainer> findByIsAccepted(boolean isAccepted);
}
