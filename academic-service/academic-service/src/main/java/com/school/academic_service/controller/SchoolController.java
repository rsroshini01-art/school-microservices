package com.school.academic_service.controller;

import com.school.academic_service.dto.SchoolRequest;
import com.school.academic_service.dto.SchoolResponse;
import com.school.academic_service.service.SchoolService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/schools")
public class SchoolController {

    @Autowired
    private SchoolService schoolService;

    @PostMapping
    
    public ResponseEntity<?> createSchool(@Valid @RequestBody SchoolRequest request) {
        SchoolResponse school = schoolService.createSchool(request);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "School created successfully");
        response.put("data", school);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<?> getSchools(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int limit
    ) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<SchoolResponse> result = schoolService.getSchools(search, pageable);

        Map<String, Object> pagination = new HashMap<>();
        pagination.put("page", page);
        pagination.put("limit", limit);
        pagination.put("total", result.getTotalElements());
        pagination.put("total_pages", result.getTotalPages());

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", result.getContent());
        response.put("pagination", pagination);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSchoolById(@PathVariable Long id) {
        try {
            SchoolResponse school = schoolService.getSchoolById(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", school);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSchool(@PathVariable Long id, @Valid @RequestBody SchoolRequest request) {
        try {
            SchoolResponse school = schoolService.updateSchool(id, request);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "School updated successfully");
            response.put("data", school);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @DeleteMapping("/{id}")
    
    public ResponseEntity<?> deleteSchool(@PathVariable Long id) {
        try {
            schoolService.deleteSchool(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}