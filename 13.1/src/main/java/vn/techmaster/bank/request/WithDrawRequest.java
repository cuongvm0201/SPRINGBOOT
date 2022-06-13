package vn.techmaster.bank.request;

public record WithDrawRequest(String userID, String accountID, Double amount) {
    
}
