package vn.techmaster.demouserbank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import vn.techmaster.demouserbank.exception.TransferException;
import vn.techmaster.demouserbank.model.Account;
import vn.techmaster.demouserbank.repository.AccountRepo;
@Service
@Slf4j
public class TransferService {
    @Autowired AccountRepo accountRepo;
   
    public String transferMoney(String sendAccountId, String recieveAccountId, long amount){
       Account sendAccount = new Account();
        if(accountRepo.findById(sendAccountId).isPresent()){
            sendAccount = accountRepo.findById(sendAccountId).get();
        } else {
            log.info(sendAccountId + "is not found");
            throw new TransferException("Account người gửi không chính xác");
            
        }
       Account recieveAcount = new Account();
       if(accountRepo.findById(recieveAccountId).isPresent()){
        recieveAcount = accountRepo.findById(recieveAccountId).get();
       } else {
        log.info(recieveAccountId + "is not found");
           throw new TransferException("Account người nhận không tồn tại");

       }

    
       if(amount > 0){
           if(sendAccount.getBalance() > amount){
            sendAccount.setBalance(sendAccount.getBalance()-amount);
            recieveAcount.setBalance(recieveAcount.getBalance()+amount);
            accountRepo.save(sendAccount);
            accountRepo.save(recieveAcount);
           }else {
               throw new TransferException("Số dư tài khoản phải lớn hơn số tiền chuyển khoản");
           }
       
       } else {
        log.error("Số tiền cần phải lớn hơn 0", NumberFormatException.class);
        throw new TransferException("Số tiền nhập vào phải là số nguyên dương");
       }

      return "Tài khoản " +sendAccount.getAccount()+ " chuyển thành công " + amount+ " tới tài khoản "+ recieveAcount.getAccount();
    }


    public String withdrawMoney(String accountId, long amount){
        Account currentAccount = new Account();
        if(accountRepo.findById(accountId).isPresent()){
            currentAccount = accountRepo.findById(accountId).get();
        } else {
            log.info(currentAccount + "is not found");
            throw new TransferException("Account không tồn tại");
        }

        if(amount > 0){
            if(currentAccount.getBalance() >= amount){
                currentAccount.setBalance(currentAccount.getBalance()-amount);
                accountRepo.save(currentAccount);
            }else {
                throw new TransferException("Số dư tài khoản phải lớn hơn số tiền muốn rút");
            }
        
        } else {
         log.info("Số tiền nhập vào cần phải lớn hơn 0");
         throw new TransferException("Số tiền nhập vào phải là số nguyên dương");
        }

        return "Tài khoản " +currentAccount.getAccount()+ " rút thành công " + amount + "\n" +
        "Số dư tài khoản hiện tại: " + currentAccount.getBalance() ;
    }

    public String depositMoney(String accountId, long amount){
        Account currentAccount = new Account();
        if(accountRepo.findById(accountId).isPresent()){
            currentAccount = accountRepo.findById(accountId).get();
        } else {
            log.info("Số tiền nhập vào cần phải lớn hơn 0");
            throw new TransferException("Account không tồn tại");
        }

        if(amount > 0){
                currentAccount.setBalance(currentAccount.getBalance()+amount);
                accountRepo.save(currentAccount);
            
        } else {
            log.info("Số tiền nhập vào cần phải lớn hơn 0");
         throw new TransferException("Số tiền nhập vào phải là số nguyên dương");
        }

        return "Tài khoản " +currentAccount.getAccount()+ " nạp thành công " + amount + "\n" +
        "Số dư tài khoản hiện tại: " + currentAccount.getBalance() ;
    }

}
