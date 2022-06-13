package vn.techmaster.bank.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.techmaster.bank.exception.CommandException;
import vn.techmaster.bank.exception.RecordNotFoundException;
import vn.techmaster.bank.model.Account;
import vn.techmaster.bank.model.CommandStatus;
import vn.techmaster.bank.model.Deposit;
import vn.techmaster.bank.model.Pay;
import vn.techmaster.bank.model.Transfer;
import vn.techmaster.bank.model.User;
import vn.techmaster.bank.model.WithDraw;
import vn.techmaster.bank.repository.AccountRepo;
import vn.techmaster.bank.repository.CommandRepo;
import vn.techmaster.bank.repository.UserRepo;
import vn.techmaster.bank.request.DepositRequest;
import vn.techmaster.bank.request.PayRequest;
import vn.techmaster.bank.request.TransferRequest;
import vn.techmaster.bank.request.WithDrawRequest;
import vn.techmaster.bank.response.AccountInfo;

@Service
public class BankService {
    @Autowired private UserRepo userRepo;
    @Autowired private AccountRepo accountRepo;
    @Autowired private CommandRepo commandRepo;

  public AccountInfo deposit(DepositRequest depositRequest){
        User user = userRepo.findById(depositRequest.userID())
        .orElseThrow(() -> new RecordNotFoundException("users", depositRequest.userID()));

        Account account = accountRepo.findById(depositRequest.accountID())
        .orElseThrow(() -> new RecordNotFoundException("account", depositRequest.accountID()));
        
        if(!account.getUser().getId().equals(depositRequest.userID())){
            throw new CommandException("Owner of Account is not User");
        }

        account.setBalance(account.getBalance() + depositRequest.amount());
        Deposit deposit = new Deposit(user,account,depositRequest.amount());
        try {
            accountRepo.save(account);
            deposit.setStatus(CommandStatus.SuCCESS);
            commandRepo.save(deposit);
            return new AccountInfo(account.getId(), account.getBank().getName(), account.getBalance());
        } catch (Exception ex) {
            deposit.setStatus(CommandStatus.FAILED);
            commandRepo.save(deposit);
            var commandException = new CommandException("Deposit error");
            commandException.initCause(ex);
            throw commandException;
        }
  }

  public AccountInfo withDraw(WithDrawRequest withDrawRequest){
    User user = userRepo.findById(withDrawRequest.userID())
    .orElseThrow(() -> new RecordNotFoundException("users", withDrawRequest.userID()));

    Account account = accountRepo.findById(withDrawRequest.accountID())
    .orElseThrow(() -> new RecordNotFoundException("account", withDrawRequest.accountID()));
    
    if(!account.getUser().getId().equals(withDrawRequest.userID())){
        throw new CommandException("Owner of Account is not User");
    }
    if(withDrawRequest.amount() > account.getBalance()){
        throw new CommandException("Not Enough Money");
    }
    account.setBalance(account.getBalance() - withDrawRequest.amount());
    WithDraw withDraw = new WithDraw(user,account,withDrawRequest.amount());
    try {
        accountRepo.save(account);
        withDraw.setStatus(CommandStatus.SuCCESS);
        commandRepo.save(withDraw);
        return new AccountInfo(account.getId(), account.getBank().getName(), account.getBalance());
    } catch (Exception ex) {
        withDraw.setStatus(CommandStatus.FAILED);
        commandRepo.save(withDraw);
        var commandException = new CommandException("WithDraw error");
        commandException.initCause(ex);
        throw commandException;
    }
}

public Map<String,AccountInfo> transfer(TransferRequest transferRequest){
    User user = userRepo.findById(transferRequest.userID())
    .orElseThrow(() -> new RecordNotFoundException("users", transferRequest.userID()));

    Account sendAcc = accountRepo.findById(transferRequest.sendAccID())
    .orElseThrow(() -> new RecordNotFoundException("account", transferRequest.sendAccID()));

    Account revAcc = accountRepo.findById(transferRequest.receiveAccID())
    .orElseThrow(() -> new RecordNotFoundException("account", transferRequest.receiveAccID()));

    if(!sendAcc.getUser().getId().equals(transferRequest.userID())){
        throw new CommandException("Owner of Account is not User");
    }
    if(transferRequest.amount() > sendAcc.getBalance()){
        throw new CommandException("Not Enough Balance To Transfer");
    }

    sendAcc.setBalance(sendAcc.getBalance() - transferRequest.amount());
    revAcc.setBalance(revAcc.getBalance() + transferRequest.amount() );
    Transfer transfer = new Transfer(user,sendAcc,revAcc,transferRequest.amount());
    try {
        accountRepo.save(sendAcc);
        accountRepo.save(revAcc);
        transfer.setStatus(CommandStatus.SuCCESS);
        commandRepo.save(transfer);
        Map newTransfer = new HashMap<>();
        newTransfer.put("Chuyển thành công "+ transferRequest.amount() 
        + " tới tài khoản " + revAcc.getId() 
        +" tại ngân hàng "+revAcc.getBank().getName(),
        new AccountInfo(sendAcc.getId(), sendAcc.getBank().getName(), sendAcc.getBalance()));
        return newTransfer;
    } catch (Exception ex) {
        transfer.setStatus(CommandStatus.FAILED);
        commandRepo.save(transfer);
        var commandException = new CommandException("Transfer error");
        commandException.initCause(ex);
        throw commandException;
    }
}

public Map<String, AccountInfo> payment(PayRequest payRequest){
    User user = userRepo.findById(payRequest.userID())
    .orElseThrow(() -> new RecordNotFoundException("users", payRequest.userID()));

    Account account = accountRepo.findById(payRequest.accountID())
    .orElseThrow(() -> new RecordNotFoundException("account", payRequest.accountID()));
    
    

    if(payRequest.amount() > account.getBalance()){
        throw new CommandException("Not Enough Money To Payment");
    }
    account.setBalance(account.getBalance() - payRequest.amount());
    Pay payment = new Pay(user,account,payRequest.payType(),payRequest.amount());
    try {
        accountRepo.save(account);
        payment.setStatus(CommandStatus.SuCCESS);
        commandRepo.save(payment);
        Map newPayment = new HashMap<>();
        switch (payRequest.payType()) {
            case RECHARGEMOBILE:
            newPayment.put("Nạp thành công "+ payRequest.amount()+ " tới số điện thoại: "+ user.getMobile(),
            new AccountInfo(account.getId(), account.getBank().getName(), account.getBalance()));
                break;
            case WATER:
            newPayment.put("Thanh toán thành công hóa đơn: "+ payRequest.payType(),
            new AccountInfo(account.getId(), account.getBank().getName(), account.getBalance()));
                break;
            case ELECTRICITY:
            newPayment.put("Thanh toán thành công hóa đơn: "+ payRequest.payType(),
            new AccountInfo(account.getId(), account.getBank().getName(), account.getBalance()));
                break;
            default:
            return newPayment;
        }
        return newPayment;
    } catch (Exception ex) {
        payment.setStatus(CommandStatus.FAILED);
        commandRepo.save(payment);
        var commandException = new CommandException("Payment error");
        commandException.initCause(ex);
        throw commandException;
    }



}

}
