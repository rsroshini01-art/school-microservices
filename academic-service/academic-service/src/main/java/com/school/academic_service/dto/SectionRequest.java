package com.school.academic_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SectionRequest {

    @NotNull(message = "Class ID is required")
    private Long classId;

    @NotBlank(message = "Section name is required")
    private String sectionName;
}