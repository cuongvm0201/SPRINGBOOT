package vn.techmaster.bank.request;

public record TransferRequest(String userID, String sendAccID, String receiveAccID, Double amount ) {
    
}
