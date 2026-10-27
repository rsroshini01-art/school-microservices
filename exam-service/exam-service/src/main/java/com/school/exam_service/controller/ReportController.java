package com.school.exam_service.controller;

import com.school.exam_service.service.ReportService;
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

    @GetMapping("/exams")
    public Map<String, Object> examReport(@RequestParam Long examId) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", reportService.getExamReport(examId));
        return response;
    }
}