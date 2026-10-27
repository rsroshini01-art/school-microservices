package com.school.academic_service.service;

import com.school.academic_service.dto.SchoolRequest;
import com.school.academic_service.dto.SchoolResponse;
import com.school.academic_service.entity.School;
import com.school.academic_service.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SchoolService {

    @Autowired
    private SchoolRepository schoolRepository;

    public SchoolResponse createSchool(SchoolRequest request) {
        School school = new School();
        school.setSchoolName(request.getSchoolName());
        school.setAddress(request.getAddress());
        school.setPhone(request.getPhone());
        school.setEmail(request.getEmail());
        school.setWebsite(request.getWebsite());
        school.setPrincipalName(request.getPrincipalName());
        school.setStatus(true);

        School saved = schoolRepository.save(school);
        return SchoolResponse.fromEntity(saved);
    }

    public Page<SchoolResponse> getSchools(String search, Pageable pageable) {
        Page<School> schools = schoolRepository.searchSchools(search, pageable);
        return schools.map(SchoolResponse::fromEntity);
    }

    public SchoolResponse getSchoolById(Long id) {
        School school = schoolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("School not found with id: " + id));
        return SchoolResponse.fromEntity(school);
    }

    public SchoolResponse updateSchool(Long id, SchoolRequest request) {
        School school = schoolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("School not found with id: " + id));

        school.setSchoolName(request.getSchoolName());
        school.setAddress(request.getAddress());
        school.setPhone(request.getPhone());
        school.setEmail(request.getEmail());
        school.setWebsite(request.getWebsite());
        school.setPrincipalName(request.getPrincipalName());

        School updated = schoolRepository.save(school);
        return SchoolResponse.fromEntity(updated);
    }

    public void deleteSchool(Long id) {
        if (!schoolRepository.existsById(id)) {
            throw new RuntimeException("School not found with id: " + id);
        }
        schoolRepository.deleteById(id);
    }
}