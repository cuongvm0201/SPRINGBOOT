package vn.techmaster.demomanageruser_jobhunt.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private String id;
    private String email;
    private String fullname;
    private String hashed_password;
    private State state;
    private Roles role;
}
