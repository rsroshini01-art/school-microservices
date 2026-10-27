package com.school.fee_service.service;

import com.school.fee_service.client.StudentClient;
import com.school.fee_service.dto.FeePaymentRequest;
import com.school.fee_service.dto.FeePaymentResponse;
import com.school.fee_service.entity.FeePayment;
import com.school.fee_service.entity.FeeStructure;
import com.school.fee_service.repository.FeePaymentRepository;
import com.school.fee_service.repository.FeeStructureRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FeePaymentService {

    @Autowired
    private FeePaymentRepository feePaymentRepository;

    @Autowired
    private FeeStructureRepository feeStructureRepository;

    @Autowired
    private StudentClient studentClient;

    // Asks student-service for the student. Throws an error if the student does not exist.
    @SuppressWarnings("unchecked")
    private String fetchStudentName(Long studentId) {
        try {
            Map<String, Object> body = studentClient.getStudent(studentId);
            Map<String, Object> data = (Map<String, Object>) body.get("data");
            return data.get("firstName") + " " + data.get("lastName");
        } catch (FeignException.NotFound e) {
            throw new RuntimeException("Student not found with id: " + studentId);
        } catch (FeignException e) {
            throw new RuntimeException("Student service is not available right now, please try again");
        }
    }

    @Transactional
    public FeePaymentResponse recordPayment(FeePaymentRequest request) {
        FeeStructure feeStructure = feeStructureRepository.findById(request.getFeeStructureId())
                .orElseThrow(() -> new RuntimeException("Fee structure not found with id: " + request.getFeeStructureId()));

        if (request.getAmountPaid() <= 0) {
            throw new RuntimeException("Payment amount must be greater than zero");
        }

        String studentName = fetchStudentName(request.getStudentId());

        double alreadyPaid = feePaymentRepository.findByStudentIdAndFeeStructureId(request.getStudentId(), request.getFeeStructureId())
                .stream().mapToDouble(FeePayment::getAmountPaid).sum();

        if (alreadyPaid + request.getAmountPaid() > feeStructure.getAmount()) {
            throw new RuntimeException("Payment exceeds the remaining fee balance. Remaining: "
                    + (feeStructure.getAmount() - alreadyPaid));
        }

        FeePayment payment = new FeePayment();
        payment.setStudentId(request.getStudentId());
        payment.setFeeStructure(feeStructure);
        payment.setAmountPaid(request.getAmountPaid());
        payment.setPaymentDate(request.getPaymentDate());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setTransactionReference(request.getTransactionReference());

        FeePayment saved = feePaymentRepository.save(payment);
        FeePaymentResponse response = FeePaymentResponse.fromEntity(saved);
        response.setStudentName(studentName);
        return response;
    }

    public List<FeePaymentResponse> getByStudent(Long studentId) {
        String studentName = fetchStudentName(studentId);
        return feePaymentRepository.findByStudentId(studentId)
                .stream().map(FeePaymentResponse::fromEntity)
                .peek(r -> r.setStudentName(studentName))
                .collect(Collectors.toList());
    }
}