package vn.techmaster.bank.request;

import vn.techmaster.bank.model.PayType;

public record PayRequest(String userID, String accountID, PayType payType, Double amount) {
    
}
