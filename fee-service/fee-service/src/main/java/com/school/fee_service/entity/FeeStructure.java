package com.school.fee_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fee_structures")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeeStructure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // the class lives in academic-service, so we store only its id
    @Column(name = "class_id", nullable = false)
    private Long classId;

    @Column(nullable = false)
    private String feeName; // e.g. "Tuition Fee - Term 1"

    @Column(nullable = false)
    private Double amount;

    private String frequency; // e.g. "TERM", "MONTHLY", "ANNUAL"
}