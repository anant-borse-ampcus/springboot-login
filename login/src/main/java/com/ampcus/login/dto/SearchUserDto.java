package com.ampcus.login.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchUserDto {
    private String email;
    private String role;
    private Boolean enabled;
    private int page = 0;
    private int size = 10;


    public SearchUserDto(String email, String role, Boolean enabled) {
        this.email = email;
        this.role = role;
        this.enabled = enabled;
    }
}
