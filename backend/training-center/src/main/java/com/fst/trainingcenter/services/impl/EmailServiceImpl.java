package com.fst.trainingcenter.services.impl;


import com.fst.trainingcenter.entities.Individual;
import com.fst.trainingcenter.entities.Trainer;
import com.fst.trainingcenter.exceptions.IndividualNotFoundException;
import com.fst.trainingcenter.exceptions.TrainingNotFoundException;
import com.fst.trainingcenter.repositories.IndividualRepository;
import com.fst.trainingcenter.repositories.TrainerRepository;
import com.fst.trainingcenter.security.entities.AppUser;
import com.fst.trainingcenter.security.repositories.AppUserRepository;
import com.fst.trainingcenter.services.Email.EmailContentGenerator;
import com.fst.trainingcenter.services.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private IndividualRepository individualRepository;
    private TrainerRepository trainerRepository;
    private AppUserRepository<AppUser> appUserRepository;

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
    @Override
    public void sendEmailNewIndividual(Long id) throws IndividualNotFoundException {
        Individual individual = individualRepository.findById(id).orElseThrow(
                () -> new IndividualNotFoundException("individual not found wit id" + id)
        );
        String emailContent = EmailContentGenerator.getEmailNewIndividualContent(individual);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setTo(individual.getEmail());
            messageHelper.setSubject("Welcome to Training Center");
            messageHelper.setText(emailContent, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            logger.error("Error sending welcome email to individual", e);
        }
    }

    @Override
    public void senEmailApplyTrainer(Long id) throws TrainingNotFoundException {
        Trainer trainer = trainerRepository.findById(id).orElseThrow(
                () -> new TrainingNotFoundException("trainer not found with id " + id)
        );
        String emailContent = EmailContentGenerator.getEmailApplyTrainerContent(trainer);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setTo(trainer.getEmail());
            messageHelper.setSubject("Application for becoming a trainer in our center");
            messageHelper.setText(emailContent, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            logger.error("Error sending welcome email to trainer", e);
        }
    }

    @Override
    public void sendEmailRefuseTrainer(Long id) throws TrainingNotFoundException {
        Trainer trainer = trainerRepository.findById(id).orElseThrow(
                () -> new TrainingNotFoundException("trainer not found with id " + id)
        );
        String emailContent = EmailContentGenerator.getEmailRefuseTrainerContent(trainer);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setTo(trainer.getEmail());
            messageHelper.setSubject("Your Trainer Application Status");
            messageHelper.setText(emailContent, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            logger.error("Error sending welcome email to trainer", e);
        }
    }

    @Override
    public void sendEmailAcceptTrainer(Long id , String password) throws TrainingNotFoundException {
        Trainer trainer = trainerRepository.findById(id).orElseThrow(
                () -> new TrainingNotFoundException("trainer not found with id " + id)
        );
        String emailContent = EmailContentGenerator.getEmailAcceptTrainerContent(trainer,password);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setTo(trainer.getEmail());
            messageHelper.setSubject("Your Trainer Application Status");
            messageHelper.setText(emailContent, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            logger.error("Error sending welcome email to trainer", e);
        }
    }

    @Override
    public void sendPasswordResetEmail(String userEmail, String token) throws IndividualNotFoundException {
        AppUser user = appUserRepository.findAppUserByEmail(userEmail);
        if (user == null)
            throw new IndividualNotFoundException("user not found with email : " + userEmail);


        if (user != null) {
            try {
                MimeMessage message = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

                String subject = "Reset Password";
                helper.setSubject(subject);
                helper.setTo(userEmail);

                String emailContent = EmailContentGenerator.getEmailContent(token,user);

                helper.setText(emailContent, true);
                helper.setSentDate(new Date());

                javaMailSender.send(message);

                logger.info("Password reset email sent to : " + userEmail);
            } catch (MessagingException ex) {
                logger.error("Error during the sending of the password reset email..", ex);
            }
        } else {
            logger.error("Unable to send the password reset email. User not found.");
        }
    }
}
