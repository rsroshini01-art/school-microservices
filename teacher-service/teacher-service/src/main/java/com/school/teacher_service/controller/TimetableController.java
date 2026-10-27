package com.school.teacher_service.controller;

import com.school.teacher_service.dto.TimetableRequest;
import com.school.teacher_service.dto.TimetableResponse;
import com.school.teacher_service.service.TimetableService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/timetable")
public class TimetableController {

    @Autowired
    private TimetableService timetableService;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody TimetableRequest request) {
        try {
            TimetableResponse t = timetableService.createTimetable(request);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Timetable entry created successfully");
            response.put("data", t);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam Long classId, @RequestParam(required = false) Long sectionId) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", timetableService.getTimetable(classId, sectionId));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", timetableService.getById(id));
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
            timetableService.deleteTimetable(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}