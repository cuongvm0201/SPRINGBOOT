package vn.techmaster.login_authentication.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "Fullname cannot blank")
        String fullname,
        @NotBlank(message = "Email cannot blank") 
        @Email(message = "Invalid email") 
        String email,
        @Size(min = 5, max = 20, message = "Password length must beetween 5 and 20 characters") 
        String password) {

}
