package com.school.fee_service.controller;

import com.school.fee_service.dto.FeeStructureRequest;
import com.school.fee_service.dto.FeeStructureResponse;
import com.school.fee_service.service.FeeStructureService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/fee-structures")
public class FeeStructureController {

    @Autowired
    private FeeStructureService feeStructureService;

    @PostMapping
    
    public ResponseEntity<?> create(@Valid @RequestBody FeeStructureRequest request) {
        try {
            FeeStructureResponse f = feeStructureService.createFeeStructure(request);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Fee structure created successfully");
            response.put("data", f);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam Long classId) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", feeStructureService.getByClass(classId));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            feeStructureService.deleteFeeStructure(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}