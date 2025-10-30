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
        try {
            if (userRepository.existsByEmail(user.getEmail())) {
                return "Error: Email already registered!";
            }


            user.setPassword(user.getPassword());
            userRepository.save(user);
            return "User added successfully!";
        } catch (Exception e) {
            return "Error: Something went wrong while adding the user.";
        }
    }

    @Override
    public String updateUser(Long userId, User user) {
        try {
            User existingUser = userRepository.findById(userId).orElse(null);

            if (existingUser == null) {
                return "Error: User not found!";
            }

            // Update the user's details
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(user.getPassword());
            existingUser.setRole(user.getRole());
            userRepository.save(existingUser);

            return "User updated successfully!";
        } catch (Exception e) {
            return "Error: Something went wrong while updating the user.";
        }
    }

    @Override
    public String softDeleteUser(Long userId) {
        try {
            User user = userRepository.findById(userId).orElse(null);

            if (user == null) {
                return "Error: User not found!";
            }


            user.setEnabled(false);
            userRepository.save(user);
            return "User deactivated (soft delete) successfully!";
        } catch (Exception e) {
            return "Error: Something went wrong while deactivating the user.";
        }
    }

    @Override
    public String deleteUser(Long userId) {
        try {
            User user = userRepository.findById(userId).orElse(null);

            if (user == null) {
                return "Error: User not found!";
            }


            userRepository.deleteById(userId);
            return "User deleted permanently!";
        } catch (Exception e) {
            return "Error: Something went wrong while deleting the user.";
        }
    }
}
