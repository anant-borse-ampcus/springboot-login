package com.ampcus.login.service.impl;

import com.ampcus.login.entity.User;
import com.ampcus.login.repository.UserRepository;
import com.ampcus.login.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public String addUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return "Error: Email already registered!";
        }


        user.setPassword(user.getPassword());
        userRepository.save(user);
        return "User added successfully!";
    }

    @Override
    public String updateUser(Long userId, User user) {
        User existingUser = userRepository.findById(userId).orElse(null);

        if (existingUser == null) {
            return "Error: User not found!";
        }

        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        existingUser.setRole(user.getRole());
        userRepository.save(existingUser);

        return "User updated successfully!";
    }

    @Override
    public String softDeleteUser(Long userId) {
        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return "Error: User not found!";
        }


        user.setEnabled(false);
        userRepository.save(user);
        return "User deactivated (soft delete) successfully!";
    }

    @Override
    public String deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return "Error: User not found!";
        }

        userRepository.deleteById(userId);
        return "User deleted permanently!";
    }
}
