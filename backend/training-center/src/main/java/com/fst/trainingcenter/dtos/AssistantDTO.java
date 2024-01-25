package com.fst.trainingcenter.dtos;

import lombok.Data;

@Data
public class AssistantDTO {
    private Long id;
    private String nom;
    private String surname;
    private String phone;
    private String password;
    private String email;
}
