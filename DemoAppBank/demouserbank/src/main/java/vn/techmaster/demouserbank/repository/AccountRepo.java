package vn.techmaster.demouserbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.techmaster.demouserbank.model.Account;
@Repository
public interface AccountRepo extends JpaRepository<Account,String> {
  
}
