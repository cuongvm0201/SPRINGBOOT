package vn.techmaster.finalproject.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.techmaster.finalproject.model.City;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminSearchRequest {
    private Long price;
    private City city;
}
