package com.school.exam_service.controller;

import com.school.exam_service.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/results")
public class ResultController {

    @Autowired
    private ResultService resultService;

    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getAllResultsForStudent(@PathVariable Long studentId) {
        try {
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", resultService.getAllResultsForStudent(studentId));
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @GetMapping("/class/{classId}/exam/{examId}")
    public ResponseEntity<?> getClassResults(@PathVariable Long classId, @PathVariable Long examId) {
        try {
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", resultService.getClassResults(classId, examId));
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @GetMapping("/student/{studentId}/exam/{examId}")
    public ResponseEntity<?> getResult(@PathVariable Long studentId, @PathVariable Long examId) {
        try {
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", resultService.getResult(studentId, examId));
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}