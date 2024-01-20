package com.fst.trainingcenter.services;

import com.fst.trainingcenter.dtos.IndividualDTO;
import com.fst.trainingcenter.exceptions.IndividualAlreadyExistsException;
import com.fst.trainingcenter.exceptions.IndividualNotFoundException;

import java.util.List;

public interface IndividualService {

    List<IndividualDTO> getAllIndividuals();
    IndividualDTO getIndividual(Long id) throws IndividualNotFoundException;
    IndividualDTO createIndividual(IndividualDTO individualDTO) throws IndividualAlreadyExistsException;
    IndividualDTO updateIndividual(Long id,IndividualDTO individualDTO) throws IndividualNotFoundException;
    boolean deleteIndividual(Long id);

}
