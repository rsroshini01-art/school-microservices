package com.school.teacher_service.dto;

import com.school.teacher_service.entity.Teacher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherResponse {

    private Long id;
    private String employeeId;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String qualification;
    private String specialization;
    private LocalDate joiningDate;
    private boolean status;

    public static TeacherResponse fromEntity(Teacher t) {
        return new TeacherResponse(
                t.getId(),
                t.getEmployeeId(),
                t.getFirstName(),
                t.getLastName(),
                t.getPhone(),
                t.getEmail(),
                t.getQualification(),
                t.getSpecialization(),
                t.getJoiningDate(),
                t.isStatus()
        );
    }
}