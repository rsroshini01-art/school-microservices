package com.school.exam_service.controller;

import com.school.exam_service.service.ReportCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/report-cards")
public class ReportCardController {

    @Autowired
    private ReportCardService reportCardService;

    @GetMapping("/student/{studentId}/exam/{examId}")
    public ResponseEntity<byte[]> getReportCard(@PathVariable Long studentId, @PathVariable Long examId) {
        byte[] pdfBytes = reportCardService.generateReportCard(studentId, examId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "report-card-" + studentId + "-" + examId + ".pdf");

        return ResponseEntity.ok().headers(headers).body(pdfBytes);
    }
}