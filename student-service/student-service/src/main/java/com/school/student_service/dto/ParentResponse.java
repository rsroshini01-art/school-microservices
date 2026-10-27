package com.school.student_service.dto;

import com.school.student_service.entity.Parent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParentResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String occupation;
    private List<String> childrenNames;

    public static ParentResponse fromEntity(Parent parent) {
        List<String> names = parent.getStudents().stream()
                .map(s -> s.getFirstName() + " " + s.getLastName())
                .collect(Collectors.toList());

        return new ParentResponse(
                parent.getId(),
                parent.getFirstName(),
                parent.getLastName(),
                parent.getPhone(),
                parent.getEmail(),
                parent.getOccupation(),
                names
        );
    }
}