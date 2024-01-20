package com.fst.trainingcenter.dtos;

import lombok.Data;

@Data
public class CompanyDTO {

    private Long id;
    private String name;
    private String address;
    private String phone;
    private String url;
    private String email;
}
