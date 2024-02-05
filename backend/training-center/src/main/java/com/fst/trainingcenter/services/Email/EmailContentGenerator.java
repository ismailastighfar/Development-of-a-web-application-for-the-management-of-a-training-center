package com.fst.trainingcenter.services.Email;

import com.fst.trainingcenter.entities.Individual;
import com.fst.trainingcenter.entities.Trainer;
import com.fst.trainingcenter.entities.Training;
import com.fst.trainingcenter.entities.TrainingSession;
import com.fst.trainingcenter.security.entities.AppUser;

public class EmailContentGenerator {
    private static String styles = EmailStyles.getStyles();
    private static String logoLink = "http://localhost:8080/images/logo.jpg";

    public static String getEmailNewIndividualContent(Individual individual) {
        return "<html>"
                + styles
                + "<body>"
                + "  <div class='image-container'>"
                + "    <img src='" + logoLink + "' alt='Logo' class='image'/>"
                + "  </div>"
                + "<div class='container'>"
                + "  <div class='text-container'>"
                + "    <b>Hi"+" "+ individual.getNom()+" "+individual.getSurname()+","+"</b><br>"
                + "    <p>Welcome to our Training Center</p><br>"
                + "    <div class='button-container' style='text-align: center;'>"
                + "      <a href='http://localhost:4200' class='button-link'>"
                + "       <span style='color: #fff;'> Access Application </span>"
                + "      </a>"
                + "    </div>"
                + "    <div class='ntt'>© 2024 Training center</div>"
                + "  </div>"
                + "</div>"
                + "</body>"
                + "</html>";
    }

    public static String sendEmailAfterLastSessionContent(Training training) {
        return "<html>"
                + styles
                + "<body>"
                + "<div class='container'>"
                + "  <div class='text-container'>"
                + "      <p>You can now add feedback for your trainer for the training : " +training.getTitle()+ "</p><br>"
                + "      <b>Your unique code is: " + training.getCode() + "</b><br>"
                + "      <p>Include this code in your evaluation form.</p><br>"
                + "    <div class='button-container' style='text-align: center;'>"
                + "      <a href='http://localhost:4200' class='button-link'>"
                + "       <span style='color: #fff;'> Access Application </span>"
                + "      </a>"
                + "    </div>"
                + "    <div class='ntt'>© 2024 Training center</div>"
                + "  </div>"
                + "</div>"
                + "</body>"
                + "</html>";
    }

    public static String getEmailApplyTrainerContent(Trainer trainer){
        return "<html>"
                + styles
                + "<body>"
                + "  <div class='image-container'>"
                + "    <img src='" + logoLink + "' alt='Logo' class='image'/>"
                + "  </div>"
                + "<div class='container'>"
                + "  <div class='text-container'>"
                + "    <b>Hi"+" "+ trainer.getNom()+" "+trainer.getSurname()+","+"</b><br>"
                + "    <p>Your request to become a trainer is being processed by our team<br><br>"
                + "       Please follow this link to review the request :</p><br>"
                + "    <div class='ntt'>© 2024 Training center</div>"
                + "  </div>"
                + "</div>"
                + "</body>"
                + "</html>";
    }

    public static String getEmailRefuseTrainerContent(Trainer trainer){
        return "<html>"
                + styles
                + "<body>"
                + "  <div class='image-container'>"
                + "    <img src='" + logoLink + "' alt='Logo' class='image'/>"
                + "  </div>"
                + "<div class='container'>"
                + "  <div class='text-container'>"
                + "    <b>Hi"+" "+ trainer.getNom()+" "+trainer.getSurname()+","+"</b><br>"
                + " <p>We appreciate your interest in becoming a trainer with our Training Center.</p>"
                + " <p>After careful consideration, we regret to inform you that your application has not been successful at this time. We received many qualified applications, and the selection process was highly competitive.</p>"
                + " <p>Thank you for taking the time to apply. We encourage you to explore future opportunities with our Training Center and wish you success in your endeavors.</p>"
                +" <p>Best regards,</p>"
                +"  <p>The Training Center Team</p>"
                + "  </div>"
                + "</div>"
                + "</body>"
                + "</html>";
    }

    public static String getEmailAcceptTrainerContent(Trainer trainer ,String password){
        return "<html>"
                + styles
                + "<body>"
                + "  <div class='image-container'>"
                + "    <img src='" + logoLink + "' alt='Logo' class='image'/>"
                + "  </div>"
                + "<div class='container'>"
                + "  <div class='text-container'>"
                + "    <b>Hi"+" "+ trainer.getNom()+" "+trainer.getSurname()+","+"</b><br>"
                + "<p>Congratulations! Your request to become a trainer has been accepted by our team.</p>"
                + "<p>We are excited to welcome you to our Training Center as a trainer. Your expertise and skills will contribute significantly to the success of our training programs.</p>"
                + "<p>Please use your email and this temporary password, but remember to change it as soon as possible: " + password + " to access your trainer account:</p>"
                +" <p>Best regards,</p>"
                +"  <p>The Training Center Team</p>"
                + "  </div>"
                + "</div>"
                + "</body>"
                + "</html>";
    }

    public static  String getEmailContent(String token, AppUser user) {
        return "<html>"
                + styles
                + "<body>"
                + "  <div class='image-container'>"
                + "    <img src='" + logoLink + "' alt='Logo' class='image'/>"
                + "  </div>"
                + "<div class='container'>"
                + "  <div class='text-container'>"
                + "    <b>Hi"+" "+ user.getNom()+" "+user.getSurname()+","+"</b><br>"
                + "    <p>Sorry to hear you’re having trouble logging into <span>Training center</span><br><br>"
                + "       We got a message that you forgot your password. If this was you, <br><br>"
                + "       reset your password now using this token :" + token + "</p><br><br>"
                + "    <div class='ntt'>© 2024 Training center </div>"
                + "  </div>"
                + "</div>"
                + "</body>"
                + "</html>";
    }

    public static  String getEmailSendSessionContent(Individual individual , Training training , TrainingSession trainingSession) {
        return "<html>"
                + "<body>"
                + "<div class='container'>"
                + "  <div class='text-container'>"
                + "    <b>Hi " + individual.getNom() + " " + individual.getSurname() + ",</b><br>"
                + "    <p>We're excited to inform you about a new training session for the training you enrolled in:</p>"
                + "    <p><strong>Training Title:</strong> " + training.getTitle() + "</p>"
                + "    <p><strong>Session Date:</strong> " + trainingSession.getSessionDate() + "</p>"
                + "    <p><strong>Session start Time:</strong> " + trainingSession.getSessionStartTime() + "</p>"
                + "    <p><strong>Session end Time:</strong> " + trainingSession.getSessionEndTime() + "</p>"
                + "    <p>Please make sure to attend the session at the specified date and time. If you have any questions, feel free to reach out to us.</p>"
                + "    <p>Thank you for choosing our Training Center!</p>"
                + "    <div class='ntt'>© 2024 Training center </div>"
                + "  </div>"
                + "</div>"
                + "</body>"
                + "</html>";
    }

}
