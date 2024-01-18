package com.fst.trainingcenter.models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor

public class AppUser {
    private Long Id;
    private String name;
    private String surname;
    private Date dateOfBirth;
    private String city;
    private String email;
    private Long phone;
    //Role role; { admin , assistant, trainer, individual }

}
