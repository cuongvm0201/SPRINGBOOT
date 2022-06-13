package vn.techmaster.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.techmaster.bank.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, String> {
  
}
