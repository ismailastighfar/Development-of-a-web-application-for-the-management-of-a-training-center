package com.fst.trainingcenter.dtos;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class TrainingSessionDTO {
    private Long id;
    private String name;
    private String description;
    private LocalDate sessionDate;
    private LocalTime sessionStartTime;
    private LocalTime sessionEndTime;
    private int duration;
    private Long trainingId;
}
