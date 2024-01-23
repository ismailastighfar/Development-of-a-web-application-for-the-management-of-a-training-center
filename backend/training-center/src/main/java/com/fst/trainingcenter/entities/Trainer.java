package com.fst.trainingcenter.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fst.trainingcenter.security.entities.AppUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("TRA")
@Data @AllArgsConstructor @NoArgsConstructor
public class Trainer extends AppUser {
    private String keywords; // skills
    private String description;
    private boolean isAccepted;
    @OneToMany(mappedBy = "trainer",fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Training> trainings = new ArrayList<>();
    @OneToMany(mappedBy = "trainer",fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Evaluation> evaluations = new ArrayList<>();
}
