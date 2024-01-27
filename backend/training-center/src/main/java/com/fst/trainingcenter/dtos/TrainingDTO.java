package com.fst.trainingcenter.dtos;

import com.fst.trainingcenter.enums.Category;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TrainingDTO {
    private long id;
    private String title;
    private int hours;
    private float cost;
    private int availableSeats;
    private int minSeats;
    private LocalDate endEnrollDate;
    private String objectives;
    private String detailed_program;
    private Category category;
    private boolean isForCompany;
    private String city;
    private int maxSessions;
    private  Long trainerId;
    private Long companyId;
}
