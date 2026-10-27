package com.school.academic_service.dto;

import com.school.academic_service.entity.Section;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SectionResponse {

    private Long id;
    private Long classId;
    private String className;
    private String sectionName;

    public static SectionResponse fromEntity(Section section) {
        return new SectionResponse(
                section.getId(),
                section.getSchoolClass().getId(),
                section.getSchoolClass().getClassName(),
                section.getSectionName()
        );
    }
}