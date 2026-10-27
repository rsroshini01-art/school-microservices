package com.school.academic_service.controller;

import com.school.academic_service.dto.SchoolClassRequest;
import com.school.academic_service.dto.SchoolClassResponse;
import com.school.academic_service.service.SchoolClassService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/classes")
public class SchoolClassController {

    @Autowired
    private SchoolClassService schoolClassService;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody SchoolClassRequest request) {
        try {
            SchoolClassResponse sc = schoolClassService.createClass(request);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Class created successfully");
            response.put("data", sc);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam Long academicYearId) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", schoolClassService.getClasses(academicYearId));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", schoolClassService.getClassById(id));
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody SchoolClassRequest request) {
        try {
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Class updated successfully");
            response.put("data", schoolClassService.updateClass(id, request));
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
            schoolClassService.deleteClass(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}