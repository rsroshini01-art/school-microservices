package com.school.teacher_service.service;

import com.school.teacher_service.dto.TeacherClassResponse;
import com.school.teacher_service.entity.Teacher;
import com.school.teacher_service.entity.TeacherClass;
import com.school.teacher_service.repository.TeacherClassRepository;
import com.school.teacher_service.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherClassService {

    @Autowired
    private TeacherClassRepository teacherClassRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    // The class lives in academic-service, so only its id is stored here.
    // Checking that the class id exists will be added in Step 6 (OpenFeign).

    public TeacherClassResponse assign(Long teacherId, Long classId) {
        if (teacherClassRepository.findByTeacherIdAndClassId(teacherId, classId).isPresent()) {
            throw new RuntimeException("This teacher is already assigned to this class");
        }

        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + teacherId));

        TeacherClass teacherClass = new TeacherClass();
        teacherClass.setTeacher(teacher);
        teacherClass.setClassId(classId);

        TeacherClass saved = teacherClassRepository.save(teacherClass);
        return TeacherClassResponse.fromEntity(saved);
    }

    public List<TeacherClassResponse> getByTeacher(Long teacherId) {
        return teacherClassRepository.findByTeacherId(teacherId)
                .stream().map(TeacherClassResponse::fromEntity).collect(Collectors.toList());
    }

    public List<TeacherClassResponse> getByClass(Long classId) {
        return teacherClassRepository.findByClassId(classId)
                .stream().map(TeacherClassResponse::fromEntity).collect(Collectors.toList());
    }

    public void remove(Long teacherId, Long classId) {
        TeacherClass tc = teacherClassRepository.findByTeacherIdAndClassId(teacherId, classId)
                .orElseThrow(() -> new RuntimeException("This teacher is not assigned to this class"));
        teacherClassRepository.delete(tc);
    }
}