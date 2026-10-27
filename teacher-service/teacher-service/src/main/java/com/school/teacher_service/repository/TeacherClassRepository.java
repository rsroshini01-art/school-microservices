package com.school.teacher_service.repository;

import com.school.teacher_service.entity.TeacherClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeacherClassRepository extends JpaRepository<TeacherClass, Long> {

    List<TeacherClass> findByTeacherId(Long teacherId);

    List<TeacherClass> findByClassId(Long classId);

    Optional<TeacherClass> findByTeacherIdAndClassId(Long teacherId, Long classId);
}