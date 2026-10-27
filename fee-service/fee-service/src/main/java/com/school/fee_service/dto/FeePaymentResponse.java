package com.school.fee_service.dto;

import com.school.fee_service.entity.FeePayment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeePaymentResponse {

    private Long id;
    private Long studentId;
    private String studentName;   // filled in Step 6 (from student-service)
    private Long feeStructureId;
    private String feeName;
    private Double feeAmount;
    private Double amountPaid;
    private Double balance;
    private LocalDate paymentDate;
    private String paymentMethod;
    private String transactionReference;

    public static FeePaymentResponse fromEntity(FeePayment p) {
        double balance = p.getFeeStructure().getAmount() - p.getAmountPaid();
        return new FeePaymentResponse(
                p.getId(),
                p.getStudentId(),
                null,
                p.getFeeStructure().getId(),
                p.getFeeStructure().getFeeName(),
                p.getFeeStructure().getAmount(),
                p.getAmountPaid(),
                balance,
                p.getPaymentDate(),
                p.getPaymentMethod(),
                p.getTransactionReference()
        );
    }
}