package com.ampcus.login.controller;

import com.ampcus.login.entity.User;
import com.ampcus.login.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody User user) {
        String result = adminService.addUser(user);
        if (result.startsWith("Error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable Long userId, @RequestBody User user) {
        String result = adminService.updateUser(userId, user);
        if (result.startsWith("Error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping("/soft-delete/{userId}")
    public ResponseEntity<String> softDeleteUser(@PathVariable Long userId) {
        String result = adminService.softDeleteUser(userId);
        if (result.startsWith("Error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        String result = adminService.deleteUser(userId);
        if (result.startsWith("Error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }
}
