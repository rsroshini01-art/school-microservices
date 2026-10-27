package com.school.academic_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SchoolRequest {

    @NotBlank(message = "School name is required")
    private String schoolName;

    private String address;
    private String phone;
    private String email;
    private String website;
    private String principalName;
}