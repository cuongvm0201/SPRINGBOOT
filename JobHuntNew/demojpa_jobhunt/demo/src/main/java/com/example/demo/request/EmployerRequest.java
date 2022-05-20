package com.example.demo.request;

import org.springframework.web.multipart.MultipartFile;

import groovy.transform.builder.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployerRequest{
        private String id;

        @NotBlank(message = "Name cannot null")
        private String name;

        @NotBlank(message = "Web site cannot null")
        private String website;

        @NotBlank(message = "Email cannot null") @Email(message = "Invalid email") 
        private String email;
        
        private String logo_path;

        private MultipartFile logo;
}