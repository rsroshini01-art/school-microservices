package com.school.communication_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "documents")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String storedFileName; // the actual name saved on disk (randomized to avoid clashes)

    @Column(nullable = false)
    private String fileType; // e.g. "application/pdf"

    @Column(nullable = false)
    private Long fileSize; // in bytes

    // the user lives in auth-service, so we store only the id
    @Column(name = "uploaded_by", nullable = false)
    private Long uploadedById;

    @Column(updatable = false)
    private LocalDateTime uploadedAt = LocalDateTime.now();
}