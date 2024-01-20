package com.fst.trainingcenter.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TrainerDTO {
    private Long id;
    private String nom;
    private String surname;
    private String phone;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String email;
    private String keywords; // skills
    private String description;
}
