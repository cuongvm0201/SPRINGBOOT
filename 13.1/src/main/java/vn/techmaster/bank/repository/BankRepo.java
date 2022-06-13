package vn.techmaster.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.techmaster.bank.model.Bank;

@Repository
public interface BankRepo extends JpaRepository<Bank, String>{
  
}
