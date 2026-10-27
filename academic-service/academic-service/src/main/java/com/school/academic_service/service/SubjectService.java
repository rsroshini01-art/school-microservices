package com.school.academic_service.service;

import com.school.academic_service.dto.SubjectRequest;
import com.school.academic_service.dto.SubjectResponse;
import com.school.academic_service.entity.SchoolClass;
import com.school.academic_service.entity.Subject;
import com.school.academic_service.repository.SchoolClassRepository;
import com.school.academic_service.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private SchoolClassRepository schoolClassRepository;

    // The teacher lives in teacher-service, so only the teacher id is stored here.
    // Checking that the teacher id exists will be added in Step 6 (OpenFeign).

    public SubjectResponse createSubject(SubjectRequest request) {
        SchoolClass schoolClass = schoolClassRepository.findById(request.getClassId())
                .orElseThrow(() -> new RuntimeException("Class not found with id: " + request.getClassId()));

        Subject subject = new Subject();
        subject.setSubjectName(request.getSubjectName());
        subject.setSubjectCode(request.getSubjectCode());
        subject.setSchoolClass(schoolClass);
        subject.setTeacherId(request.getTeacherId());

        Subject saved = subjectRepository.save(subject);
        return SubjectResponse.fromEntity(saved);
    }

    public List<SubjectResponse> getSubjects(Long classId) {
        return subjectRepository.findBySchoolClassId(classId)
                .stream().map(SubjectResponse::fromEntity).collect(Collectors.toList());
    }

    public SubjectResponse getSubjectById(Long id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found with id: " + id));
        return SubjectResponse.fromEntity(subject);
    }

    public SubjectResponse updateSubject(Long id, SubjectRequest request) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found with id: " + id));

        SchoolClass schoolClass = schoolClassRepository.findById(request.getClassId())
                .orElseThrow(() -> new RuntimeException("Class not found with id: " + request.getClassId()));

        subject.setSubjectName(request.getSubjectName());
        subject.setSubjectCode(request.getSubjectCode());
        subject.setSchoolClass(schoolClass);
        subject.setTeacherId(request.getTeacherId());

        Subject updated = subjectRepository.save(subject);
        return SubjectResponse.fromEntity(updated);
    }

    public void deleteSubject(Long id) {
        if (!subjectRepository.existsById(id)) {
            throw new RuntimeException("Subject not found with id: " + id);
        }
        subjectRepository.deleteById(id);
    }
}