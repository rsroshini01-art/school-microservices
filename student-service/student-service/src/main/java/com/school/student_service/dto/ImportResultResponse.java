package com.school.student_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImportResultResponse {
    private int total;
    private int success;
    private int failed;
    private List<ImportRowError> errors;
}