package com.fst.trainingcenter.entities;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fst.trainingcenter.security.entities.AppUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("IND")
@Data @NoArgsConstructor @AllArgsConstructor
public class Individual extends AppUser {
   @DateTimeFormat(pattern = "yyyy-MM-dd")
   private LocalDate dateOfBirth;
   @ManyToMany(fetch = FetchType.EAGER)
   @ToString.Exclude
   @JoinTable(name = "Enrollment")
   @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
   private List<Training> trainings;
   @OneToMany(mappedBy = "individual",fetch = FetchType.EAGER)
   @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
   private List<Evaluation> evaluations = new ArrayList<>();
}
