package com.ampcus.login.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class searchUserDto {
    private String email;
    private String role;
    private Boolean enabled;

}
