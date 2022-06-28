package vn.techmaster.finalproject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.techmaster.finalproject.model.User;

@Repository
public interface UserRepo extends JpaRepository<User,String> {
    Optional<User> findUsersByEmail(String email);
}
