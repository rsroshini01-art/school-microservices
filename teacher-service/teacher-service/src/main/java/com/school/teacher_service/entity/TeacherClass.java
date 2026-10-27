package com.school.teacher_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "teacher_classes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    // class lives in academic-service, so we store only its id
    @Column(name = "class_id", nullable = false)
    private Long classId;
}