package com.fst.trainingcenter.services.impl;

import com.fst.trainingcenter.dtos.IndividualDTO;
import com.fst.trainingcenter.dtos.TrainerDTO;
import com.fst.trainingcenter.entities.Individual;
import com.fst.trainingcenter.entities.Trainer;
import com.fst.trainingcenter.exceptions.IndividualAlreadyExistsException;
import com.fst.trainingcenter.exceptions.IndividualNotFoundException;
import com.fst.trainingcenter.mappers.MappersImpl;
import com.fst.trainingcenter.repositories.IndividualRepository;
import com.fst.trainingcenter.security.entities.AppRole;
import com.fst.trainingcenter.security.entities.AppUser;
import com.fst.trainingcenter.security.services.ISecurityService;
import com.fst.trainingcenter.services.IndividualService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class IndividualServiceImpl implements IndividualService {

    private IndividualRepository individualRepository;
    private ISecurityService securityService;
    private PasswordEncoder passwordEncoder;
    private MappersImpl mappers;
    @Override
    public List<IndividualDTO> getAllIndividuals() {
        List<Individual> individuals = individualRepository.findAll();
        List<IndividualDTO> individualDTO = individuals.stream().
                map(individual -> mappers.fromIndividual(individual)).
                toList();
        return individualDTO;
    }

    @Override
    public IndividualDTO getIndividual(Long id) throws IndividualNotFoundException {
        Individual individual = individualRepository.findById(id).orElseThrow(
                () -> new IndividualNotFoundException("individual not found")
        );
        return mappers.fromIndividual(individual);
    }

    @Override
    public IndividualDTO createIndividual(IndividualDTO individualDTO) throws IndividualAlreadyExistsException {
        Individual individual = mappers.fromIndividualDTO(individualDTO);
        AppUser user = securityService.findUserByEmail(individual.getEmail());
        if (user != null)
            throw new IndividualAlreadyExistsException("Individual Already exists");
        Individual newIndividual = new Individual();
        newIndividual.setNom(individual.getNom());
        newIndividual.setSurname(individual.getSurname());
        newIndividual.setEmail(individual.getEmail());
        newIndividual.setPhone(individual.getPhone());
        String password = individual.getPassword();
        newIndividual.setPassword(passwordEncoder.encode(password));
        
        newIndividual.setDateOfBirth(individual.getDateOfBirth());
        Individual individualSaved = individualRepository.save(newIndividual);
        AppRole role = securityService.findRoleByRoleName("INDIVIDUAL");
        securityService.addRoleToUser(role.getRoleName(),individualSaved.getEmail());
        return mappers.fromIndividual(individualSaved);
    }

    @Override
    public IndividualDTO updateIndividual(Long id, IndividualDTO individualDTO) throws IndividualNotFoundException {
        Individual individual = mappers.fromIndividualDTO(individualDTO);
        Individual individualExist = individualRepository.findById(id).orElseThrow(
                () -> new IndividualNotFoundException("individual not found id " + id)
        );
        individualExist.setNom(individual.getNom());
        individualExist.setSurname(individual.getSurname());
        individualExist.setEmail(individual.getEmail());
        individualExist.setPhone(individual.getPhone());
        String password = individual.getPassword();
        individualExist.setPassword(passwordEncoder.encode(password));
        individualExist.setDateOfBirth(individual.getDateOfBirth());
        Individual individualsaved = individualRepository.save(individualExist);
        return mappers.fromIndividual(individualsaved);
    }

    @Override
    public boolean deleteIndividual(Long id) {
        Individual individual =individualRepository.findIndividualById(id);
        if (individual == null)
            return  false;
        individualRepository.delete(individual);
        return true;
    }
}
