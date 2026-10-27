package com.school.teacher_service.service;

import com.school.teacher_service.dto.TimetableRequest;
import com.school.teacher_service.dto.TimetableResponse;
import com.school.teacher_service.entity.Teacher;
import com.school.teacher_service.entity.Timetable;
import com.school.teacher_service.repository.TeacherRepository;
import com.school.teacher_service.repository.TimetableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimetableService {

    @Autowired
    private TimetableRepository timetableRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    // Class, section and subject live in academic-service: only their ids are stored.
    // Checking that those ids exist will be added in Step 6 (OpenFeign).

    public TimetableResponse createTimetable(TimetableRequest request) {
        if (!request.getStartTime().isBefore(request.getEndTime())) {
            throw new RuntimeException("Start time must be before end time");
        }

        Teacher teacher = null;
        if (request.getTeacherId() != null) {
            teacher = teacherRepository.findById(request.getTeacherId())
                    .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + request.getTeacherId()));

            // Business rule: prevent double-booking a teacher at an overlapping time on the same day
            List<Timetable> teacherSlots = timetableRepository.findByTeacherIdAndDayOfWeek(teacher.getId(), request.getDayOfWeek());
            for (Timetable slot : teacherSlots) {
                boolean overlaps = request.getStartTime().isBefore(slot.getEndTime())
                        && slot.getStartTime().isBefore(request.getEndTime());
                if (overlaps) {
                    throw new RuntimeException("Teacher is already booked during this time slot on " + request.getDayOfWeek());
                }
            }
        }

        Timetable timetable = new Timetable();
        timetable.setClassId(request.getClassId());
        timetable.setSectionId(request.getSectionId());
        timetable.setSubjectId(request.getSubjectId());
        timetable.setTeacher(teacher);
        timetable.setDayOfWeek(request.getDayOfWeek());
        timetable.setStartTime(request.getStartTime());
        timetable.setEndTime(request.getEndTime());

        Timetable saved = timetableRepository.save(timetable);
        return TimetableResponse.fromEntity(saved);
    }

    public List<TimetableResponse> getTimetable(Long classId, Long sectionId) {
        return timetableRepository.findByClassIdAndSectionId(classId, sectionId)
                .stream().map(TimetableResponse::fromEntity).collect(Collectors.toList());
    }

    public TimetableResponse getById(Long id) {
        Timetable t = timetableRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Timetable entry not found with id: " + id));
        return TimetableResponse.fromEntity(t);
    }

    public void deleteTimetable(Long id) {
        if (!timetableRepository.existsById(id)) {
            throw new RuntimeException("Timetable entry not found with id: " + id);
        }
        timetableRepository.deleteById(id);
    }
}