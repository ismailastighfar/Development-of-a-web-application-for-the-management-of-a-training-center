package com.fst.trainingcenter.services;

import com.fst.trainingcenter.entities.Individual;
import com.fst.trainingcenter.entities.Training;
import com.fst.trainingcenter.entities.TrainingSession;
import com.fst.trainingcenter.exceptions.IndividualNotFoundException;
import com.fst.trainingcenter.exceptions.TrainingNotFoundException;

public interface EmailService {

   void sendEmailNewIndividual(Long id) throws IndividualNotFoundException;
   void senEmailApplyTrainer(Long id) throws TrainingNotFoundException;
   void sendEmailRefuseTrainer(Long id) throws TrainingNotFoundException;
   void sendEmailAcceptTrainer(Long id,String password) throws TrainingNotFoundException;
   void sendPasswordResetEmail(String userEmail, String token) throws IndividualNotFoundException;
   void sendEmailNewSession(Individual individual , Training training , TrainingSession trainingSession);
   void sendEmailAfterLastSession(String email,Training training);
}
