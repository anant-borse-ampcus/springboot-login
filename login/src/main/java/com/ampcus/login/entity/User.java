package com.ampcus.login.entity;

import jakarta.persistence.*;
import lombok.*;


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true,nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    private boolean enabled = true;

    @Enumerated (EnumType.STRING)
    @Column(nullable = false)
    private Role role;
    public enum Role{
        USER,
        ADMIN
    }

}
