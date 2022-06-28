package vn.techmaster.finalproject.request;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Valid
public class ReverseRequest{
    private String id;
    private String userID;
    private String houseID;
    private String houseName;

    @NotBlank(message = "Số điện thoại không được để trống") @Size(min = 10, max = 11, message = "Số điện thoại phải là 10/11 số")
    private String mobile;

    private String userName;

    @NotBlank(message = "Vui lòng chọn ngày đến")
    private String checkin;
    
    @NotBlank(message = "Vui lòng chọn ngày về")
    private String checkout;
    
}
