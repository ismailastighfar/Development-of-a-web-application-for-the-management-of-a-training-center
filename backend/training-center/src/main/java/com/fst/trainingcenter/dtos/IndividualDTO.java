package com.fst.trainingcenter.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class IndividualDTO {
    private Long id;
    private String nom;
    private String surname;
    private String phone;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String email;
    private LocalDate dateOfBirth;
}
