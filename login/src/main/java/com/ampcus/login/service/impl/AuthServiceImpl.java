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
        if (userRepository.existsByEmail(request.getEmail())) {
            return "Error: Email already registered!";
        }

        //new user
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        userRepository.save(user);
        return "User registered successfully!";
    }

    @Override
    public String loginUser(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElse(null);

        if (user == null) {
            return "Error: User not found!";
        }

        if (!user.getPassword().equals(request.getPassword())) {
            return "Error: Invalid password!";
        }

        return "Login successful!";
    }
}
