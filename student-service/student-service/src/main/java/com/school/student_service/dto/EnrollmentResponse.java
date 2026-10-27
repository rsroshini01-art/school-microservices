package com.school.student_service.dto;

import com.school.student_service.entity.Enrollment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentResponse {

    private Long id;
    private Long studentId;
    private String studentName;
    private Long academicYearId;
    private String yearName;      // filled in Step 6 (from academic-service)
    private Long classId;
    private String className;     // filled in Step 6 (from academic-service)
    private Long sectionId;
    private String sectionName;   // filled in Step 6 (from academic-service)
    private LocalDate enrollmentDate;
    private boolean active;

    public static EnrollmentResponse fromEntity(Enrollment e) {
        return new EnrollmentResponse(
                e.getId(),
                e.getStudent().getId(),
                e.getStudent().getFirstName() + " " + e.getStudent().getLastName(),
                e.getAcademicYearId(),
                null,
                e.getClassId(),
                null,
                e.getSectionId(),
                null,
                e.getEnrollmentDate(),
                e.isActive()
        );
    }
}