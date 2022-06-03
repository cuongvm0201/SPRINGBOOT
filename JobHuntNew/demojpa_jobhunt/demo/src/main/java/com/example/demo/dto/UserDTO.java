package com.example.demo.dto;

import com.example.demo.model.Roles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO{
    private String id; 
    private String fullname;
    private String email;
    private Roles role;
}
