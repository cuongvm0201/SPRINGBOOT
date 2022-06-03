package com.example.demo.service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import com.example.demo.exception.UserException;
import com.example.demo.hash.Hashing;
import com.example.demo.model.ActiveCode;
import com.example.demo.model.Roles;
import com.example.demo.model.State;
import com.example.demo.model.User;
import com.example.demo.repository.ActiveCodeRepo;
import com.example.demo.repository.JobRepo;
import com.example.demo.repository.UserRepo;

@Service
@AllArgsConstructor
public class UserServiceInMemory implements UserService {
    private EmailService emailService;
    private UserRepo userRepo;
    private Hashing hashing;
    private ActiveCodeService activeCodeService;
    private ActiveCodeRepo activeCodeRepo;
    @Override
    public User login(String email, String password) {
        Optional<User> o_user = userRepo.findUsersByEmail(email);
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
        String id = UUID.randomUUID().toString();
        User user = User.builder()
        .id(id)
        .fullname(fullname)
        .email(email)
        .hashed_password(hashing.hashPassword(password))
        .state(State.PENDING)
        .role(Roles.MEMBER)
        .build();
        if(user.getState().equals(State.PENDING)){
          String regisCode = UUID.randomUUID().toString();
          activeCodeService.addCode(regisCode, id);
          try{
              emailService.sendEmail(email,regisCode);
          }catch (Exception e){
            // activeCodeService.getAllActiveCode().remove(regisCode);
              userRepo.delete(user);
              throw new UserException("Địa chỉ email của bạn không tồn tại");
          }
      }
        userRepo.save(user);
        return user;
    }

    

    // @Override
    // public User addUserThenAutoActivate(String fullname, String email, String password) {
    //     return userRepo.save(fullname, email, hashing.hashPassword(password), State.ACTIVE, Roles.MEMBER);
    // }

    // @Override
    // public User addUserThenAutoActivateVIP(String fullname, String email, String password) {
    //     return userRepo.addUser(fullname, email, hashing.hashPassword(password), State.ACTIVE, Roles.ADMIN);
    // }

    @Override
    public Boolean activateUser(String activation_code) {
      
        return null;
    }

    public boolean isEmailExist(String email){
        return userRepo.findAll().stream().filter(user -> user.getEmail().equalsIgnoreCase(email)).count() > 0;
    }
    @Override
    public Optional<User> findByEmail(String email){
        return userRepo.findAll().stream().filter(user -> user.getEmail().equalsIgnoreCase(email)).findFirst();
    }
  
    public void updateUser(User user){
        userRepo.save(user);
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
    public User findById(String id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void checkValidate(String code) {
        User user = userRepo.findById(activeCodeService.getAllActiveCode().get(code)).get();
        user.setState(State.ACTIVE);
        userRepo.save(user);
        // activeCodeService.getAllActiveCode().remove(code);
    }
    

}
