package com.fst.trainingcenter.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@AllArgsConstructor
@Data
public class Formation {

    private List<TrainingSession> trainingSessionList;

    private List<Individual> individualList;

    private Trainer trainer;

    private Company company;

    private String city;






}
