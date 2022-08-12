package vn.com.cmcglobal.demoshopcart.mapper.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private String username;
    private String password;
    private String fullname;
    private String mobile;
    private String email;
    private String address;
    private Roles role;
    private LocalDateTime creatAt;
    private LocalDateTime updateAt;

    
}
