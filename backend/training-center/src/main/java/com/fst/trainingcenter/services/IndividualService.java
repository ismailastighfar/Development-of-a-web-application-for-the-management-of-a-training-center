package com.fst.trainingcenter.services;

import com.fst.trainingcenter.dtos.CompanyDTO;
import com.fst.trainingcenter.dtos.IndividualDTO;
import com.fst.trainingcenter.dtos.TrainingDTO;
import com.fst.trainingcenter.exceptions.IndividualAlreadyExistsException;
import com.fst.trainingcenter.exceptions.IndividualNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IndividualService {

    List<IndividualDTO> getAllIndividuals();
    IndividualDTO getIndividual(Long id) throws IndividualNotFoundException;
    List<TrainingDTO> getTrainingsForIndividual(Long individualId) throws IndividualNotFoundException;
    Page<IndividualDTO> searchIndividuals(String name, String email, Pageable pageable);
    IndividualDTO createIndividual(IndividualDTO individualDTO) throws IndividualAlreadyExistsException, IndividualNotFoundException;
    IndividualDTO updateIndividual(Long id,IndividualDTO individualDTO) throws IndividualNotFoundException;
    boolean deleteIndividual(Long id);


}
