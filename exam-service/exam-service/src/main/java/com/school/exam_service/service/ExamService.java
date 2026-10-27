package com.school.exam_service.service;

import com.school.exam_service.dto.ExamRequest;
import com.school.exam_service.dto.ExamResponse;
import com.school.exam_service.entity.Exam;
import com.school.exam_service.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExamService {

    @Autowired
    private ExamRepository examRepository;

    // Academic year and term live in academic-service, so only their ids are stored.
    // Checking that those ids exist will be added in Step 6 (OpenFeign).

    public ExamResponse createExam(ExamRequest request) {
        if (!request.getStartDate().isBefore(request.getEndDate()) && !request.getStartDate().isEqual(request.getEndDate())) {
            throw new RuntimeException("Start date must be before or equal to end date");
        }

        Exam exam = new Exam();
        exam.setAcademicYearId(request.getAcademicYearId());
        exam.setTermId(request.getTermId());
        exam.setExamName(request.getExamName());
        exam.setExamType(request.getExamType());
        exam.setStartDate(request.getStartDate());
        exam.setEndDate(request.getEndDate());

        Exam saved = examRepository.save(exam);
        return ExamResponse.fromEntity(saved);
    }

    public List<ExamResponse> getExams(Long academicYearId) {
        return examRepository.findByAcademicYearId(academicYearId)
                .stream().map(ExamResponse::fromEntity).collect(Collectors.toList());
    }

    public ExamResponse getById(Long id) {
        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam not found with id: " + id));
        return ExamResponse.fromEntity(exam);
    }

    public ExamResponse updateExam(Long id, ExamRequest request) {
        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam not found with id: " + id));

        exam.setExamName(request.getExamName());
        exam.setExamType(request.getExamType());
        exam.setStartDate(request.getStartDate());
        exam.setEndDate(request.getEndDate());

        Exam updated = examRepository.save(exam);
        return ExamResponse.fromEntity(updated);
    }

    public void deleteExam(Long id) {
        if (!examRepository.existsById(id)) {
            throw new RuntimeException("Exam not found with id: " + id);
        }
        examRepository.deleteById(id);
    }
}