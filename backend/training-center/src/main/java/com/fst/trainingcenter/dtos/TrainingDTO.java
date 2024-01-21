package com.fst.trainingcenter.dtos;

import com.fst.trainingcenter.entities.Company;
import com.fst.trainingcenter.entities.Trainer;

import lombok.Data;

@Data
public class TrainingDTO {
    private long id;
    private String title;
    private int hours;
    private float cost;
    private String objectives;
    private String detailed_program;
    private String category;
    private Trainer trainer; // TODO:might change to TrainerDTO
    private Company company;

}
