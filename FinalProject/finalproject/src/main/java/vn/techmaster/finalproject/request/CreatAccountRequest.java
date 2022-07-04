package vn.techmaster.finalproject.request;



import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.techmaster.finalproject.model.Roles;
import vn.techmaster.finalproject.model.State;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatAccountRequest {
    @NotBlank(message = "Fullname cannot blank")
    private String fullname;
    @NotBlank(message = "Email cannot blank") 
    @Email(message = "Invalid email") 
    private String email;
    @Size(min = 5, max = 20, message = "Password length must beetween 5 and 20 characters") 
    private String password;
    @Size(min = 5, max = 20, message = "Password length must beetween 5 and 20 characters") 
    private String confimPassword;
    private State state;
    private Roles role;
    
}
