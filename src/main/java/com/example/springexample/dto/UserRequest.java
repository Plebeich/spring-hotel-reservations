package com.example.springexample.dto;

import com.example.springexample.model.Role;
import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String password;
    private String email;
    private Role role;
}
