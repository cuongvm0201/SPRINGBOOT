package vn.techmaster.login_authentication.service;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import vn.techmaster.login_authentication.exception.UserException;
import vn.techmaster.login_authentication.hash.Hashing;
import vn.techmaster.login_authentication.model.State;
import vn.techmaster.login_authentication.model.User;
import vn.techmaster.login_authentication.repository.UserRepo;

@Service
@AllArgsConstructor
public class UserServiceInMemory implements UserService {
    private UserRepo userRepo;
    private Hashing hashing;

    @Override
    public User login(String email, String password) {
        Optional<User> o_user = userRepo.findByEmail(email);
        if (!o_user.isPresent()) {
            throw new UserException("User is not found");
        }

        User user = o_user.get();
        // User muốn login phải có trạng thái Active
        if (user.getState() != State.ACTIVE) {
            throw new UserException("User is not activated");
        } 
        // Kiểm tra password
        if (hashing.validatePassword(password, user.getHashed_password())) {
            return user;
        } else {
            throw new UserException("Password is incorrect");
        }
    }

    @Override
    public boolean logout(String email) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public User addUser(String fullname, String email, String password) {

        return userRepo.addUser(fullname, email, hashing.hashPassword(password));
    }

    

    @Override
    public User addUserThenAutoActivate(String fullname, String email, String password) {
        return userRepo.addUser(fullname, email, hashing.hashPassword(password), State.ACTIVE);
    }

    @Override
    public Boolean activateUser(String activation_code) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean updatePassword(String email, String password) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean updateEmail(String email, String newEmail) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public User findById(String id) {
        // TODO Auto-generated method stub
        return null;
    }

    @PostConstruct
    public void addSomeData(){
        this.addUserThenAutoActivate("Vu Manh Cuong", "vmcuong2192@gmail.com", "Cuong123@");
        this.addUser("Vu Van Hoang", "hoang123@gmail.com", "Hoang456@");
    }

}
