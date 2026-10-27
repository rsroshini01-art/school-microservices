package com.school.auth_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username; // stored as text, not a foreign key, so logs survive even if the user is deleted later

    @Column(nullable = false)
    private String action; // CREATE, UPDATE, DELETE, LOGIN, LOGOUT, PAYMENT, MARKS_UPDATE, ATTENDANCE_UPDATE

    @Column(nullable = false)
    private String module; // e.g. "STUDENT", "PAYMENT"

    private String recordId; // e.g. "STU1023"

    @Column(nullable = false)
    private LocalDateTime timestamp = LocalDateTime.now();
}