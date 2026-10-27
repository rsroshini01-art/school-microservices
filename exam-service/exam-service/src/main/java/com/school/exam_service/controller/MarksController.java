package com.school.exam_service.controller;

import com.school.exam_service.dto.MarksRequest;
import com.school.exam_service.dto.MarksResponse;
import com.school.exam_service.service.MarksService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/marks")
public class MarksController {

    @Autowired
    private MarksService marksService;

    @PostMapping
    
    public ResponseEntity<?> add(@Valid @RequestBody MarksRequest request) {
        try {
            MarksResponse m = marksService.addMarks(request);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Marks recorded successfully");
            response.put("data", m);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @GetMapping("/student/{studentId}/exam/{examId}")
    public ResponseEntity<?> getByStudentAndExam(@PathVariable Long studentId, @PathVariable Long examId) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", marksService.getByStudentAndExam(studentId, examId));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/exam/{examId}")
    public ResponseEntity<?> getByExam(@PathVariable Long examId) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", marksService.getByExam(examId));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody MarksRequest request) {
        try {
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Marks updated successfully");
            response.put("data", marksService.updateMarks(id, request));
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            marksService.deleteMarks(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}