package com.fst.trainingcenter.services;

import com.fst.trainingcenter.dtos.AssistantDTO;
import com.fst.trainingcenter.exceptions.AssistantAlreadyExistsException;
import com.fst.trainingcenter.exceptions.AssistantNotFoundException;

import java.util.List;

public interface AssistantService {

    List<AssistantDTO> getAllAssistants();
    AssistantDTO getAssistant(Long id) throws AssistantNotFoundException;
    AssistantDTO createAssistant(AssistantDTO assistantDTO) throws AssistantAlreadyExistsException;
    AssistantDTO updateAssistant(Long id,AssistantDTO assistantDTO) throws AssistantNotFoundException;
    void deleteAssistant(Long id) throws AssistantNotFoundException;
    void initiatePasswordReset(String email);
    Boolean completePasswordReset(String token, String newPassword);

}
