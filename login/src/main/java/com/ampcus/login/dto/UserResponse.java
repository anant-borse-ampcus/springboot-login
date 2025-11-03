package com.ampcus.login.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UserResponse {
    private Long id;
    private String email;
    private String password;
    private String role;




}
