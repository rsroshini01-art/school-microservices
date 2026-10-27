package com.school.auth_service.service;

import com.school.auth_service.dto.*;
import com.school.auth_service.entity.BlacklistedToken;
import com.school.auth_service.entity.User;
import com.school.auth_service.repository.BlacklistedTokenRepository;
import com.school.auth_service.repository.UserRepository;
import com.school.auth_service.security.CustomUserDetails;
import com.school.auth_service.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuditLogService auditLogService;

    @Autowired
    private BlacklistedTokenRepository blacklistedTokenRepository;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setActive(true);

        User savedUser = userRepository.save(user);

        CustomUserDetails userDetails = new CustomUserDetails(savedUser);
        String token = jwtUtil.generateToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);

        return new AuthResponse(token, refreshToken, savedUser.getId(), savedUser.getEmail(),
                savedUser.getFirstName(), savedUser.getLastName(), savedUser.getRole());
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        CustomUserDetails userDetails = new CustomUserDetails(user);
        String token = jwtUtil.generateToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);

        auditLogService.log(user.getEmail(), "LOGIN", "AUTH", user.getEmail());

        return new AuthResponse(token, refreshToken, user.getId(), user.getEmail(),
                user.getFirstName(), user.getLastName(), user.getRole());
    }

    public void changePassword(String email, ChangePasswordRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        auditLogService.log(user.getEmail(), "UPDATE", "AUTH", "password_change");
    }

    public AuthResponse refreshToken(RefreshTokenRequest request) {
        if (!jwtUtil.validateRefreshToken(request.getRefreshToken())) {
            throw new RuntimeException("Invalid or expired refresh token");
        }

        String email = jwtUtil.extractEmailFromRefreshToken(request.getRefreshToken());
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        CustomUserDetails userDetails = new CustomUserDetails(user);
        String newAccessToken = jwtUtil.generateToken(userDetails);

        return new AuthResponse(newAccessToken, request.getRefreshToken(), user.getId(), user.getEmail(),
                user.getFirstName(), user.getLastName(), user.getRole());
    }

    public void logout(String token) {
        BlacklistedToken blacklisted = new BlacklistedToken();
        blacklisted.setToken(token);
        blacklistedTokenRepository.save(blacklisted);
    }
}