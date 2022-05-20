package vn.techmaster.demomanageruser_jobhunt.service;

import java.util.Optional;

import vn.techmaster.demomanageruser_jobhunt.model.User;

public interface UserService {
    public User login(String email, String password);
    public boolean logout(String email);
    
    public User addUser(String fullname, String email, String password);
    public Boolean activateUser(String activation_code);

    public Boolean updatePassword(String email, String password);
    public Boolean updateEmail(String email, String newEmail);

    public Optional<User> findByEmail(String email);
    public User findById(String id);
    public User addUserThenAutoActivate(String fullname, String email, String password);
    public User addUserThenAutoActivateVIP(String fullname, String email, String password);
}   
