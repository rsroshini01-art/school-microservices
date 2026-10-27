package com.school.student_service.controller;

import com.school.student_service.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/students")
    public Map<String, Object> studentReport(@RequestParam(required = false) Long classId) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", reportService.getStudentReport(classId));
        return response;
    }
}