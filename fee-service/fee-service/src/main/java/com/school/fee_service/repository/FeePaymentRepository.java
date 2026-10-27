package com.school.fee_service.repository;

import com.school.fee_service.entity.FeePayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeePaymentRepository extends JpaRepository<FeePayment, Long> {
    List<FeePayment> findByStudentId(Long studentId);
    List<FeePayment> findByStudentIdAndFeeStructureId(Long studentId, Long feeStructureId);
}