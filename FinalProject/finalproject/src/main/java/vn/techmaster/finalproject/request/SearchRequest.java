package vn.techmaster.finalproject.request;


import javax.validation.constraints.NotBlank;

import vn.techmaster.finalproject.model.City;

public record SearchRequest(
    @NotBlank(message = "Ngày đi không được để trống")
    String checkin,
    @NotBlank(message = "Ngày về không được để trống")
    String checkout,
    City city,
    Long price) {
    
}
