package vn.techmaster.finalproject.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.techmaster.finalproject.model.City;
import vn.techmaster.finalproject.model.Roles;
import vn.techmaster.finalproject.model.State;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private String id;
    private String fullname;
    private String email;
    private String mobile;
    private String address;
    private Long wallet;
    private City city;
}
