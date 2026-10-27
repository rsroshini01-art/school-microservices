package com.school.exam_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "exams")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // academic year and term live in academic-service: ids only
    @Column(name = "academic_year_id", nullable = false)
    private Long academicYearId;

    @Column(name = "term_id")
    private Long termId;

    @Column(nullable = false)
    private String examName; // e.g. "Midterm Exam"

    private String examType; // e.g. "UNIT_TEST", "MIDTERM", "FINAL"

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;
}