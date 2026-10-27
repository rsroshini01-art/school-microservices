package com.school.fee_service.controller;

import com.school.fee_service.dto.FeePaymentRequest;
import com.school.fee_service.dto.FeePaymentResponse;
import com.school.fee_service.service.FeePaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/fee-payments")
public class FeePaymentController {

    @Autowired
    private FeePaymentService feePaymentService;

    @PostMapping
    
    public ResponseEntity<?> record(@Valid @RequestBody FeePaymentRequest request) {
        try {
            FeePaymentResponse p = feePaymentService.recordPayment(request);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Payment recorded successfully");
            response.put("data", p);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getByStudent(@PathVariable Long studentId) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", feePaymentService.getByStudent(studentId));
        return ResponseEntity.ok(response);
    }
}