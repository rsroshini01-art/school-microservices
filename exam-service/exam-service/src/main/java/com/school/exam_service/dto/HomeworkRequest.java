package com.school.exam_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class HomeworkRequest {

    @NotNull(message = "Class ID is required")
    private Long classId;

    private Long sectionId;

    @NotNull(message = "Subject ID is required")
    private Long subjectId;

    private Long teacherId;

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotNull(message = "Due date is required")
    private LocalDate dueDate;
}