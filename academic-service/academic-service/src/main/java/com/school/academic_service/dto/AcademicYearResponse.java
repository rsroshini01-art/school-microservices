package com.school.academic_service.dto;

import com.school.academic_service.entity.AcademicYear;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcademicYearResponse {

    private Long id;
    private Long schoolId;
    private String schoolName;
    private String yearName;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean active;

    public static AcademicYearResponse fromEntity(AcademicYear year) {
        return new AcademicYearResponse(
                year.getId(),
                year.getSchool().getId(),
                year.getSchool().getSchoolName(),
                year.getYearName(),
                year.getStartDate(),
                year.getEndDate(),
                year.isActive()
        );
    }
}