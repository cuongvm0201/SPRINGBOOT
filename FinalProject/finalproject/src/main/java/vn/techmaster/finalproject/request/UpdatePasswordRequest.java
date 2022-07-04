package vn.techmaster.finalproject.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public record UpdatePasswordRequest(
    String id,
    @NotBlank(message = "Không được để trống")
    String oldPassword,

    @NotBlank(message = "Không được để trống")
    @Size(min = 5, max = 20, message = "Mật khẩu phải có độ dài từ 5-20 ký tự")
    String newPassword
) 
{}

