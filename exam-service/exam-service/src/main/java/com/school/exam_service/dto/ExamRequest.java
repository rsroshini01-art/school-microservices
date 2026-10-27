package com.school.exam_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ExamRequest {

    @NotNull(message = "Academic year ID is required")
    private Long academicYearId;

    private Long termId;

    @NotBlank(message = "Exam name is required")
    private String examName;

    private String examType;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;
}