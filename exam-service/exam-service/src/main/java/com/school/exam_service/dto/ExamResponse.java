package com.school.exam_service.dto;

import com.school.exam_service.entity.Exam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamResponse {

    private Long id;
    private Long academicYearId;
    private Long termId;
    private String termName;   // filled in Step 6 (from academic-service)
    private String examName;
    private String examType;
    private LocalDate startDate;
    private LocalDate endDate;

    public static ExamResponse fromEntity(Exam e) {
        return new ExamResponse(
                e.getId(),
                e.getAcademicYearId(),
                e.getTermId(),
                null,
                e.getExamName(),
                e.getExamType(),
                e.getStartDate(),
                e.getEndDate()
        );
    }
}