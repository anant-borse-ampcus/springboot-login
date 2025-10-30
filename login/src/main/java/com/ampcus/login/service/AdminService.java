package com.ampcus.login.service;

import com.ampcus.login.entity.User;
import org.springframework.data.domain.Page;  // Correct import here
import org.springframework.data.domain.Pageable;

public interface AdminService {
    String addUser(User user);
    String updateUser(Long userId, User user);
    String softDeleteUser(Long userId);
    String deleteUser(Long userId);

    Page<User> searchUsers(String email, String roleStr, Boolean enabled, Pageable pageable);  // Signature matches the implementation
}
