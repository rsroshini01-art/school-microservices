package com.school.exam_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GradeRuleRequest {

    @NotNull(message = "Min percentage is required")
    private Double minPercentage;

    @NotNull(message = "Max percentage is required")
    private Double maxPercentage;

    @NotBlank(message = "Grade is required")
    private String grade;

    private boolean passGrade = true;
}