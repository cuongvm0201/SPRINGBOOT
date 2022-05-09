package vn.techmaster.login_authentication.service;

import java.util.Optional;

import vn.techmaster.login_authentication.model.User;

public interface UserService {
    public User login(String email, String password);
    public boolean logout(String email);
    
    public User addUser(String fullname, String email, String password);
    public Boolean activateUser(String email, String activation_code);

    public Boolean updatePassword(String email, String password);
    public Boolean updateEmail(String email, String newEmail);

    public Optional<User> findByEmail(String email);
    public User findById(String id);
    public User addUserThenAutoActivate(String fullname, String email, String password);
    public String generatedActivecode();
}   
