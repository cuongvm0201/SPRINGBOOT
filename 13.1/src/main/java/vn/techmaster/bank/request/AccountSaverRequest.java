package vn.techmaster.bank.request;

import vn.techmaster.bank.model.TypeSave;

public record AccountSaverRequest(String userID, String accountID, Long months, Double amount, TypeSave typeSave) {
    
}
