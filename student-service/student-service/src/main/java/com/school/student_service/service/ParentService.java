package com.school.student_service.service;

import com.school.student_service.dto.ParentRequest;
import com.school.student_service.dto.ParentResponse;
import com.school.student_service.entity.Parent;
import com.school.student_service.entity.Student;
import com.school.student_service.repository.ParentRepository;
import com.school.student_service.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParentService {

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private StudentRepository studentRepository;

    public ParentResponse createParent(ParentRequest request) {
        Parent parent = new Parent();
        parent.setFirstName(request.getFirstName());
        parent.setLastName(request.getLastName());
        parent.setPhone(request.getPhone());
        parent.setEmail(request.getEmail());
        parent.setOccupation(request.getOccupation());

        Parent saved = parentRepository.save(parent);
        return ParentResponse.fromEntity(saved);
    }

    public List<ParentResponse> getAllParents() {
        return parentRepository.findAll().stream()
                .map(ParentResponse::fromEntity).collect(Collectors.toList());
    }

    public ParentResponse getParentById(Long id) {
        Parent parent = parentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parent not found with id: " + id));
        return ParentResponse.fromEntity(parent);
    }

    // Link a student to a parent (many-to-many)
    public ParentResponse linkStudent(Long parentId, Long studentId) {
        Parent parent = parentRepository.findById(parentId)
                .orElseThrow(() -> new RuntimeException("Parent not found with id: " + parentId));
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));

        parent.getStudents().add(student);
        Parent saved = parentRepository.save(parent);
        return ParentResponse.fromEntity(saved);
    }

    // Unlink a student from a parent
    public ParentResponse unlinkStudent(Long parentId, Long studentId) {
        Parent parent = parentRepository.findById(parentId)
                .orElseThrow(() -> new RuntimeException("Parent not found with id: " + parentId));
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));

        parent.getStudents().remove(student);
        Parent saved = parentRepository.save(parent);
        return ParentResponse.fromEntity(saved);
    }

    public void deleteParent(Long id) {
        if (!parentRepository.existsById(id)) {
            throw new RuntimeException("Parent not found with id: " + id);
        }
        parentRepository.deleteById(id);
    }
}