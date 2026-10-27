package com.school.exam_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultResponse {

    private String studentName;
    private String examName;
    private List<MarksResponse> subjects;
    private Double totalMarksObtained;
    private Double totalMaxMarks;
    private Double percentage;
    private String grade;
    private String result; // "PASS" or "FAIL"
}