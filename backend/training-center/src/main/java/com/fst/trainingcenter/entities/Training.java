package com.fst.trainingcenter.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Training {
    @Id
    private long id;
    private String title;
    private int hours;
    private float cost;
    private String objectives;
    private String detailed_program;
    private String category;
    @ManyToOne
    private Trainer trainer;
    @ManyToOne
    private Company company;

}
