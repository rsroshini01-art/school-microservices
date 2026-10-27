package com.school.exam_service.service;

import com.school.exam_service.dto.GradeSubmissionRequest;
import com.school.exam_service.dto.HomeworkSubmissionRequest;
import com.school.exam_service.dto.HomeworkSubmissionResponse;
import com.school.exam_service.entity.Homework;
import com.school.exam_service.entity.HomeworkSubmission;
import com.school.exam_service.repository.HomeworkRepository;
import com.school.exam_service.repository.HomeworkSubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HomeworkSubmissionService {

    @Autowired
    private HomeworkSubmissionRepository submissionRepository;

    @Autowired
    private HomeworkRepository homeworkRepository;

    // The student lives in student-service, so only the student id is stored here.
    // Checking that the student id exists will be added in Step 6 (OpenFeign).

    public HomeworkSubmissionResponse submit(HomeworkSubmissionRequest request) {
        if (submissionRepository.findByHomeworkIdAndStudentId(request.getHomeworkId(), request.getStudentId()).isPresent()) {
            throw new RuntimeException("Homework already submitted by this student");
        }

        Homework homework = homeworkRepository.findById(request.getHomeworkId())
                .orElseThrow(() -> new RuntimeException("Homework not found with id: " + request.getHomeworkId()));

        HomeworkSubmission submission = new HomeworkSubmission();
        submission.setHomework(homework);
        submission.setStudentId(request.getStudentId());
        submission.setSubmissionText(request.getSubmissionText());
        submission.setAttachmentUrl(request.getAttachmentUrl());
        submission.setSubmittedAt(LocalDateTime.now());

        // Auto-detect late submission
        boolean isLate = LocalDate.now().isAfter(homework.getDueDate());
        submission.setStatus(isLate ? "LATE" : "SUBMITTED");

        HomeworkSubmission saved = submissionRepository.save(submission);
        return HomeworkSubmissionResponse.fromEntity(saved);
    }

    public HomeworkSubmissionResponse grade(Long id, GradeSubmissionRequest request) {
        HomeworkSubmission submission = submissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Submission not found with id: " + id));

        submission.setMarksAwarded(request.getMarksAwarded());
        submission.setFeedback(request.getFeedback());
        submission.setStatus("GRADED");

        HomeworkSubmission updated = submissionRepository.save(submission);
        return HomeworkSubmissionResponse.fromEntity(updated);
    }

    public List<HomeworkSubmissionResponse> getByHomework(Long homeworkId) {
        return submissionRepository.findByHomeworkId(homeworkId)
                .stream().map(HomeworkSubmissionResponse::fromEntity).collect(Collectors.toList());
    }

    public List<HomeworkSubmissionResponse> getByStudent(Long studentId) {
        return submissionRepository.findByStudentId(studentId)
                .stream().map(HomeworkSubmissionResponse::fromEntity).collect(Collectors.toList());
    }
}