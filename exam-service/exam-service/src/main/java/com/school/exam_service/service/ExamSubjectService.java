package com.school.exam_service.service;

import com.school.exam_service.dto.ExamSubjectRequest;
import com.school.exam_service.dto.ExamSubjectResponse;
import com.school.exam_service.entity.Exam;
import com.school.exam_service.entity.ExamSubject;
import com.school.exam_service.repository.ExamRepository;
import com.school.exam_service.repository.ExamSubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExamSubjectService {

    @Autowired
    private ExamSubjectRepository examSubjectRepository;

    @Autowired
    private ExamRepository examRepository;

    // The subject lives in academic-service, so only its id is stored here.
    // Checking that the subject id exists will be added in Step 6 (OpenFeign).

    public ExamSubjectResponse addSubjectToExam(Long examId, ExamSubjectRequest request) {
        if (examSubjectRepository.findByExamIdAndSubjectId(examId, request.getSubjectId()).isPresent()) {
            throw new RuntimeException("This subject is already configured for this exam");
        }

        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found with id: " + examId));

        ExamSubject examSubject = new ExamSubject();
        examSubject.setExam(exam);
        examSubject.setSubjectId(request.getSubjectId());
        examSubject.setMaxMarks(request.getMaxMarks());

        ExamSubject saved = examSubjectRepository.save(examSubject);
        return ExamSubjectResponse.fromEntity(saved);
    }

    public List<ExamSubjectResponse> getByExam(Long examId) {
        return examSubjectRepository.findByExamId(examId)
                .stream().map(ExamSubjectResponse::fromEntity).collect(Collectors.toList());
    }

    public ExamSubjectResponse updateMaxMarks(Long examId, Long subjectId, ExamSubjectRequest request) {
        ExamSubject examSubject = examSubjectRepository.findByExamIdAndSubjectId(examId, subjectId)
                .orElseThrow(() -> new RuntimeException("This subject is not configured for this exam"));
        examSubject.setMaxMarks(request.getMaxMarks());
        ExamSubject updated = examSubjectRepository.save(examSubject);
        return ExamSubjectResponse.fromEntity(updated);
    }

    public void removeSubjectFromExam(Long examId, Long subjectId) {
        ExamSubject examSubject = examSubjectRepository.findByExamIdAndSubjectId(examId, subjectId)
                .orElseThrow(() -> new RuntimeException("This subject is not configured for this exam"));
        examSubjectRepository.delete(examSubject);
    }
}