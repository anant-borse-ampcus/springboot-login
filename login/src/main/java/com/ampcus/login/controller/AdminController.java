package com.ampcus.login.controller;

import com.ampcus.login.dto.UserRequestDto;
import com.ampcus.login.dto.UserResponseDto;
import com.ampcus.login.dto.SearchUserDto;
import com.ampcus.login.entity.User;
import com.ampcus.login.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    @PutMapping("/activate/{userId}")
    public ResponseEntity<String> activateUser(@PathVariable Long userId) {
        String result = adminService.activateUser(userId);
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

    @GetMapping("/search-users")
    public ResponseEntity<Page<UserResponseDto>> searchUsers(
            @ModelAttribute  SearchUserDto searchDto){

        try {
            PageRequest pageRequest = PageRequest.of(searchDto.getPage(), searchDto.getSize());
            Page<UserResponseDto> usersPage = adminService.searchUsers(searchDto, pageRequest);
            return ResponseEntity.ok(usersPage);

        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }


}
