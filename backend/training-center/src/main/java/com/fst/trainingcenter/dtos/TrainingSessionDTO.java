package com.fst.trainingcenter.dtos;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class TrainingSessionDTO {
    private Long id;
    private LocalDate sessionDate;
    private LocalTime sessionTime;
    private int duration;
    private Long trainingId;
}
