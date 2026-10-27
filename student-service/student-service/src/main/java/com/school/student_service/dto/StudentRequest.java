package com.school.student_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentRequest {

    @NotBlank(message = "Admission number is required")
    private String admissionNumber;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    private LocalDate dateOfBirth;

    private String gender;

    private String phone;

    @Email(message = "Invalid email format")
    private String email;

    private String address;

    private LocalDate admissionDate;

    @NotNull(message = "Class ID is required")
    private Long classId;

    private Long sectionId;

    private String rollNumber;
}