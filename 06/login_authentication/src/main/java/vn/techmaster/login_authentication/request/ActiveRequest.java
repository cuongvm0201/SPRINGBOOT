package vn.techmaster.login_authentication.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public record ActiveRequest(
@Email(message = "Invalid email")  
String email,    
@Size(min = 5, max = 5, message = "Active_Code is only 5 characters")     
String active_code) {
    
}
