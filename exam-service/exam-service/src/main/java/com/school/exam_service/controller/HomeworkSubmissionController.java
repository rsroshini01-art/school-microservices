package com.school.exam_service.controller;

import com.school.exam_service.dto.GradeSubmissionRequest;
import com.school.exam_service.dto.HomeworkSubmissionRequest;
import com.school.exam_service.dto.HomeworkSubmissionResponse;
import com.school.exam_service.service.HomeworkSubmissionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/homework-submissions")
public class HomeworkSubmissionController {

    @Autowired
    private HomeworkSubmissionService submissionService;

    @PostMapping
    public ResponseEntity<?> submit(@Valid @RequestBody HomeworkSubmissionRequest request) {
        try {
            HomeworkSubmissionResponse s = submissionService.submit(request);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Homework submitted successfully");
            response.put("data", s);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @PatchMapping("/{id}/grade")
    public ResponseEntity<?> grade(@PathVariable Long id, @Valid @RequestBody GradeSubmissionRequest request) {
        try {
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Submission graded successfully");
            response.put("data", submissionService.grade(id, request));
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @GetMapping("/homework/{homeworkId}")
    public ResponseEntity<?> getByHomework(@PathVariable Long homeworkId) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", submissionService.getByHomework(homeworkId));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getByStudent(@PathVariable Long studentId) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", submissionService.getByStudent(studentId));
        return ResponseEntity.ok(response);
    }
}