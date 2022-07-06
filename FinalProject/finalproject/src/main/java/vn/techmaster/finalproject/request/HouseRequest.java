package vn.techmaster.finalproject.request;

import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import groovy.transform.builder.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.techmaster.finalproject.model.City;
import vn.techmaster.finalproject.model.TypeHouse;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HouseRequest {
    private String id;
    @NotBlank(message = "Tên nhà Không Được Để Trống")
    private String name;
    @NotBlank(message = "Mô tả nhà Không Được Để Trống")
    private String description;
    private City city;
    private TypeHouse typeHouse;
    @NotBlank(message = "Địa chỉ nhà Không Được Để Trống")
    private String address;
    private Long price;
    private String logo_main;
    private MultipartFile logo;
    private String logo_sub_main1;
    private MultipartFile logo1;
    private String logo_sub_main2;
    private MultipartFile logo2;
    private String logo_sub_main3;
    private MultipartFile logo3;
    private String adminID;
}
