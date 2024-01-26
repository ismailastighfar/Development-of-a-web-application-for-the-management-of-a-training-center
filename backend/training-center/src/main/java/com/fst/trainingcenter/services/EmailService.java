package com.fst.trainingcenter.services;

import com.fst.trainingcenter.exceptions.IndividualNotFoundException;
import com.fst.trainingcenter.exceptions.TrainingNotFoundException;

public interface EmailService {

   void sendEmailNewIndividual(Long id) throws IndividualNotFoundException;

   void senEmailApplyTrainer(Long id) throws TrainingNotFoundException;
   void sendEmailRefuseTrainer(Long id) throws TrainingNotFoundException;
   void sendEmailAcceptTrainer(Long id,String password) throws TrainingNotFoundException;
}
