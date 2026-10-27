package com.school.exam_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ExamSubjectRequest {

    @NotNull(message = "Subject ID is required")
    private Long subjectId;

    @NotNull(message = "Max marks is required")
    private Double maxMarks;
}