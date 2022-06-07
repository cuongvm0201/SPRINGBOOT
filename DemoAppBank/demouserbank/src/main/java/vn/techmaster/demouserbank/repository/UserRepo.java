package vn.techmaster.demouserbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.techmaster.demouserbank.model.User;
@Repository
public interface UserRepo extends JpaRepository<User,String> {
    
}
