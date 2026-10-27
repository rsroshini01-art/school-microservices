package com.school.academic_service.dto;

import com.school.academic_service.entity.Term;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TermResponse {

    private Long id;
    private Long academicYearId;
    private String termName;
    private LocalDate startDate;
    private LocalDate endDate;

    public static TermResponse fromEntity(Term term) {
        return new TermResponse(
                term.getId(),
                term.getAcademicYear().getId(),
                term.getTermName(),
                term.getStartDate(),
                term.getEndDate()
        );
    }
}