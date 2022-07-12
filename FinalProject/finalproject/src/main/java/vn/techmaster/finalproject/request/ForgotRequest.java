package vn.techmaster.finalproject.request;

import javax.validation.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForgotRequest {
    @Email(message = "Email sai định dạng")
    private String email;
}
