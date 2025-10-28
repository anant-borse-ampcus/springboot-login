package com.ampcus.login.service;

import com.ampcus.login.dto.LoginRequest;
import com.ampcus.login.dto.SignupRequest;

public interface AuthService {
    String registerUser (SignupRequest request);
    String loginUser(LoginRequest request);
}
