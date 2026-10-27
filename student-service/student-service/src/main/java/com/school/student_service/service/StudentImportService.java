package com.school.student_service.service;

import com.school.student_service.dto.ImportResultResponse;
import com.school.student_service.dto.ImportRowError;
import com.school.student_service.entity.Student;
import com.school.student_service.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentImportService {

    @Autowired
    private StudentRepository studentRepository;

    // Expected CSV header: Admission No,Name,DOB,Gender,ClassId,SectionId
    public ImportResultResponse importStudents(MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }
        String filename = file.getOriginalFilename();
        if (filename == null || !filename.toLowerCase().endsWith(".csv")) {
            throw new RuntimeException("Only CSV files are supported");
        }

        List<ImportRowError> errors = new ArrayList<>();
        int total = 0;
        int success = 0;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String headerLine = reader.readLine(); // skip header row
            String line;
            int rowNum = 1; // row 1 = first data row (after header)

            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;
                total++;

                try {
                    String[] parts = line.split(",");
                    if (parts.length < 6) {
                        throw new RuntimeException("Expected 6 columns, found " + parts.length);
                    }

                    String admissionNumber = parts[0].trim();
                    String fullName = parts[1].trim();
                    String dobStr = parts[2].trim();
                    String gender = parts[3].trim();
                    String classIdStr = parts[4].trim();
                    String sectionIdStr = parts[5].trim();

                    if (admissionNumber.isEmpty() || fullName.isEmpty()) {
                        throw new RuntimeException("Admission number and name are required");
                    }

                    if (studentRepository.existsByAdmissionNumber(admissionNumber)) {
                        throw new RuntimeException("Duplicate admission number: " + admissionNumber);
                    }

                    LocalDate dob;
                    try {
                        dob = LocalDate.parse(dobStr); // expects YYYY-MM-DD
                    } catch (Exception ex) {
                        throw new RuntimeException("Invalid date format for DOB: " + dobStr + " (expected YYYY-MM-DD)");
                    }

                    Long classId;
                    try {
                        classId = Long.parseLong(classIdStr);
                    } catch (NumberFormatException ex) {
                        throw new RuntimeException("Invalid class id: " + classIdStr);
                    }

                    Long sectionId = null;
                    if (!sectionIdStr.isEmpty()) {
                        try {
                            sectionId = Long.parseLong(sectionIdStr);
                        } catch (NumberFormatException ex) {
                            throw new RuntimeException("Invalid section id: " + sectionIdStr);
                        }
                    }

                    String[] nameParts = fullName.split(" ", 2);
                    String firstName = nameParts[0];
                    String lastName = nameParts.length > 1 ? nameParts[1] : "";

                    Student student = new Student();
                    student.setAdmissionNumber(admissionNumber);
                    student.setFirstName(firstName);
                    student.setLastName(lastName);
                    student.setDateOfBirth(dob);
                    student.setGender(gender);
                    student.setClassId(classId);
                    student.setSectionId(sectionId);
                    student.setStatus(true);

                    studentRepository.save(student);
                    success++;

                } catch (Exception rowEx) {
                    errors.add(new ImportRowError(rowNum, rowEx.getMessage()));
                }

                rowNum++;
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to read file: " + e.getMessage());
        }

        return new ImportResultResponse(total, success, total - success, errors);
    }
}