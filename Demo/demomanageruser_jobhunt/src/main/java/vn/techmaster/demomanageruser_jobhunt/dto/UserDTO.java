package vn.techmaster.demomanageruser_jobhunt.dto;

import vn.techmaster.demomanageruser_jobhunt.model.Roles;

public record UserDTO(String id, String fullname, String email, Roles role) {
    
}
