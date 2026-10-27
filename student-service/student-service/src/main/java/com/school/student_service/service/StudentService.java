package com.school.student_service.service;

import com.school.student_service.client.AcademicClient;
import com.school.student_service.dto.StudentRequest;
import com.school.student_service.dto.StudentResponse;
import com.school.student_service.entity.Student;
import com.school.student_service.repository.StudentRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AcademicClient academicClient;

    // Used when creating or updating: the class must exist, otherwise we refuse.
    @SuppressWarnings("unchecked")
    private String validateClass(Long classId) {
        try {
            Map<String, Object> body = academicClient.getSchoolClass(classId);
            Map<String, Object> data = (Map<String, Object>) body.get("data");
            return String.valueOf(data.get("className"));
        } catch (FeignException.NotFound e) {
            throw new RuntimeException("Class not found with id: " + classId);
        } catch (FeignException e) {
            throw new RuntimeException("Academic service is not available right now, please try again");
        }
    }

    // Used when only reading: if academic-service is down, we just leave the name empty.
    @SuppressWarnings("unchecked")
    private String findClassName(Long classId) {
        if (classId == null) {
            return null;
        }
        try {
            Map<String, Object> body = academicClient.getSchoolClass(classId);
            Map<String, Object> data = (Map<String, Object>) body.get("data");
            return String.valueOf(data.get("className"));
        } catch (Exception e) {
            return null;
        }
    }

    public StudentResponse createStudent(StudentRequest request) {
        if (studentRepository.existsByAdmissionNumber(request.getAdmissionNumber())) {
            throw new RuntimeException("Admission number already exists: " + request.getAdmissionNumber());
        }

        String className = validateClass(request.getClassId());

        Student student = new Student();
        student.setAdmissionNumber(request.getAdmissionNumber());
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setDateOfBirth(request.getDateOfBirth());
        student.setGender(request.getGender());
        student.setPhone(request.getPhone());
        student.setEmail(request.getEmail());
        student.setAddress(request.getAddress());
        student.setAdmissionDate(request.getAdmissionDate());
        student.setClassId(request.getClassId());
        student.setSectionId(request.getSectionId());
        student.setRollNumber(request.getRollNumber());
        student.setStatus(true);

        Student saved = studentRepository.save(student);
        StudentResponse response = StudentResponse.fromEntity(saved);
        response.setClassName(className);
        return response;
    }

    public Page<StudentResponse> getStudents(String search, Long classId, Long sectionId, Boolean status, Pageable pageable) {
        Page<Student> students = studentRepository.searchStudents(search, classId, sectionId, status, pageable);
        return students.map(StudentResponse::fromEntity);
    }

    public StudentResponse getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        StudentResponse response = StudentResponse.fromEntity(student);
        response.setClassName(findClassName(student.getClassId()));
        return response;
    }

    public StudentResponse updateStudent(Long id, StudentRequest request) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));

        if (!student.getAdmissionNumber().equals(request.getAdmissionNumber())
                && studentRepository.existsByAdmissionNumber(request.getAdmissionNumber())) {
            throw new RuntimeException("Admission number already exists: " + request.getAdmissionNumber());
        }

        String className = validateClass(request.getClassId());

        student.setAdmissionNumber(request.getAdmissionNumber());
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setDateOfBirth(request.getDateOfBirth());
        student.setGender(request.getGender());
        student.setPhone(request.getPhone());
        student.setEmail(request.getEmail());
        student.setAddress(request.getAddress());
        student.setAdmissionDate(request.getAdmissionDate());
        student.setClassId(request.getClassId());
        student.setSectionId(request.getSectionId());
        student.setRollNumber(request.getRollNumber());

        Student updated = studentRepository.save(student);
        StudentResponse response = StudentResponse.fromEntity(updated);
        response.setClassName(className);
        return response;
    }

    public StudentResponse updateStatus(Long id, boolean status) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        student.setStatus(status);
        Student updated = studentRepository.save(student);
        return StudentResponse.fromEntity(updated);
    }

    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new RuntimeException("Student not found with id: " + id);
        }
        studentRepository.deleteById(id);
    }
}