package com.school.fee_service.dto;

import com.school.fee_service.entity.FeeStructure;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeeStructureResponse {

    private Long id;
    private Long classId;
    private String className;   // filled in Step 6 (from academic-service)
    private String feeName;
    private Double amount;
    private String frequency;

    public static FeeStructureResponse fromEntity(FeeStructure f) {
        return new FeeStructureResponse(
                f.getId(),
                f.getClassId(),
                null,
                f.getFeeName(),
                f.getAmount(),
                f.getFrequency()
        );
    }
}