package com.ampcus.login.service;

import com.ampcus.login.entity.User;
import com.ampcus.login.repository.UserRepository;

public interface AdminService {
    String addUser(User user);
    String updateUser(Long userId, User user);
    String softDeleteUser(Long userId);
    String deleteUser(Long userId);
}
