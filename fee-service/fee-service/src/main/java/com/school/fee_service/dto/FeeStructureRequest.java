package com.school.fee_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FeeStructureRequest {

    @NotNull(message = "Class ID is required")
    private Long classId;

    @NotBlank(message = "Fee name is required")
    private String feeName;

    @NotNull(message = "Amount is required")
    private Double amount;

    private String frequency;
}