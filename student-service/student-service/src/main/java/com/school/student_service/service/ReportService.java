package com.school.student_service.service;

import com.school.student_service.entity.Student;
import com.school.student_service.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    private StudentRepository studentRepository;

    public Map<String, Object> getStudentReport(Long classId) {
        List<Student> students = studentRepository.findAll();
        if (classId != null) {
            students = students.stream()
                    .filter(s -> classId.equals(s.getClassId()))
                    .collect(Collectors.toList());
        }

        Map<String, Object> report = new HashMap<>();
        report.put("totalStudents", students.size());
        report.put("activeStudents", students.stream().filter(Student::isStatus).count());
        report.put("inactiveStudents", students.stream().filter(s -> !s.isStatus()).count());
        return report;
    }
}