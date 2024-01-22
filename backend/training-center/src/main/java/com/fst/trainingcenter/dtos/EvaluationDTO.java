package com.fst.trainingcenter.dtos;

import lombok.Data;

@Data
public class EvaluationDTO {
    private Long id;
    private String pedagogicalQuality;
    private String pace;
    private String courseSupport;
    private String subjectMastery;
    private String code;
    private Long trainerId;
    private Long IndividualId;
}
