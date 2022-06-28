package vn.techmaster.finalproject.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.techmaster.finalproject.model.City;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private String id;
    private String fullname;
    private String email;
    private String mobile;
    private String address;
    private City city;
}
