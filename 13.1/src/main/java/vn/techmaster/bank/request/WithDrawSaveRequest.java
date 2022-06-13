package vn.techmaster.bank.request;

import java.util.UUID;

public record WithDrawSaveRequest(String userID, String accountID, String accountSaverID) {
    
}
