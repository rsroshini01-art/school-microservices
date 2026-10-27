package com.school.academic_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TermRequest {

    @NotNull(message = "Academic year ID is required")
    private Long academicYearId;

    @NotBlank(message = "Term name is required")
    private String termName;

    private LocalDate startDate;
    private LocalDate endDate;
}