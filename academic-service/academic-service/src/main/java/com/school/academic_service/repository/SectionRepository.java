package com.school.academic_service.repository;

import com.school.academic_service.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SectionRepository extends JpaRepository<Section, Long> {
    List<Section> findBySchoolClassId(Long classId);
}