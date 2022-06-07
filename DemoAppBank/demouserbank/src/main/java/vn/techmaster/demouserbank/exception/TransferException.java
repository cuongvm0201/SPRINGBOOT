package vn.techmaster.demouserbank.exception;

public class TransferException extends RuntimeException {
    private static final long serialVersionUID = -4315753772384796683L;
    public TransferException(String message){
        super(message);
    }
}
