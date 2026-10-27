package com.school.exam_service.service;

import com.school.exam_service.client.AcademicClient;
import com.school.exam_service.client.StudentClient;
import com.school.exam_service.dto.MarksResponse;
import com.school.exam_service.dto.ResultResponse;
import com.school.exam_service.entity.Exam;
import com.school.exam_service.entity.Marks;
import com.school.exam_service.repository.ExamRepository;
import com.school.exam_service.repository.MarksRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ResultService {

    @Autowired
    private MarksRepository marksRepository;

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private GradeRuleService gradeRuleService;

    @Autowired
    private StudentClient studentClient;

    @Autowired
    private AcademicClient academicClient;

    // Asks student-service for the student. Throws an error if the student does not exist.
    @SuppressWarnings("unchecked")
    private String fetchStudentName(Long studentId) {
        try {
            Map<String, Object> body = studentClient.getStudent(studentId);
            Map<String, Object> data = (Map<String, Object>) body.get("data");
            return data.get("firstName") + " " + data.get("lastName");
        } catch (FeignException.NotFound e) {
            throw new RuntimeException("Student not found with id: " + studentId);
        } catch (FeignException e) {
            throw new RuntimeException("Student service is not available right now, please try again");
        }
    }

    // Only reads the subject name. If academic-service has no such subject, we show a simple label.
    @SuppressWarnings("unchecked")
    private String findSubjectName(Long subjectId) {
        try {
            Map<String, Object> body = academicClient.getSubject(subjectId);
            Map<String, Object> data = (Map<String, Object>) body.get("data");
            return String.valueOf(data.get("subjectName"));
        } catch (Exception e) {
            return "Subject " + subjectId;
        }
    }

    public List<ResultResponse> getAllResultsForStudent(Long studentId) {
        fetchStudentName(studentId);

        List<Marks> allMarks = marksRepository.findAll().stream()
                .filter(m -> m.getStudentId().equals(studentId))
                .collect(Collectors.toList());

        Map<Long, List<Marks>> byExam = allMarks.stream()
                .collect(Collectors.groupingBy(m -> m.getExam().getId()));

        List<ResultResponse> results = new ArrayList<>();
        for (Long examId : byExam.keySet()) {
            results.add(getResult(studentId, examId));
        }
        return results;
    }

    @SuppressWarnings("unchecked")
    public List<ResultResponse> getClassResults(Long classId, Long examId) {
        List<Map<String, Object>> students;
        try {
            Map<String, Object> body = studentClient.getStudentsByClass(classId, 1000);
            students = (List<Map<String, Object>>) body.get("data");
        } catch (FeignException e) {
            throw new RuntimeException("Student service is not available right now, please try again");
        }

        List<ResultResponse> results = new ArrayList<>();
        for (Map<String, Object> student : students) {
            Long studentId = ((Number) student.get("id")).longValue();
            try {
                results.add(getResult(studentId, examId));
            } catch (RuntimeException e) {
                // skip students with no marks recorded for this exam yet
            }
        }
        return results;
    }

    public ResultResponse getResult(Long studentId, Long examId) {
        String studentName = fetchStudentName(studentId);
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found with id: " + examId));

        List<Marks> marksList = marksRepository.findByStudentIdAndExamId(studentId, examId);

        if (marksList.isEmpty()) {
            throw new RuntimeException("No marks recorded yet for this student in this exam");
        }

        double totalObtained = marksList.stream().mapToDouble(Marks::getMarksObtained).sum();
        double totalMax = marksList.stream().mapToDouble(Marks::getMaxMarks).sum();
        double percentage = (totalObtained / totalMax) * 100;

        String overallGrade = gradeRuleService.resolveGrade(percentage);
        boolean passed = gradeRuleService.isPassing(percentage);

        List<MarksResponse> subjectResponses = marksList.stream().map(m -> {
            MarksResponse r = MarksResponse.fromEntity(m);
            r.setStudentName(studentName);
            r.setSubjectName(findSubjectName(m.getSubjectId()));
            return r;
        }).collect(Collectors.toList());

        ResultResponse result = new ResultResponse();
        result.setStudentName(studentName);
        result.setExamName(exam.getExamName());
        result.setSubjects(subjectResponses);
        result.setTotalMarksObtained(totalObtained);
        result.setTotalMaxMarks(totalMax);
        result.setPercentage(Math.round(percentage * 100.0) / 100.0);
        result.setGrade(overallGrade);
        result.setResult(passed ? "PASS" : "FAIL");

        return result;
    }
}