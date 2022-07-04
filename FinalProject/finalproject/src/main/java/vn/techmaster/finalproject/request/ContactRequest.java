package vn.techmaster.finalproject.request;

import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import groovy.transform.builder.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactRequest {
        private String id;
        @NotBlank(message = "Tên không được để trống")
        private String fullname;
        @Email(message = "Email sai định dạng")
        @NotBlank(message = "Email không được để trống")
        private String email;
        @NotBlank(message = "SĐT không được để trống")
        @Size(min = 10, max = 10, message = "SĐT phải đủ 10 số")
        private String phone;
        @NotBlank(message = "Tin nhắn không được để trống")
        private String message;
}
