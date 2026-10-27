package com.school.exam_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "homework_submissions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomeworkSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "homework_id", nullable = false)
    private Homework homework;

    // student lives in student-service: id only
    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @Column(columnDefinition = "TEXT")
    private String submissionText;

    private String attachmentUrl;

    @Column(nullable = false)
    private LocalDateTime submittedAt = LocalDateTime.now();

    private Double marksAwarded;

    @Column(columnDefinition = "TEXT")
    private String feedback;

    @Column(nullable = false)
    private String status = "SUBMITTED"; // SUBMITTED, GRADED, LATE
}