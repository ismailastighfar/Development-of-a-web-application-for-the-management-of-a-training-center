package com.fst.trainingcenter.services.impl;

import com.fst.trainingcenter.dtos.AssistantDTO;
import com.fst.trainingcenter.dtos.TrainerDTO;
import com.fst.trainingcenter.entities.PasswordResetToken;
import com.fst.trainingcenter.exceptions.AssistantAlreadyExistsException;
import com.fst.trainingcenter.exceptions.AssistantNotFoundException;
import com.fst.trainingcenter.exceptions.IndividualNotFoundException;
import com.fst.trainingcenter.mappers.MappersImpl;
import com.fst.trainingcenter.repositories.PasswordResetTokenRepository;
import com.fst.trainingcenter.security.entities.AppUser;
import com.fst.trainingcenter.security.repositories.AppUserRepository;
import com.fst.trainingcenter.security.services.ISecurityService;
import com.fst.trainingcenter.services.AssistantService;
import com.fst.trainingcenter.services.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class AssistantServiceImpl implements AssistantService {

    private ISecurityService securityService;
    private AppUserRepository<AppUser> appUserRepository;
    private PasswordResetTokenRepository passwordResetTokenRepository;
    private EmailService emailService;
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
        return mappers.fromAssistant(existingAssistant);
    }

    @Override
    public void deleteAssistant(Long id) throws AssistantNotFoundException {
        AppUser assistant = appUserRepository.findById(id).orElseThrow(
                () -> new AssistantNotFoundException("Assistant not found with id " + id)
        );
        appUserRepository.delete(assistant);
    }

    @Override
    public void initiatePasswordReset(String email) {
        try {
            AppUser user = appUserRepository.findAppUserByEmail(email);
            if(user==null){
                throw new IndividualNotFoundException("User not found with this email");
            }
            String token = generatePasswordResetToken();

            PasswordResetToken resetToken = new PasswordResetToken();
            System.out.println("le token " + token);
            resetToken.setToken(token);
            resetToken.setExpiryDate(calculateExpiryDate());

            resetToken.setUser(user);
            passwordResetTokenRepository.save(resetToken);
            emailService.sendPasswordResetEmail(user.getEmail(), token);
        } catch (Exception e) {
            System.out.println("erreur in initiatePasswordReset");
        }
    }

    @Override
    public Boolean completePasswordReset(String token, String newPassword) {
        System.out.println("token " + token + " pass " + newPassword);
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token);
        if (resetToken != null && !resetToken.isExpired()) {
            AppUser user = resetToken.getUser();
            user.setPassword(passwordEncoder.encode(newPassword));
            appUserRepository.save(user);
            System.out.println("saved new pass");
            passwordResetTokenRepository.delete(resetToken);
            return true;
        } else {
            System.out.println("Invalid or expired token");
            return false;
        }
    }

    private String generatePasswordResetToken() {
        return UUID.randomUUID().toString();
    }

    private Date calculateExpiryDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, 30);
        return cal.getTime();
    }

}
