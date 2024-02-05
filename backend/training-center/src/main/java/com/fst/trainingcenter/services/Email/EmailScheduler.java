package com.fst.trainingcenter.services.Email;
import com.fst.trainingcenter.entities.Training;
import com.fst.trainingcenter.exceptions.TrainingNotFoundException;
import com.fst.trainingcenter.repositories.TrainingRepository;
import com.fst.trainingcenter.services.EmailService;
import com.fst.trainingcenter.services.TrainingService;
import com.fst.trainingcenter.services.TrainingSessionService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@AllArgsConstructor
public class EmailScheduler {

    private final TrainingService trainingService;
    private final TrainingSessionService trainingSessionService;
    private final TrainingRepository trainingRepository;
    private final EmailService emailService;


    // Schedule the task to run every day at 4:30 PM
    @Scheduled(cron = "0 40 17 * * *")
    public void sendEmailAfterLastSession() throws TrainingNotFoundException {
        // Replace this with the actual logic to fetch and send emails
        List<Long> trainingIds = trainingService.getTrainingIdsForIndividuals();

        for (Long trainingId : trainingIds) {
            LocalDate lastSessionDate = trainingSessionService.getLastSessionDay(trainingId).orElse(null);
            if (lastSessionDate != null && lastSessionDate.isEqual(LocalDate.now().minusDays(1))) {
                List<String> individualEmails = trainingService.getIndividualEmailsByTrainingId(trainingId);
                Training training = trainingRepository.findById(trainingId).orElse(null);
                // Send emails to individuals
                for (String email : individualEmails) {
                  emailService.sendEmailAfterLastSession(email,training);
                }
            }
        }
    }
}
