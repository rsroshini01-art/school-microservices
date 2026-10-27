package com.school.auth_service.service;

import com.school.auth_service.dto.UserResponse;
import com.school.auth_service.entity.Role;
import com.school.auth_service.entity.User;
import com.school.auth_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // The student/parent now live in student-service, so we only store their ids here.
    // Checking that the id really exists will be added in Step 6 (OpenFeign).
    public void linkToStudent(Long userId, Long studentId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        user.setLinkedStudentId(studentId);
        userRepository.save(user);
    }

    public void linkToParent(Long userId, Long parentId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        user.setLinkedParentId(parentId);
        userRepository.save(user);
    }

    public Page<UserResponse> getUsers(String search, Role role, Boolean active, Pageable pageable) {
        Page<User> users = userRepository.searchUsers(search, role, active, pageable);
        return users.map(UserResponse::fromEntity);
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return UserResponse.fromEntity(user);
    }

    public UserResponse updateUserStatus(Long id, boolean active) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        user.setActive(active);
        userRepository.save(user);
        return UserResponse.fromEntity(user);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}