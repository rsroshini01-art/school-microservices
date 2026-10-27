package com.school.student_service.dto;

import com.school.student_service.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponse {

    private Long id;
    private String admissionNumber;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
    private String phone;
    private String email;
    private String address;
    private LocalDate admissionDate;
    private Long classId;
    private String className;     // filled in Step 6 (from academic-service)
    private Long sectionId;
    private String sectionName;   // filled in Step 6 (from academic-service)
    private String rollNumber;
    private boolean status;

    public static StudentResponse fromEntity(Student s) {
        return new StudentResponse(
                s.getId(),
                s.getAdmissionNumber(),
                s.getFirstName(),
                s.getLastName(),
                s.getDateOfBirth(),
                s.getGender(),
                s.getPhone(),
                s.getEmail(),
                s.getAddress(),
                s.getAdmissionDate(),
                s.getClassId(),
                null,
                s.getSectionId(),
                null,
                s.getRollNumber(),
                s.isStatus()
        );
    }
}