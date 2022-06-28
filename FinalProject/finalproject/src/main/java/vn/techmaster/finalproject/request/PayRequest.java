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
public class PayRequest{
    private String reverseID;
    private String userID;

    @NotBlank(message = "Không được để trống")
    private String name;

    @NotBlank(message = "Không được để trống")
    @Size(min = 16, max = 16, message = "Số thẻ tiêu chuẩn gồm 16 số")
    private String cardnumber;

    @NotBlank(message = "Không được để trống")
    private String month;

    @NotBlank(message = "Không được để trống")
    private String year;

    @NotBlank(message = "Không được để trống")
    private String securitycode;
  
}
