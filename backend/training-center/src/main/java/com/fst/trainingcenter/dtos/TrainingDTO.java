package com.fst.trainingcenter.dtos;

import com.fst.trainingcenter.entities.Individual;
import jakarta.persistence.Column;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
public class TrainingDTO {
    private long id;
    @Column(unique = true)
    private String title;
    private int hours;
    private float cost;
    private int availableSeats;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    private String objectives;
    private String detailed_program;
    private String category;
    private boolean isForCompany;
    private String city;
    private int maxSessions;
    private List<IndividualDTO> individuals;
    private TrainerDTO trainer;
    private CompanyDTO company;
}
