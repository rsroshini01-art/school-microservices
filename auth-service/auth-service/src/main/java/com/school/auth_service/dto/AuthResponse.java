package com.school.auth_service.dto;

import com.school.auth_service.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private String token;
    private String refreshToken;
    private Long userId;
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
}