package com.school.exam_service.service;

import com.school.exam_service.dto.HomeworkRequest;
import com.school.exam_service.dto.HomeworkResponse;
import com.school.exam_service.entity.Homework;
import com.school.exam_service.repository.HomeworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HomeworkService {

    @Autowired
    private HomeworkRepository homeworkRepository;

    // Class, section and subject live in academic-service; the teacher lives in teacher-service.
    // Only their ids are stored. Checking that the ids exist will be added in Step 6 (OpenFeign).

    public HomeworkResponse createHomework(HomeworkRequest request) {
        Homework homework = new Homework();
        homework.setClassId(request.getClassId());
        homework.setSectionId(request.getSectionId());
        homework.setSubjectId(request.getSubjectId());
        homework.setTeacherId(request.getTeacherId());
        homework.setTitle(request.getTitle());
        homework.setDescription(request.getDescription());
        homework.setDueDate(request.getDueDate());

        Homework saved = homeworkRepository.save(homework);
        return HomeworkResponse.fromEntity(saved);
    }

    public List<HomeworkResponse> getHomework(Long classId, Long sectionId) {
        return homeworkRepository.findByClassIdAndSectionId(classId, sectionId)
                .stream().map(HomeworkResponse::fromEntity).collect(Collectors.toList());
    }

    public HomeworkResponse getById(Long id) {
        Homework h = homeworkRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Homework not found with id: " + id));
        return HomeworkResponse.fromEntity(h);
    }

    public HomeworkResponse updateHomework(Long id, HomeworkRequest request) {
        Homework homework = homeworkRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Homework not found with id: " + id));

        homework.setTitle(request.getTitle());
        homework.setDescription(request.getDescription());
        homework.setDueDate(request.getDueDate());

        Homework updated = homeworkRepository.save(homework);
        return HomeworkResponse.fromEntity(updated);
    }

    public void deleteHomework(Long id) {
        if (!homeworkRepository.existsById(id)) {
            throw new RuntimeException("Homework not found with id: " + id);
        }
        homeworkRepository.deleteById(id);
    }
}