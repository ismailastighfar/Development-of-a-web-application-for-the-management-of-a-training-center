package com.fst.trainingcenter.dtos;


import lombok.Data;

import java.time.LocalDate;

@Data
public class IndividualDTO {
    private Long id;
    private String nom;
    private String surname;
    private String phone;
    private String password;
    private String email;
    private LocalDate dateOfBirth;
}
