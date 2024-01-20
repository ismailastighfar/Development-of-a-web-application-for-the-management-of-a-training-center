package com.fst.trainingcenter.entities;


import com.fst.trainingcenter.security.entities.AppUser;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("TRA")
@Data @AllArgsConstructor @NoArgsConstructor
public class Trainer extends AppUser {
    private String keywords; // skills
    private String description;
    private boolean isAccepted;
}
