package com.ampcus.login.controller;

import com.ampcus.login.dto.LoginRequest;
import com.ampcus.login.dto.SignupRequest;
import com.ampcus.login.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest request) {
        String result = authService.registerUser(request);
        if (result.startsWith("Error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        String result = authService.loginUser(request);
        if (result.equals("Login successful!")) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(401).body(result);
    }
}
