package com.fst.trainingcenter.entities;

import com.fst.trainingcenter.enums.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private int hours;
    private float cost;
    private int availableSeats;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    private String objectives;
    private String detailed_program;
    @Enumerated(EnumType.STRING)
    private Category category;
    private boolean isForCompany;
    private String city;
    private String code;
    private int maxSessions;
    @ManyToMany(mappedBy = "trainings" , fetch = FetchType.EAGER)
    private List<Individual> individuals = new ArrayList<>();
    @ManyToOne
    private Trainer trainer;
    @ManyToOne
    private Company company;
    @OneToMany(mappedBy = "training",fetch = FetchType.LAZY)
    private List<TrainingSession> trainingSessions = new ArrayList<>();
}
