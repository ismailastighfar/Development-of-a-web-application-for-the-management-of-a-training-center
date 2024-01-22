package com.fst.trainingcenter.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Evaluation {
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   private String pedagogicalQuality;
   private String pace;
   private String courseSupport;
   private String subjectMastery;
   private String code;
   @ManyToOne
   private Individual individual;
   @ManyToOne
   private Trainer trainer;
}
