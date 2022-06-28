package vn.techmaster.bank.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.techmaster.bank.exception.CommandException;
import vn.techmaster.bank.exception.RecordNotFoundException;
import vn.techmaster.bank.model.Account;
import vn.techmaster.bank.model.AccountSaver;
import vn.techmaster.bank.model.Bank;
import vn.techmaster.bank.model.User;
import vn.techmaster.bank.repository.AccountRepo;
import vn.techmaster.bank.repository.BankRepo;
import vn.techmaster.bank.repository.UserRepo;
import vn.techmaster.bank.request.AccountRequest;
import vn.techmaster.bank.response.AccountInfo;

@Service
public class AccountService {
    @Autowired private UserRepo userRepo;
    @Autowired private AccountRepo accountRepo;
    @Autowired private BankRepo bankRepo;
    public AccountInfo openNewAccountBank(AccountRequest accountRequest){
        Bank bank = bankRepo.findById(accountRequest.bankID())
        .orElseThrow(() -> new RecordNotFoundException("bank", accountRequest.bankID()));

        User user = userRepo.findById(accountRequest.userID())
        .orElseThrow(() -> new RecordNotFoundException("users", accountRequest.bankID()));

        if(accountRequest.balance() <= 0){
            throw new CommandException("Số tiền nhập vào phải lớn hơn 0");
        }

        Account newAccByBank = new Account(UUID.randomUUID().toString(),bank,user,accountRequest.balance(),null,LocalDateTime.now());
        accountRepo.save(newAccByBank);
        return new AccountInfo(newAccByBank.getId(), newAccByBank.getBank().getName(), newAccByBank.getBalance());
    }
}
