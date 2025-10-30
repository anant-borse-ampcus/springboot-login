package com.ampcus.login.service.impl;

import com.ampcus.login.dto.LoginRequest;
import com.ampcus.login.dto.SignupRequest;
import com.ampcus.login.entity.User;
import com.ampcus.login.repository.UserRepository;
import com.ampcus.login.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public String registerUser(SignupRequest request) {
        try {

            if (userRepository.existsByEmail(request.getEmail())) {
                return "Error: Email already registered!";
            }


            if (request.getRole() == null || request.getRole().isEmpty()) {
                return "Error: Role is required!";
            }

            User.Role role;
            try {
                role = User.Role.valueOf(request.getRole().toUpperCase());
            } catch (IllegalArgumentException e) {
                return "Error: Invalid role! Valid roles are USER and ADMIN.";
            }


            User user = new User();
            user.setEmail(request.getEmail());
            user.setPassword(request.getPassword());
            user.setRole(role);

            userRepository.save(user);
            return "User registered successfully!";
        } catch (Exception e) {
            return "Error: Something went wrong during registration! Please try again.";
        }
    }

    @Override
    public String loginUser(LoginRequest request) {
        try {
            User user = userRepository.findByEmail(request.getEmail()).orElse(null);

            if (user == null) {
                return "Error: User not found!";
            }

            if (!user.getPassword().equals(request.getPassword())) {
                return "Error: Invalid password!";
            }

            return "Login successful! Role: " + user.getRole();
        } catch (Exception e) {
            return "Error: Something went wrong during login! Please try again.";
        }
    }
}
