package com.school.exam_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "grade_rules")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GradeRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double minPercentage;

    @Column(nullable = false)
    private Double maxPercentage;

    @Column(nullable = false)
    private String grade;

    @Column(nullable = false)
    private boolean passGrade = true; // false = fail (e.g. "F")
}