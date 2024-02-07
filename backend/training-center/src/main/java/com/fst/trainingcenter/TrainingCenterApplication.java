package com.fst.trainingcenter;

import com.fst.trainingcenter.dtos.TrainingDTO;
import com.fst.trainingcenter.entities.Individual;
import com.fst.trainingcenter.entities.Trainer;
import com.fst.trainingcenter.entities.Training;
import com.fst.trainingcenter.enums.Category;
import com.fst.trainingcenter.repositories.IndividualRepository;
import com.fst.trainingcenter.repositories.TrainerRepository;
import com.fst.trainingcenter.repositories.TrainingRepository;
import com.fst.trainingcenter.security.entities.AppRole;
import com.fst.trainingcenter.security.entities.AppUser;
import com.fst.trainingcenter.security.services.ISecurityService;
import com.fst.trainingcenter.services.TrainingService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Collections;
import java.util.UUID;

@SpringBootApplication
@EnableScheduling
public class TrainingCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainingCenterApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}


//@Bean
	CommandLineRunner commandLineRunner(ISecurityService securityService , TrainingRepository trainingRepository
			, PasswordEncoder passwordEncoder, IndividualRepository individualRepository
										){
		return args -> {
			// Training 2: Web Development with React
			Training training2 = Training.builder()
					.title("Web Development with React")
					.hours(30)
					.cost(600.0f)
					.availableSeats(20)
					.minSeats(10)
					.endEnrollDate(LocalDate.now().plusDays(15))
					.objectives("Build modern web applications using React and related technologies.")
					.detailed_program("Day 1: Introduction to React, Day 2: React Components, Day 3: State and Props, etc.")
					.category(Category.WEB_DEVELOPMENT)
					.isForCompany(false)
					.city("Tetouan")
					.code(UUID.randomUUID().toString())
					.maxSessions(8)
					.build();
			trainingRepository.save(training2);

			// Training 3: Data Science with Python
			Training training3 = Training.builder()
					.title("Data Science with Python")
					.hours(40)
					.cost(800.0f)
					.availableSeats(25)
					.minSeats(12)
					.endEnrollDate(LocalDate.now().plusDays(20))
					.objectives("Explore data science concepts and tools using Python.")
					.detailed_program("Day 1: Python Basics, Day 2: Data Analysis with Pandas, Day 3: Machine Learning, etc.")
					.category(Category.DATA_SCIENCE)
					.isForCompany(false)
					.city("Tanger")
					.code(UUID.randomUUID().toString())
					.maxSessions(10)
					.build();
			trainingRepository.save(training3);

			// Training 4: Cybersecurity Fundamentals
			Training training4 = Training.builder()
					.title("Cybersecurity Fundamentals")
					.hours(35)
					.cost(700.0f)
					.availableSeats(18)
					.minSeats(9)
					.endEnrollDate(LocalDate.now().plusDays(18))
					.objectives("Learn essential concepts of cybersecurity and basic defense techniques.")
					.detailed_program("Day 1: Introduction to Cybersecurity, Day 2: Network Security, Day 3: Threat Detection, etc.")
					.category(Category.SECURITY)
					.isForCompany(false)
					.city("SecureCity")
					.code(UUID.randomUUID().toString())
					.maxSessions(9)
					.build();
			trainingRepository.save(training4);

			// Training 5: Mobile App Development with Flutter
			Training training5 = Training.builder()
					.title("Mobile App Development with Flutter")
					.hours(28)
					.cost(550.0f)
					.availableSeats(15)
					.minSeats(8)
					.endEnrollDate(LocalDate.now().plusDays(12))
					.objectives("Build cross-platform mobile apps using Flutter framework.")
					.detailed_program("Day 1: Introduction to Flutter, Day 2: Flutter Widgets, Day 3: State Management, etc.")
					.category(Category.MOBILE_APP_DEVELOPMENT)
					.isForCompany(false)
					.city("Fluttertown")
					.code(UUID.randomUUID().toString())
					.maxSessions(7)
					.build();
			trainingRepository.save(training5);

			// Training 6: Java Enterprise Development
			Training training6 = Training.builder()
					.title("Java Enterprise Development")
					.hours(32)
					.cost(640.0f)
					.availableSeats(18)
					.minSeats(9)
					.endEnrollDate(LocalDate.now().plusDays(14))
					.objectives("Explore Java EE for building robust and scalable enterprise applications.")
					.detailed_program("Day 1: Introduction to Java EE, Day 2: Java Persistence API (JPA), Day 3: JavaServer Faces (JSF), etc.")
					.category(Category.SOFTWARE_ENGINEERING)
					.isForCompany(false)
					.city("Javatown")
					.maxSessions(8)
					.code(UUID.randomUUID().toString())
					.build();
			trainingRepository.save(training6);

			AppUser user = new AppUser();
			user.setNom("admin");
			user.setSurname("admin");
			user.setEmail("admin@gmail.com");
			String password = "12345";
			user.setPassword(passwordEncoder.encode(password));
			securityService.addNewUser(user);
			AppRole role = new AppRole();
			role.setRoleName("ADMIN");
			securityService.addNewRole(role);

			securityService.addRoleToUser(role.getRoleName() , user.getEmail());

			AppRole role1 = new AppRole();
			role1.setRoleName("INDIVIDUAL");
			securityService.addNewRole(role1);

			AppRole role2 = new AppRole();
			role2.setRoleName("TRAINER");
			securityService.addNewRole(role2);

			AppRole role3 = new AppRole();
			role3.setRoleName("ASSISTANT");
			securityService.addNewRole(role3);

		};
	}
}
