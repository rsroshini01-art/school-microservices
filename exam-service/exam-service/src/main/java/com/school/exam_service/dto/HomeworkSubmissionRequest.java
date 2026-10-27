package com.school.exam_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class HomeworkSubmissionRequest {

    @NotNull(message = "Homework ID is required")
    private Long homeworkId;

    @NotNull(message = "Student ID is required")
    private Long studentId;

    private String submissionText;

    private String attachmentUrl;
}