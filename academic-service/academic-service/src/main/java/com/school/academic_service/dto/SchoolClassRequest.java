package com.school.academic_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SchoolClassRequest {

    @NotNull(message = "Academic year ID is required")
    private Long academicYearId;

    @NotBlank(message = "Class name is required")
    private String className;

    private String description;
}