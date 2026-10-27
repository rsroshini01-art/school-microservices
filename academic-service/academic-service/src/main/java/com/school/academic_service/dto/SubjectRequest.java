package com.school.academic_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SubjectRequest {

    @NotBlank(message = "Subject name is required")
    private String subjectName;

    private String subjectCode;

    @NotNull(message = "Class ID is required")
    private Long classId;

    private Long teacherId;
}