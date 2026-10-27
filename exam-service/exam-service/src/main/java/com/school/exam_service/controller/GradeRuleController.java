package com.school.exam_service.controller;

import com.school.exam_service.dto.GradeRuleRequest;
import com.school.exam_service.dto.GradeRuleResponse;
import com.school.exam_service.service.GradeRuleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/grade-rules")
public class GradeRuleController {

    @Autowired
    private GradeRuleService gradeRuleService;

    @PostMapping
    
    public ResponseEntity<?> create(@Valid @RequestBody GradeRuleRequest request) {
        try {
            GradeRuleResponse r = gradeRuleService.createRule(request);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Grade rule created successfully");
            response.put("data", r);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", gradeRuleService.getAllRules());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody GradeRuleRequest request) {
        try {
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Grade rule updated successfully");
            response.put("data", gradeRuleService.updateRule(id, request));
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            gradeRuleService.deleteRule(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}