package com.school.communication_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "announcements")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TargetAudience targetAudience;

    // the class lives in academic-service, so we store only its id
    // (only used when targetAudience = CLASS)
    @Column(name = "class_id")
    private Long classId;

    @Column(nullable = false)
    private boolean published = false;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}