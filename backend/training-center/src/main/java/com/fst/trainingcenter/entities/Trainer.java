package com.fst.trainingcenter.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fst.trainingcenter.security.entities.AppUser;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@DiscriminatorValue("TRA")
@Data @AllArgsConstructor @NoArgsConstructor
public class Trainer extends AppUser {
    private String keywords; // skills
    private String description;
    private boolean isAccepted;
    @JsonIgnore
    @OneToMany(mappedBy = "trainer")
    private List<Training> trainingList;
}
