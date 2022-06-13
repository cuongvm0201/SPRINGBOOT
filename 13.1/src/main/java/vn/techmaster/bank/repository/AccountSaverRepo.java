package vn.techmaster.bank.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.techmaster.bank.model.Account;
import vn.techmaster.bank.model.AccountSaver;
@Repository
public interface AccountSaverRepo extends JpaRepository<AccountSaver,String> {
   
}
