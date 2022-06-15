package vn.techmaster.bank.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.hibernate.query.criteria.internal.predicate.IsEmptyPredicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import vn.techmaster.bank.exception.CommandException;
import vn.techmaster.bank.exception.RecordNotFoundException;
import vn.techmaster.bank.model.Account;
import vn.techmaster.bank.model.AccountSaver;
import vn.techmaster.bank.model.TypeSave;
import vn.techmaster.bank.model.User;
import vn.techmaster.bank.repository.AccountRepo;
import vn.techmaster.bank.repository.AccountSaverRepo;
import vn.techmaster.bank.repository.UserRepo;
import vn.techmaster.bank.request.AccountSaverRequest;
import vn.techmaster.bank.request.WithDrawSaveRequest;
import vn.techmaster.bank.response.AccountSaverInfo;

@Service
public class AccountSaverService {
    @Autowired private UserRepo userRepo;
    @Autowired private AccountRepo accountRepo;
    @Autowired private AccountSaverRepo accountSaverRepo;
    @Autowired private RateConfigService rateConfigService;

    @Scheduled(fixedRate = 10000)
    public void auto_renew(){
    List<AccountSaver> allAccSave = accountSaverRepo.findAll();
    for (int i = 0; i < allAccSave.size(); i++) {
       AccountSaver ai = allAccSave.get(i);
       Account accByaccSave = accountRepo.findById(ai.getAccount().getId()).get();
       Double bonusBalance;
       if(ai.getTypeSave().equals(TypeSave.FINAL)){
        if(ai.getMonths() == 0){
            bonusBalance= ai.getStartBalance()*((ai.getRate()/100)/12);
        } else {
            bonusBalance = ai.getStartBalance()*ai.getMonths()*((ai.getRate()/100)/12);
        }
        if(ai.getCloseAt().plusDays(1).compareTo(LocalDateTime.now()) <= 0){
            switch(ai.getAutoSaver()){
                case AUTORENEW:
                ai.setOpenAt(LocalDateTime.now());
                ai.setCloseAt(ai.getOpenAt().plusMonths(ai.getMonths()));
                ai.setStartBalance(ai.getEndBalance()+bonusBalance);
                ai.setEndBalance(ai.getStartBalance());
                accountSaverRepo.save(ai);
                break;
                case NON_AUTORENEW:
                ai.setEndBalance(ai.getStartBalance()+bonusBalance);
                accByaccSave.setBalance(accByaccSave.getBalance()+ai.getEndBalance());
                accountSaverRepo.deleteById(ai.getId());
                accountRepo.save(accByaccSave);
                break;
            }
        }
        }

        if(ai.getTypeSave().equals(TypeSave.EVERYMONTH)){
            final Double currentBalance = ai.getStartBalance();
            Double bonusBalancePerMonth = currentBalance*(((ai.getRate()*0.8)/100)/12);
            if(ai.getCloseAt().plusDays(1).compareTo(LocalDateTime.now()) <= 0){
                switch(ai.getAutoSaver()){
                    case AUTORENEW:
                    ai.setOpenAt(LocalDateTime.now());
                    ai.setCloseAt(ai.getOpenAt().plusMonths(ai.getMonths()));
                    ai.setStartBalance(ai.getEndBalance()+(bonusBalancePerMonth*ai.getMonths()));
                    ai.setEndBalance(ai.getStartBalance());
                    accountSaverRepo.save(ai);
                    break;
                    case NON_AUTORENEW:
                    ai.setEndBalance(ai.getStartBalance()+(bonusBalancePerMonth*ai.getMonths()));
                    accByaccSave.setBalance(accByaccSave.getBalance()+ai.getEndBalance());
                    accountSaverRepo.deleteById(ai.getId());
                    accountRepo.save(accByaccSave);
                    break;
                }
            }
        }   
    }
}


    public AccountSaverInfo openAccount(AccountSaverRequest accountSaverRequest){
        User user = userRepo.findById(accountSaverRequest.userID())
        .orElseThrow(() -> new RecordNotFoundException("users", accountSaverRequest.userID()));

        Account account = accountRepo.findById(accountSaverRequest.accountID())
        .orElseThrow(() -> new RecordNotFoundException("account", accountSaverRequest.accountID()));

        if(accountSaverRequest.amount() > account.getBalance()){
            throw new CommandException("Not Enough Balance To Transfer");
        }
        
        if(rateConfigService.findRateByMonth(accountSaverRequest.months()) == null){
            throw new CommandException("This month is not valid");
        }

        account.setBalance(account.getBalance()-accountSaverRequest.amount());
        AccountSaver newAccSaver;
        if(accountSaverRequest.typeSave().toString().equals("FINAL")){
         newAccSaver = AccountSaver.builder()
        .id(UUID.randomUUID().toString())
        .account(account)
        .startBalance(accountSaverRequest.amount())
        .endBalance(accountSaverRequest.amount())
        .months(accountSaverRequest.months())
        .rate(rateConfigService.findRateByMonth(accountSaverRequest.months()))
        .typeSave(accountSaverRequest.typeSave())
        .autoSaver(accountSaverRequest.autoSaver())
        .openAt(LocalDateTime.now())
        .updateAt(null)
        .closeAt(LocalDateTime.now().plusMonths(accountSaverRequest.months())).build();
        }
         newAccSaver = AccountSaver.builder()
        .id(UUID.randomUUID().toString())
        .account(account)
        .startBalance(accountSaverRequest.amount())
        .endBalance(accountSaverRequest.amount())
        .months(accountSaverRequest.months())
        .rate(rateConfigService.findRateByMonth(accountSaverRequest.months()))
        .typeSave(accountSaverRequest.typeSave())
        .autoSaver(accountSaverRequest.autoSaver())
        .openAt(LocalDateTime.now())
        .updateAt(LocalDateTime.now().plusMonths(1))
        .closeAt(LocalDateTime.now().plusMonths(accountSaverRequest.months())).build();
        accountSaverRepo.save(newAccSaver);

        return new AccountSaverInfo(newAccSaver.getId(), 
        newAccSaver.getStartBalance(), 
        newAccSaver.getMonths(), 
        newAccSaver.getRate(), 
        newAccSaver.getOpenAt(), newAccSaver.getCloseAt());
    }

    public String withDrawSaveAccount(WithDrawSaveRequest withDrawSaveRequest){
        User user = userRepo.findById(withDrawSaveRequest.userID())
    .orElseThrow(() -> new RecordNotFoundException("users", withDrawSaveRequest.userID()));

        Account account = accountRepo.findById(withDrawSaveRequest.accountID())
    .orElseThrow(() -> new RecordNotFoundException("account", withDrawSaveRequest.accountID()));

       AccountSaver accountSaver = accountSaverRepo.findById(withDrawSaveRequest.accountSaverID())
       .orElseThrow(() -> new RecordNotFoundException("accountsaver", withDrawSaveRequest.accountSaverID()));
        if(accountSaver.getTypeSave().equals(TypeSave.FINAL)){
            Double bonusBalance;
            if(accountSaver.getMonths() == 0){
                bonusBalance= accountSaver.getStartBalance()*((accountSaver.getRate()/100)/12);
            } else {
                bonusBalance = accountSaver.getStartBalance()*accountSaver.getMonths()*((accountSaver.getRate()/100)/12);
            }
            if(accountSaver.getMonths() != 0 && accountSaver.getCloseAt().compareTo(LocalDateTime.now()) > 0){
                throw new CommandException("Chưa đến hạn rút lãi");
            }
           
            account.setBalance(account.getBalance()+bonusBalance);
            accountRepo.save(account);
            accountSaverRepo.deleteById(accountSaver.getId());
    
            return "Tài khoản "+ account.getId() + " nhận thành công khoản lãi: "+ bonusBalance;
        }
        final Double currentBalance = accountSaver.getStartBalance();
        Double bonusBalancePerMonth = currentBalance*(((accountSaver.getRate()*0.8)/100)/12);
        if(accountSaver.getUpdateAt().compareTo(accountSaver.getCloseAt()) < 0){
            if(accountSaver.getUpdateAt().compareTo(LocalDateTime.now()) > 0){
                throw new CommandException("Chưa đến hạn rút lãi");
            } else {
            accountSaver.setEndBalance((accountSaver.getEndBalance()+bonusBalancePerMonth));
            accountSaver.setUpdateAt(accountSaver.getUpdateAt().plusMonths(1));
            accountSaverRepo.save(accountSaver);
            return "Tài khoản tiết kiệm "+ accountSaver.getId() + " nhận thành công khoản lãi hàng tháng: "+ bonusBalancePerMonth;
            }
            
        } else {
            account.setBalance(account.getBalance()+accountSaver.getEndBalance()+bonusBalancePerMonth);
            accountRepo.save(account);
            accountSaverRepo.deleteById(accountSaver.getId());
            return "Tài khoản "+ account.getId() + " nhận thành công khoản gốc kèm lãi: "+ (accountSaver.getEndBalance()+bonusBalancePerMonth) + " với số tiền lãi: "+
            currentBalance*accountSaver.getMonths()*(((accountSaver.getRate()*0.8)/100)/12);
        }

        
    }

    public AccountSaver findAccSavebyAccID(String accountID){
        List<AccountSaver> listAccSave = accountSaverRepo.findAll();
        AccountSaver finalAccSave = new AccountSaver();
        for (int i = 0; i < listAccSave.size(); i++) {
            if(listAccSave.get(i).getAccount().getId().equalsIgnoreCase(accountID)){
                finalAccSave = listAccSave.get(i);
            }
            else {
                throw new CommandException("Account is not found");
            }
        }
        return finalAccSave;
    }

    
}
