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
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;
    private String title;
    private int hours;
    private float cost;
    private String objectives;
    private String detailed_program;
    private String category;
    @ManyToOne(fetch = FetchType.EAGER)
    private Trainer trainer;
    @ManyToOne(fetch = FetchType.EAGER)
    private Company company;

}
