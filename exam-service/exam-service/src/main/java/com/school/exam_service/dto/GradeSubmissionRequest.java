package com.school.exam_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GradeSubmissionRequest {

    @NotNull(message = "Marks awarded is required")
    private Double marksAwarded;

    private String feedback;
}