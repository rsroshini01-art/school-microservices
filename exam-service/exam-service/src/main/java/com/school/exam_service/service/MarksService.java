package com.school.exam_service.service;

import com.school.exam_service.dto.MarksRequest;
import com.school.exam_service.dto.MarksResponse;
import com.school.exam_service.entity.Exam;
import com.school.exam_service.entity.Marks;
import com.school.exam_service.repository.ExamRepository;
import com.school.exam_service.repository.MarksRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.school.exam_service.client.StudentClient;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MarksService {

    @Autowired
    private MarksRepository marksRepository;

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private GradeRuleService gradeRuleService;

    @Autowired
    private StudentClient studentClient;

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

    public MarksResponse addMarks(MarksRequest request) {
        if (request.getMarksObtained() > request.getMaxMarks()) {
            throw new RuntimeException("Marks obtained cannot exceed max marks");
        }

        if (marksRepository.findByExamIdAndStudentIdAndSubjectId(
                request.getExamId(), request.getStudentId(), request.getSubjectId()).isPresent()) {
            throw new RuntimeException("Marks already recorded for this student in this subject for this exam");
        }

        Exam exam = examRepository.findById(request.getExamId())
                .orElseThrow(() -> new RuntimeException("Exam not found with id: " + request.getExamId()));

        String studentName = fetchStudentName(request.getStudentId());

        double percentage = (request.getMarksObtained() / request.getMaxMarks()) * 100;

        Marks marks = new Marks();
        marks.setExam(exam);
        marks.setStudentId(request.getStudentId());
        marks.setSubjectId(request.getSubjectId());
        marks.setMarksObtained(request.getMarksObtained());
        marks.setMaxMarks(request.getMaxMarks());
        marks.setGrade(gradeRuleService.resolveGrade(percentage));
        marks.setRemarks(request.getRemarks());

        Marks saved = marksRepository.save(marks);
        MarksResponse response = MarksResponse.fromEntity(saved);
        response.setStudentName(studentName);
        return response;
    }

    public List<MarksResponse> getByStudentAndExam(Long studentId, Long examId) {
        String studentName = fetchStudentName(studentId);
        return marksRepository.findByStudentIdAndExamId(studentId, examId)
                .stream().map(MarksResponse::fromEntity)
                .peek(r -> r.setStudentName(studentName))
                .collect(Collectors.toList());
    }

    public List<MarksResponse> getByExam(Long examId) {
        return marksRepository.findByExamId(examId)
                .stream().map(MarksResponse::fromEntity).collect(Collectors.toList());
    }

    public MarksResponse updateMarks(Long id, MarksRequest request) {
        Marks marks = marksRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Marks record not found with id: " + id));

        if (request.getMarksObtained() > request.getMaxMarks()) {
            throw new RuntimeException("Marks obtained cannot exceed max marks");
        }

        double percentage = (request.getMarksObtained() / request.getMaxMarks()) * 100;

        marks.setMarksObtained(request.getMarksObtained());
        marks.setMaxMarks(request.getMaxMarks());
        marks.setGrade(gradeRuleService.resolveGrade(percentage));
        marks.setRemarks(request.getRemarks());

        Marks updated = marksRepository.save(marks);
        return MarksResponse.fromEntity(updated);
    }

    public void deleteMarks(Long id) {
        if (!marksRepository.existsById(id)) {
            throw new RuntimeException("Marks record not found with id: " + id);
        }
        marksRepository.deleteById(id);
    }
}