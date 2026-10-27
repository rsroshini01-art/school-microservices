package com.school.exam_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MarksRequest {

    @NotNull(message = "Exam ID is required")
    private Long examId;

    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotNull(message = "Subject ID is required")
    private Long subjectId;

    @NotNull(message = "Marks obtained is required")
    private Double marksObtained;

    @NotNull(message = "Max marks is required")
    private Double maxMarks;

    private String remarks;
}