package com.school.attendance_service.service;

import com.school.attendance_service.entity.Attendance;
import com.school.attendance_service.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    public Map<String, Object> getAttendanceReport(LocalDate dateFrom, LocalDate dateTo) {
        List<Attendance> all = attendanceRepository.findAll();
        List<Attendance> filtered = all.stream()
                .filter(a -> (dateFrom == null || !a.getAttendanceDate().isBefore(dateFrom))
                        && (dateTo == null || !a.getAttendanceDate().isAfter(dateTo)))
                .collect(Collectors.toList());

        Map<String, Long> statusCounts = filtered.stream()
                .collect(Collectors.groupingBy(a -> a.getStatus().name(), Collectors.counting()));

        Map<String, Object> report = new HashMap<>();
        report.put("totalRecords", filtered.size());
        report.put("byStatus", statusCounts);
        return report;
    }
}