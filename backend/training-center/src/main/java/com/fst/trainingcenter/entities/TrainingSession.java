package com.fst.trainingcenter.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class TrainingSession {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate sessionDate;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime sessionStartTime;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime sessionEndTime;
    private int duration;
    @ManyToOne
    private Training training;
}
