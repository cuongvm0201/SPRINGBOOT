package vn.techmaster.finalproject.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.techmaster.finalproject.exception.UserException;
import vn.techmaster.finalproject.hash.Hashing;
import vn.techmaster.finalproject.model.ActiveCode;
import vn.techmaster.finalproject.model.Roles;
import vn.techmaster.finalproject.model.State;
import vn.techmaster.finalproject.model.User;
import vn.techmaster.finalproject.repository.ActiveCodeRepo;
import vn.techmaster.finalproject.repository.UserRepo;
import vn.techmaster.finalproject.request.UpdatePasswordRequest;

@Service
public class UserService {
    @Autowired private UserRepo userRepo;
    @Autowired private Hashing hashing;
    @Autowired private ActiveCodeService activeCodeService;
    @Autowired private ActiveCodeRepo activeCodeRepo;
    @Autowired private EmailService emailService;
    public List<User> getAllUser(){
        return userRepo.findAll();
    }

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
          String currentCodeID = activeCodeService.getActiveCodeByID().get(regisCode);
          ActiveCode currentCode = activeCodeRepo.findById(currentCodeID).get();
          try{
            emailService.sendEmail(email,regisCode);
          }catch (Exception e){
            activeCodeRepo.deleteById(currentCodeID);
              userRepo.delete(user);
              throw new UserException("Địa chỉ email của bạn không tồn tại");
          }
      }
        userRepo.save(user);
        return user;
    }

    public User addUserByAdmin(String fullname, String email, String password, State state, Roles role) {
        String id = UUID.randomUUID().toString();
        User newUserByAdmin = User.builder()
        .id(id)
        .fullname(fullname)
        .email(email)
        .hashed_password(hashing.hashPassword(password))
        .state(state)
        .role(role)
        .build();
       
        userRepo.save(newUserByAdmin);
        return newUserByAdmin;
    }
    
    public void updatePassword(String id, UpdatePasswordRequest updatePasswordRequest){
       User currentUser = userRepo.findById(id).get();
       currentUser.setHashed_password(hashing.hashPassword(updatePasswordRequest.newPassword()));
       currentUser.setUpdateAt(LocalDateTime.now());
       userRepo.save(currentUser);
    }
    public void edit(User user) {
        userRepo.save(user);
    }

    public Optional<User> findById(String id) {
        return userRepo.findById(id);
    }
    
    public boolean checkEmail(String email){
        for (int i = 0; i < userRepo.findAll().size(); i++) {
            if(userRepo.findAll().get(i).getEmail().equalsIgnoreCase(email)){
                return true;
            }
        }
        return false;        
    }

    public void checkValidate(String code) {
        User user = userRepo.findById(activeCodeService.getAllActiveCode().get(code)).get();
        String currentCodeID = activeCodeService.getActiveCodeByID().get(code);
        user.setState(State.ACTIVE);
        userRepo.save(user);
        activeCodeRepo.deleteById(currentCodeID);
    }

}
