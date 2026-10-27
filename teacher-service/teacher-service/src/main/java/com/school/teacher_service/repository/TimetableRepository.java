package com.school.teacher_service.repository;

import com.school.teacher_service.entity.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimetableRepository extends JpaRepository<Timetable, Long> {

    List<Timetable> findByClassIdAndSectionId(Long classId, Long sectionId);

    List<Timetable> findByTeacherIdAndDayOfWeek(Long teacherId, String dayOfWeek);
}