package com.example.demo.request;

import com.example.demo.model.Skill;

import groovy.transform.builder.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Thêm sửa xoá Applicant:
 * - Chọn job từ danh sách job hiện có
 * - Họ và tên
 * - Email
 * - Điện thoại
 * - Mô tả kỹ năng
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicantRequest{
                private String id;

                private String job_id;

                @NotBlank(message = "Name cannot null")
                private String name;

                @NotBlank(message = "Email cannot null") @Email(message = "Invalid email") 
                private String email;

                @NotBlank(message = "Phone cannot null")
                private String phone;

                @NotNull(message = "Skill cannot null")
                private List<Skill> skills; 
}