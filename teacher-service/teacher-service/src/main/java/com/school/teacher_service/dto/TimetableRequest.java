package com.school.teacher_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalTime;

@Data
public class TimetableRequest {

    @NotNull(message = "Class ID is required")
    private Long classId;

    private Long sectionId;

    @NotNull(message = "Subject ID is required")
    private Long subjectId;

    private Long teacherId;

    @NotBlank(message = "Day of week is required")
    private String dayOfWeek;

    @NotNull(message = "Start time is required")
    private LocalTime startTime;

    @NotNull(message = "End time is required")
    private LocalTime endTime;
}