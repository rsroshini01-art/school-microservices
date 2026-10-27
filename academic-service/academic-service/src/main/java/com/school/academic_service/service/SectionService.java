package com.school.academic_service.service;

import com.school.academic_service.dto.SectionRequest;
import com.school.academic_service.dto.SectionResponse;
import com.school.academic_service.entity.SchoolClass;
import com.school.academic_service.entity.Section;
import com.school.academic_service.repository.SchoolClassRepository;
import com.school.academic_service.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SectionService {

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private SchoolClassRepository schoolClassRepository;

    public SectionResponse createSection(SectionRequest request) {
        SchoolClass sc = schoolClassRepository.findById(request.getClassId())
                .orElseThrow(() -> new RuntimeException("Class not found with id: " + request.getClassId()));

        Section section = new Section();
        section.setSchoolClass(sc);
        section.setSectionName(request.getSectionName());

        Section saved = sectionRepository.save(section);
        return SectionResponse.fromEntity(saved);
    }

    public List<SectionResponse> getSections(Long classId) {
        return sectionRepository.findBySchoolClassId(classId)
                .stream().map(SectionResponse::fromEntity).collect(Collectors.toList());
    }

    public SectionResponse getSectionById(Long id) {
        Section section = sectionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Section not found with id: " + id));
        return SectionResponse.fromEntity(section);
    }

    public SectionResponse updateSection(Long id, SectionRequest request) {
        Section section = sectionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Section not found with id: " + id));
        section.setSectionName(request.getSectionName());
        Section updated = sectionRepository.save(section);
        return SectionResponse.fromEntity(updated);
    }

    public void deleteSection(Long id) {
        if (!sectionRepository.existsById(id)) {
            throw new RuntimeException("Section not found with id: " + id);
        }
        sectionRepository.deleteById(id);
    }
}