package com.school.exam_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "homework")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Homework {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // class, section and subject live in academic-service; teacher lives in teacher-service: ids only
    @Column(name = "class_id", nullable = false)
    private Long classId;

    @Column(name = "section_id")
    private Long sectionId;

    @Column(name = "subject_id", nullable = false)
    private Long subjectId;

    @Column(name = "teacher_id")
    private Long teacherId;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private LocalDate dueDate;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}