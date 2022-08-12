package vn.techmaster.finalproject.request;

import javax.validation.Valid;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Valid
public class PayWalletRequest {
    private String reverseID;
    private String userID;
    private Long walletuser;
    private Long totalPay;
}
