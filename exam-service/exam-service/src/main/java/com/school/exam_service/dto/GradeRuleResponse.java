package com.school.exam_service.dto;

import com.school.exam_service.entity.GradeRule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GradeRuleResponse {

    private Long id;
    private Double minPercentage;
    private Double maxPercentage;
    private String grade;
    private boolean passGrade;

    public static GradeRuleResponse fromEntity(GradeRule r) {
        return new GradeRuleResponse(r.getId(), r.getMinPercentage(), r.getMaxPercentage(), r.getGrade(), r.isPassGrade());
    }
}