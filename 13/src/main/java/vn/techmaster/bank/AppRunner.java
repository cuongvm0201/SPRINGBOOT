package vn.techmaster.bank;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import vn.techmaster.bank.exception.RecordNotFoundException;
import vn.techmaster.bank.model.Account;
import vn.techmaster.bank.model.Bank;
import vn.techmaster.bank.model.User;
import vn.techmaster.bank.repository.AccountRepo;
import vn.techmaster.bank.repository.BankRepo;
import vn.techmaster.bank.repository.UserRepo;

@Component
@Transactional
public class AppRunner implements CommandLineRunner{
  @Autowired private EntityManager em;
  @Autowired private UserRepo userRepo;
  @Autowired private AccountRepo accountRepo;
  @Autowired private BankRepo bankRepo;
  private void generateSomeAccount() {
    Bank vcb = bankRepo.findById("vcb")
    .orElseThrow(() ->new RecordNotFoundException("bank", "vcb"));

    Bank acb = bankRepo.findById("acb")
    .orElseThrow(() ->new RecordNotFoundException("bank", "acb"));

    Bank vpbank = bankRepo.findById("vp")
    .orElseThrow(() ->new RecordNotFoundException("bank", "vpbank"));

    User bob = userRepo.findById("0001")
    .orElseThrow(() ->new RecordNotFoundException("users", "0001"));

    User alice = userRepo.findById("0002")
    .orElseThrow(() ->new RecordNotFoundException("users", "0002"));

    User tom = userRepo.findById("0003")
    .orElseThrow(() ->new RecordNotFoundException("users", "0003"));

    Account bob_vcb_1 = new Account("00012", vcb, bob, 1000L);
    accountRepo.save(bob_vcb_1);

    Account bob_vcb_2 = new Account("00013", vcb, bob, 0L);
    accountRepo.save(bob_vcb_2);

    Account alice_acb = new Account("78912", acb, alice, 500L);
    accountRepo.save(alice_acb);

    Account tom_acb = new Account("8901233",acb,tom,2000L);
    Account tom_vcb = new Account("1212155",vcb,tom,200L);
    accountRepo.save(tom_acb);
    accountRepo.save(tom_vcb);

 }

  @Override
  public void run(String... args) throws Exception {
    generateSomeAccount();
    
  }

  
  
}
