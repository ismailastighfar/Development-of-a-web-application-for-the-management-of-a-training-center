package com.fst.trainingcenter.services.impl;

import com.fst.trainingcenter.dtos.AssistantDTO;
import com.fst.trainingcenter.dtos.TrainerDTO;
import com.fst.trainingcenter.exceptions.AssistantAlreadyExistsException;
import com.fst.trainingcenter.exceptions.AssistantNotFoundException;
import com.fst.trainingcenter.mappers.MappersImpl;
import com.fst.trainingcenter.security.entities.AppUser;
import com.fst.trainingcenter.security.repositories.AppUserRepository;
import com.fst.trainingcenter.security.services.ISecurityService;
import com.fst.trainingcenter.services.AssistantService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class AssistantServiceImpl implements AssistantService {

    private ISecurityService securityService;
    private AppUserRepository<AppUser> appUserRepository;
    private MappersImpl mappers;
    private PasswordEncoder passwordEncoder;

    @Override
    public List<AssistantDTO> getAllAssistants() {
        List<AppUser> assistants = appUserRepository.findByUserRoles_RoleName("ASSISTANT");
        List<AssistantDTO> assistantDTOS = assistants.stream().
                map(assistant -> mappers.fromAssistant(assistant)).
                toList();
        return assistantDTOS;
    }

    @Override
    public AssistantDTO getAssistant(Long id) throws AssistantNotFoundException {
        AppUser assistant = appUserRepository.findById(id).orElseThrow(
                () -> new AssistantNotFoundException("Assistant not found with id " + id)
        );
        return mappers.fromAssistant(assistant);
    }

    @Override
    public AssistantDTO createAssistant(AssistantDTO assistantDTO) throws AssistantAlreadyExistsException {
       AppUser user =  appUserRepository.findAppUserByEmail(assistantDTO.getEmail());
       if (user != null)
           throw new AssistantAlreadyExistsException("user already exists");
        AppUser assistant = mappers.fromAssistantDTO(assistantDTO);
        assistant = securityService.addNewUser(assistant);
        securityService.addRoleToUser("ASSISTANT", assistant.getEmail());
        return mappers.fromAssistant(assistant);
    }

    @Override
    public AssistantDTO updateAssistant(Long id, AssistantDTO assistantDTO) throws AssistantNotFoundException {
        AppUser existingAssistant = appUserRepository.findById(id).orElseThrow(
                () -> new AssistantNotFoundException("Assistant not found with id " + id)
        );
        existingAssistant.setNom(assistantDTO.getNom());
        existingAssistant.setSurname(assistantDTO.getSurname());
        existingAssistant.setEmail(assistantDTO.getEmail());
        existingAssistant.setPhone(assistantDTO.getPhone());
        String password = assistantDTO.getPassword();
        existingAssistant.setPassword(passwordEncoder.encode(password));
        return mappers.fromAssistant(existingAssistant);
    }

    @Override
    public void deleteAssistant(Long id) throws AssistantNotFoundException {
        AppUser assistant = appUserRepository.findById(id).orElseThrow(
                () -> new AssistantNotFoundException("Assistant not found with id " + id)
        );
        appUserRepository.delete(assistant);
    }
}
