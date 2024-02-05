package com.fst.trainingcenter;

import com.fst.trainingcenter.entities.Individual;
import com.fst.trainingcenter.entities.Trainer;
import com.fst.trainingcenter.repositories.IndividualRepository;
import com.fst.trainingcenter.repositories.TrainerRepository;
import com.fst.trainingcenter.security.entities.AppRole;
import com.fst.trainingcenter.security.entities.AppUser;
import com.fst.trainingcenter.security.services.ISecurityService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

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

//	@Bean
	CommandLineRunner commandLineRunner(ISecurityService securityService , TrainerRepository trainerRepository
			, PasswordEncoder passwordEncoder, IndividualRepository individualRepository
										){
		return args -> {

			AppUser user = AppUser.builder().
					email("zaka@gmail.com")
					.password("12345").
					nom("zaka").surname("kas").phone("06014").
					build();
			AppRole role = AppRole.builder()
					.roleName("ADMIN")
					.build();

			securityService.addNewUser(user);
			securityService.addNewRole(role);
			securityService.addRoleToUser(role.getRoleName(),user.getEmail());

			Trainer trainer = new Trainer();
			trainer.setNom("ismail");
			trainer.setSurname("ast");
			trainer.setAccepted(false);
			trainer.setEmail("ismail@gmail.com");
			trainer.setPassword(passwordEncoder.encode("12345"));
			trainer.setPhone("0606");
			trainer.setDescription("test");
			trainer.setKeywords("react ,  spring boot");
			trainerRepository.save(trainer);
			AppRole role1 = AppRole.builder()
					.roleName("TRAINER")
					.build();
			securityService.addNewRole(role1);
			securityService.addRoleToUser(role1.getRoleName(), trainer.getEmail());

			AppRole role2 = AppRole.builder()
					.roleName("INDIVIDUAL")
					.build();
			securityService.addNewRole(role2);
			Individual individual = new Individual();
			individual.setNom("yahya");
			individual.setSurname("azzouz");
			individual.setEmail("yahya@gmail.com");
			individual.setPassword(passwordEncoder.encode("12345"));
			individual.setPhone("06025");
			individual.setDateOfBirth(LocalDate.parse("2000-01-04"));
			individualRepository.save(individual);
			securityService.addRoleToUser(role2.getRoleName(), individual.getEmail());
		};
	}
}
