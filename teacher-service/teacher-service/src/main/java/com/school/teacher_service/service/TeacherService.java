package com.school.teacher_service.service;

import com.school.teacher_service.dto.TeacherRequest;
import com.school.teacher_service.dto.TeacherResponse;
import com.school.teacher_service.entity.Teacher;
import com.school.teacher_service.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    public TeacherResponse createTeacher(TeacherRequest request) {
        if (teacherRepository.existsByEmployeeId(request.getEmployeeId())) {
            throw new RuntimeException("Employee ID already exists: " + request.getEmployeeId());
        }

        Teacher teacher = new Teacher();
        teacher.setEmployeeId(request.getEmployeeId());
        teacher.setFirstName(request.getFirstName());
        teacher.setLastName(request.getLastName());
        teacher.setPhone(request.getPhone());
        teacher.setEmail(request.getEmail());
        teacher.setQualification(request.getQualification());
        teacher.setSpecialization(request.getSpecialization());
        teacher.setJoiningDate(request.getJoiningDate());
        teacher.setStatus(true);

        Teacher saved = teacherRepository.save(teacher);
        return TeacherResponse.fromEntity(saved);
    }

    public Page<TeacherResponse> getTeachers(String search, Pageable pageable) {
        return teacherRepository.searchTeachers(search, pageable).map(TeacherResponse::fromEntity);
    }

    public TeacherResponse getTeacherById(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + id));
        return TeacherResponse.fromEntity(teacher);
    }

    public TeacherResponse updateTeacher(Long id, TeacherRequest request) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + id));

        if (!teacher.getEmployeeId().equals(request.getEmployeeId())
                && teacherRepository.existsByEmployeeId(request.getEmployeeId())) {
            throw new RuntimeException("Employee ID already exists: " + request.getEmployeeId());
        }

        teacher.setEmployeeId(request.getEmployeeId());
        teacher.setFirstName(request.getFirstName());
        teacher.setLastName(request.getLastName());
        teacher.setPhone(request.getPhone());
        teacher.setEmail(request.getEmail());
        teacher.setQualification(request.getQualification());
        teacher.setSpecialization(request.getSpecialization());
        teacher.setJoiningDate(request.getJoiningDate());

        Teacher updated = teacherRepository.save(teacher);
        return TeacherResponse.fromEntity(updated);
    }

    public TeacherResponse updateStatus(Long id, boolean status) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + id));
        teacher.setStatus(status);
        Teacher updated = teacherRepository.save(teacher);
        return TeacherResponse.fromEntity(updated);
    }

    public void deleteTeacher(Long id) {
        if (!teacherRepository.existsById(id)) {
            throw new RuntimeException("Teacher not found with id: " + id);
        }
        teacherRepository.deleteById(id);
    }
}