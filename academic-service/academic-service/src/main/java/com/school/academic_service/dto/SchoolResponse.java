package com.school.academic_service.dto;

import com.school.academic_service.entity.School;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchoolResponse {

    private Long id;
    private String schoolName;
    private String address;
    private String phone;
    private String email;
    private String website;
    private String principalName;
    private boolean status;
    private LocalDateTime createdAt;

    public static SchoolResponse fromEntity(School school) {
        return new SchoolResponse(
                school.getId(),
                school.getSchoolName(),
                school.getAddress(),
                school.getPhone(),
                school.getEmail(),
                school.getWebsite(),
                school.getPrincipalName(),
                school.isStatus(),
                school.getCreatedAt()
        );
    }
}