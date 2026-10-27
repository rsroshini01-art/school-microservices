package com.school.fee_service.service;

import com.school.fee_service.entity.FeePayment;
import com.school.fee_service.repository.FeePaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private FeePaymentRepository feePaymentRepository;

    public Map<String, Object> getFeeReport() {
        List<FeePayment> payments = feePaymentRepository.findAll();

        double totalCollected = payments.stream().mapToDouble(FeePayment::getAmountPaid).sum();

        Map<String, Object> report = new HashMap<>();
        report.put("totalPayments", payments.size());
        report.put("totalAmountCollected", totalCollected);
        return report;
    }
}