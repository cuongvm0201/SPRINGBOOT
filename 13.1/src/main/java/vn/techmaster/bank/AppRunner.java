package vn.techmaster.bank;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties.Build;
import org.springframework.stereotype.Component;

import vn.techmaster.bank.exception.RecordNotFoundException;
import vn.techmaster.bank.model.Account;
import vn.techmaster.bank.model.AccountSaver;
import vn.techmaster.bank.model.AutoSaver;
import vn.techmaster.bank.model.Bank;
import vn.techmaster.bank.model.RateConfig;
import vn.techmaster.bank.model.TypeSave;
import vn.techmaster.bank.model.User;
import vn.techmaster.bank.repository.AccountRepo;
import vn.techmaster.bank.repository.AccountSaverRepo;
import vn.techmaster.bank.repository.BankRepo;
import vn.techmaster.bank.repository.RateConfigRepo;
import vn.techmaster.bank.repository.UserRepo;
import vn.techmaster.bank.service.AccountSaverService;

@Component
@Transactional
public class AppRunner implements ApplicationRunner{
  @Autowired private EntityManager em;
  @Autowired private UserRepo userRepo;
  @Autowired private AccountRepo accountRepo;
  @Autowired private BankRepo bankRepo;
  @Autowired private RateConfigRepo rateConfigRepo;
  @Autowired private AccountSaverRepo accountSaverRepo;
  @Autowired private AccountSaverService accountSaverService;
  
  private void generateAccount() {
    
    Bank vcb = bankRepo.findById("vcb")
    .orElseThrow(() ->new RecordNotFoundException("bank", "vcb"));

    Bank acb = bankRepo.findById("acb")
    .orElseThrow(() ->new RecordNotFoundException("bank", "acb"));

    Bank vp = bankRepo.findById("vp")
    .orElseThrow(() ->new RecordNotFoundException("bank", "vp"));

    User bob = userRepo.findById("0001")
    .orElseThrow(() ->new RecordNotFoundException("users", "0001"));

    User alice = userRepo.findById("0002")
    .orElseThrow(() ->new RecordNotFoundException("users", "0002"));

    User tom = userRepo.findById("0003")
    .orElseThrow(() ->new RecordNotFoundException("users", "0003"));

    List<AccountSaver> list1 = new ArrayList<>();
    Account bob_vcb_1 = new Account("00012", vcb, bob, 10000000D,list1);
    accountRepo.save(bob_vcb_1);
    
    String str1 = "2021-06-14T13:02:00";
    String str2 = "2022-06-14T13:02:00";
    AccountSaver accsaver1 = AccountSaver.builder()
    .id("1234")
    .account(bob_vcb_1)
    .startBalance(2000000D)
    .endBalance(2000000D)
    .months(12L)
    .rate(6.5)
    .typeSave(TypeSave.FINAL)
    .autoSaver(AutoSaver.NON_AUTORENEW)
    .openAt(LocalDateTime.parse(str1))
    .updateAt(null)
    .closeAt(LocalDateTime.parse(str2))
    .build();

    String str11 = "2021-06-14T13:02:00";
    String str22 = "2022-06-14T13:02:00";
    AccountSaver accsaver2 = AccountSaver.builder()
    .id("3333")
    .account(bob_vcb_1)
    .startBalance(5000000D)
    .endBalance(5000000D)
    .months(12L)
    .rate(6.5)
    .typeSave(TypeSave.EVERYMONTH)
    .autoSaver(AutoSaver.AUTORENEW)
    .openAt(LocalDateTime.parse(str11))
    .updateAt(LocalDateTime.parse(str11).plusMonths(1))
    .closeAt(LocalDateTime.parse(str22))
    .build();
    list1.add(accsaver1);
    list1.add(accsaver2);
    accountSaverRepo.save(accsaver1);
    accountSaverRepo.save(accsaver2);
    
    Account bob_vcb_2 = new Account("00013", vcb, bob, 0D,null);
    accountRepo.save(bob_vcb_2);

    Account alice_acb = new Account("78912", acb, alice, 5000000D,null);
    accountRepo.save(alice_acb);

    Account tom_acb = new Account("8901233", acb, tom, 20000000D,null);
    Account tom_vcb = new Account("1212155", vcb, tom, 2000000D,null);
    accountRepo.save(tom_acb);
    accountRepo.save(tom_vcb);

    
  }


  @Override
  public void run(ApplicationArguments args) throws Exception {
    generateAccount();
    
  }
}
