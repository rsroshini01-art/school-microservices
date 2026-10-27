package com.school.fee_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FeePaymentRequest {

    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotNull(message = "Fee structure ID is required")
    private Long feeStructureId;

    @NotNull(message = "Amount paid is required")
    private Double amountPaid;

    @NotNull(message = "Payment date is required")
    private LocalDate paymentDate;

    private String paymentMethod;
    private String transactionReference;
}