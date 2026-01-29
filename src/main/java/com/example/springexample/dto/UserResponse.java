package com.example.springexample.dto;

import com.example.springexample.model.Role;
import lombok.Data;

@Data
public class UserResponse {
    private Integer id;
    private String username;
    private String email;
    private Role role;
}
