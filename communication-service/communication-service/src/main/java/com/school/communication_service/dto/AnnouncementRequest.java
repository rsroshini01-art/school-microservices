package com.school.communication_service.dto;

import com.school.communication_service.entity.TargetAudience;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AnnouncementRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Content is required")
    private String content;

    @NotNull(message = "Target audience is required")
    private TargetAudience targetAudience;

    private Long classId; // required only when targetAudience = CLASS
}