package com.fst.trainingcenter.dtos;

import lombok.Data;

@Data
public class TrainerRequestDTO {
    private String nom;
    private String surname;
    private String phone;
    private String email;
    private String keywords; // skills
    private String description;
}
