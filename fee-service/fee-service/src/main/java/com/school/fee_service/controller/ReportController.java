package com.school.fee_service.controller;

import com.school.fee_service.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/fees")
    public Map<String, Object> feeReport() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", reportService.getFeeReport());
        return response;
    }
}