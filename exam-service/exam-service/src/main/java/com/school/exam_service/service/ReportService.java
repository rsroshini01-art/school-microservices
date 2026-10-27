package com.school.exam_service.service;

import com.school.exam_service.entity.Marks;
import com.school.exam_service.repository.MarksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    private MarksRepository marksRepository;

    // The student, attendance and fee reports now live in their own services.
    public Map<String, Object> getExamReport(Long examId) {
        List<Marks> marksList = marksRepository.findByExamId(examId);

        double avgPercentage = marksList.stream()
                .mapToDouble(m -> (m.getMarksObtained() / m.getMaxMarks()) * 100)
                .average().orElse(0.0);

        Map<String, Long> gradeCounts = marksList.stream()
                .collect(Collectors.groupingBy(Marks::getGrade, Collectors.counting()));

        Map<String, Object> report = new HashMap<>();
        report.put("totalRecords", marksList.size());
        report.put("averagePercentage", Math.round(avgPercentage * 100.0) / 100.0);
        report.put("gradeDistribution", gradeCounts);
        return report;
    }
}