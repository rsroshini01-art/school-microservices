package com.school.exam_service.controller;

import com.school.exam_service.dto.ExamSubjectRequest;
import com.school.exam_service.dto.ExamSubjectResponse;
import com.school.exam_service.service.ExamSubjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/exams/{examId}/subjects")
public class ExamSubjectController {

    @Autowired
    private ExamSubjectService examSubjectService;

    @PostMapping
    public ResponseEntity<?> add(@PathVariable Long examId, @Valid @RequestBody ExamSubjectRequest request) {
        try {
            ExamSubjectResponse es = examSubjectService.addSubjectToExam(examId, request);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Subject added to exam");
            response.put("data", es);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll(@PathVariable Long examId) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", examSubjectService.getByExam(examId));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{subjectId}")
    public ResponseEntity<?> update(@PathVariable Long examId, @PathVariable Long subjectId, @Valid @RequestBody ExamSubjectRequest request) {
        try {
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Max marks updated");
            response.put("data", examSubjectService.updateMaxMarks(examId, subjectId, request));
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @DeleteMapping("/{subjectId}")
    public ResponseEntity<?> remove(@PathVariable Long examId, @PathVariable Long subjectId) {
        try {
            examSubjectService.removeSubjectFromExam(examId, subjectId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}