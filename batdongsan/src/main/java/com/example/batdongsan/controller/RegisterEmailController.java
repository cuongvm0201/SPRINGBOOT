package com.example.batdongsan.controller;

import com.example.batdongsan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterEmailController {
    @Autowired
    private UserService userService;
    @PostMapping("/register/users/check_email")
    public String checkDuplicate(@Param("id") Integer id, @Param("email") String email)
    {
        return userService.isEmailUnique(id,email) ? "OK" : "DUPLICATE";
    }
}
