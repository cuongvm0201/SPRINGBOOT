package vn.techmaster.demouserbank.request;

public record TransferRequest(String sendid, String getid, long amount) {
    
}
