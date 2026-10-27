package com.school.student_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EnrollmentRequest {

    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotNull(message = "Academic year ID is required")
    private Long academicYearId;

    @NotNull(message = "Class ID is required")
    private Long classId;

    private Long sectionId;

    @NotNull(message = "Enrollment date is required")
    private LocalDate enrollmentDate;
}