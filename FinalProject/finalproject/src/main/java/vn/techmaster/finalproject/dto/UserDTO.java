package vn.techmaster.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.techmaster.finalproject.model.Roles;
import vn.techmaster.finalproject.model.State;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO{
    private String id; 
    private String fullname;
    private String email;
    private State state;
    private Roles role;
}
