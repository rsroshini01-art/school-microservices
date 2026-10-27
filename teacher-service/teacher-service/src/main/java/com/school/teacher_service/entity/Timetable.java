package com.school.teacher_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Table(name = "timetables")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Timetable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // class, section and subject live in academic-service: ids only
    @Column(name = "class_id", nullable = false)
    private Long classId;

    @Column(name = "section_id")
    private Long sectionId;

    @Column(name = "subject_id", nullable = false)
    private Long subjectId;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @Column(nullable = false)
    private String dayOfWeek; // e.g. "MONDAY"

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;
}