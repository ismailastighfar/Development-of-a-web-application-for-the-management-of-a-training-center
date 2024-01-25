package com.fst.trainingcenter.dtos;

import com.fst.trainingcenter.entities.Individual;
import com.fst.trainingcenter.enums.Category;
import jakarta.persistence.Column;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
public class TrainingDTO {
    private long id;
    private String title;
    private int hours;
    private float cost;
    private int availableSeats;
    private LocalDate startDate;
    private String objectives;
    private String detailed_program;
    private Category category;
    private boolean isForCompany;
    private String city;
    private int maxSessions;
    private List<IndividualDTO> individuals;
    private TrainerDTO trainer;
    private CompanyDTO company;
}
