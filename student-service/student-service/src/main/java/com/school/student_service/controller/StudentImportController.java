package com.school.student_service.controller;

import com.school.student_service.dto.ImportResultResponse;
import com.school.student_service.service.StudentImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/import")
public class StudentImportController {

    @Autowired
    private StudentImportService studentImportService;

    @PostMapping("/students")
    public ResponseEntity<?> importStudents(@RequestParam("file") MultipartFile file) {
        try {
            ImportResultResponse result = studentImportService.importStudents(file);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", result);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
}