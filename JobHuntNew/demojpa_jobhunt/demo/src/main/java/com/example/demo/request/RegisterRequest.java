package com.example.demo.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
        @NotBlank(message = "Fullname cannot blank")
        private String fullname;
        @NotBlank(message = "Email cannot blank") 
        @Email(message = "Invalid email") 
        private String email;
        @Size(min = 5, max = 20, message = "Password length must beetween 5 and 20 characters") 
        private String password;
        @Size(min = 5, max = 20, message = "Password length must beetween 5 and 20 characters") 
        private String confimPassword;
        

}
