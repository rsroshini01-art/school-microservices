package com.school.academic_service.controller;

import com.school.academic_service.dto.AcademicYearRequest;
import com.school.academic_service.dto.AcademicYearResponse;
import com.school.academic_service.service.AcademicYearService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/academic-years")
public class AcademicYearController {

    @Autowired
    private AcademicYearService academicYearService;

    @PostMapping
    
    public ResponseEntity<?> create(@Valid @RequestBody AcademicYearRequest request) {
        try {
            AcademicYearResponse year = academicYearService.createAcademicYear(request);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Academic year created successfully");
            response.put("data", year);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam Long schoolId) {
        List<AcademicYearResponse> years = academicYearService.getAcademicYears(schoolId);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", years);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            AcademicYearResponse year = academicYearService.getAcademicYearById(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", year);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<?> activate(@PathVariable Long id) {
        try {
            AcademicYearResponse year = academicYearService.activateAcademicYear(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Academic year activated");
            response.put("data", year);
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
            academicYearService.deleteAcademicYear(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
}