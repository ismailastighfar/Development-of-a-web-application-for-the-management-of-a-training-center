package com.fst.trainingcenter.services.impl;

import com.fst.trainingcenter.dtos.CompanyDTO;
import com.fst.trainingcenter.dtos.TrainingDTO;
import com.fst.trainingcenter.entities.Company;
import com.fst.trainingcenter.entities.Training;
import com.fst.trainingcenter.exceptions.*;
import com.fst.trainingcenter.mappers.MappersImpl;
import com.fst.trainingcenter.repositories.CompanyRepository;
import com.fst.trainingcenter.repositories.TrainingRepository;
import com.fst.trainingcenter.services.CompanyService;
import com.fst.trainingcenter.services.TrainerService;
import com.fst.trainingcenter.services.TrainingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class TrainingServiceImpl implements TrainingService {

    private TrainingRepository trainingRepository;
    private MappersImpl mappers;
    private TrainerService trainerService;
    private CompanyService companyService;
    @Override
    public List<TrainingDTO> getAllTrainings() {

        List <Training> trainingList=trainingRepository.findAll();
        List<TrainingDTO> trainingDTOList=trainingList.stream().
                map(
                        training -> mappers.fromTraining(training)

                ).toList();

        return trainingDTOList;
    }

    @Override
    public TrainingDTO getTraining(Long id) throws TrainingNotFoundException {
        return null;
    }

    @Override
    public TrainingDTO createTraining(TrainingDTO trainingDTO) throws TrainingAlreadyExistsException, TrainerNotFoundException,CompanyNotFoundException {
        Training training= mappers.fromTrainingDTO(trainingDTO);
        if(trainingRepository.existsTrainingByTitle(training.getTitle()))
            throw new TrainingAlreadyExistsException("Company with title `" + training.getTitle() + "` already exists!");
        //check if trainer exists or else throw exception
        if(trainingDTO.getTrainer()!=null){
            trainerService.getTrainer(trainingDTO.getTrainer().getId());
        }
        //check if company exists or else throw exception
        if(trainingDTO.getCompany()!=null){
            companyService.getCompany(trainingDTO.getCompany().getId());
        }

        Training savedTraining=trainingRepository.save(training);
        return mappers.fromTraining(savedTraining);
    }

    @Override
    public TrainingDTO updateTraining(Long id, TrainingDTO trainingDTO) throws TrainingNotFoundException, TrainerNotFoundException, CompanyNotFoundException {
        trainingRepository.findById(id).orElseThrow(
                ()->new TrainingNotFoundException("Company Not Found With Id = "+id)
        );
        Training training = mappers.fromTrainingDTO(trainingDTO);

        //check if trainer exists or else throw exception
        if(training.getTrainer()!=null){
            trainerService.getTrainer(training.getTrainer().getId());
        }
        //check if company exists or else throw exception
        if(trainingDTO.getCompany()!=null){
            companyService.getCompany(training.getCompany().getId());
        }
        training.setId(id);



        Training savedTraining=trainingRepository.save(training);
        return mappers.fromTraining(savedTraining);
    }

    @Override
    public boolean deleteTraining(Long id) throws TrainingNotFoundException {
        trainingRepository.findById(id).orElseThrow(
                ()->new TrainingNotFoundException("Training Not Found With Id = !"+id)
        );

        trainingRepository.deleteById(id);
        return true;
    }
}
