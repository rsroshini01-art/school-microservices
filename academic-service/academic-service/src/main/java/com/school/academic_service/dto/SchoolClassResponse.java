package com.school.academic_service.dto;

import com.school.academic_service.entity.SchoolClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchoolClassResponse {

    private Long id;
    private Long academicYearId;
    private String className;
    private String description;
    private boolean status;

    public static SchoolClassResponse fromEntity(SchoolClass sc) {
        return new SchoolClassResponse(
                sc.getId(),
                sc.getAcademicYear().getId(),
                sc.getClassName(),
                sc.getDescription(),
                sc.isStatus()
        );
    }
}