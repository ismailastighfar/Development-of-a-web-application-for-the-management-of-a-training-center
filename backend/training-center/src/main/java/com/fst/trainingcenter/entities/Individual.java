package com.fst.trainingcenter.entities;


import com.fst.trainingcenter.security.entities.AppUser;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Entity
@DiscriminatorValue("IND")
@Data @NoArgsConstructor @AllArgsConstructor
public class Individual extends AppUser {
   @DateTimeFormat(pattern = "yyyy-MM-dd")
   private LocalDate dateOfBirth;
}
