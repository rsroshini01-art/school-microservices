package com.school.exam_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "exam_subjects")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "exam_id", nullable = false)
    private Exam exam;

    // subject lives in academic-service: id only
    @Column(name = "subject_id", nullable = false)
    private Long subjectId;

    @Column(nullable = false)
    private Double maxMarks;
}