package com.school.teacher_service.controller;

import com.school.teacher_service.dto.TeacherClassResponse;
import com.school.teacher_service.service.TeacherClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/teacher-classes")
public class TeacherClassController {

    @Autowired
    private TeacherClassService teacherClassService;

    @PostMapping
    public ResponseEntity<?> assign(@RequestParam Long teacherId, @RequestParam Long classId) {
        try {
            TeacherClassResponse tc = teacherClassService.assign(teacherId, classId);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Teacher assigned to class");
            response.put("data", tc);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<?> getByTeacher(@PathVariable Long teacherId) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", teacherClassService.getByTeacher(teacherId));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/class/{classId}")
    public ResponseEntity<?> getByClass(@PathVariable Long classId) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", teacherClassService.getByClass(classId));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<?> remove(@RequestParam Long teacherId, @RequestParam Long classId) {
        try {
            teacherClassService.remove(teacherId, classId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}