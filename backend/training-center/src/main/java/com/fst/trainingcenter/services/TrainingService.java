package com.fst.trainingcenter.services;

import com.fst.trainingcenter.dtos.TrainingDTO;
import com.fst.trainingcenter.entities.Training;
import com.fst.trainingcenter.enums.Category;
import com.fst.trainingcenter.exceptions.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.time.LocalDate;
import java.util.List;

public interface TrainingService {
    List<TrainingDTO> getAllTrainings();
    TrainingDTO getTraining(Long id) throws TrainingNotFoundException;
    List<TrainingDTO> getTrainingsByIsCompany(boolean isCompany);
    TrainingDTO createTraining(TrainingDTO trainingDTO ) throws TrainingAlreadyExistsException;
    TrainingDTO updateTraining(Long id,TrainingDTO trainingDTO) throws TrainingNotFoundException;
    boolean deleteTraining(Long id) throws TrainingNotFoundException;
    TrainingDTO enrollIndividual(Long idIndividual,Long idTraining) throws IndividualNotFoundException, TrainingNotFoundException, NoAvailableSeatsException, IndividualAlreadyEnrolledException, IndividualTrainingException;

    TrainingDTO cancelEnrollment(Long idIndividual, Long idTraining) throws IndividualNotFoundException, TrainingNotFoundException, CancelEnrollmentException;
    Page<TrainingDTO> searchTrainings(Category category, String city, String startDate, Pageable pageable);

    TrainingDTO assignCompanyAndTrainer(Long trainingId,Long companyId , Long trainerId) throws TrainingNotFoundException, TrainerNotFoundException, CompanyNotFoundException, TrainingNotForCompanyException;
    TrainingDTO assignTrainerToIndividuals(Long trainingId,Long trainerId) throws TrainingNotFoundException, TrainerNotFoundException, NotEnoughIndividualsException;
}
