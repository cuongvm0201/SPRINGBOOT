package vn.techmaster.finalproject.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import javax.validation.constraints.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.techmaster.finalproject.exception.UserException;
import vn.techmaster.finalproject.hash.Hashing;
import vn.techmaster.finalproject.model.Roles;
import vn.techmaster.finalproject.model.State;
import vn.techmaster.finalproject.model.User;
import vn.techmaster.finalproject.repository.UserRepo;
import vn.techmaster.finalproject.request.UpdatePasswordRequest;

@Service
public class UserService {
    @Autowired private UserRepo userRepo;
    @Autowired private Hashing hashing;
    @Autowired private ActiveCodeService activeCodeService;
    @Autowired private MailService emailService;
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
    
    public void updatePassword(String id, UpdatePasswordRequest updatePasswordRequest){
       User currentUser = userRepo.findById(id).get();
       currentUser.setHashed_password(hashing.hashPassword(updatePasswordRequest.getNewPassword()));
       currentUser.setUpdateAt(LocalDateTime.now());
       userRepo.save(currentUser);
    }
    public void edit(User user) {
        userRepo.save(user);
    }

    public Optional<User> findById(String id) {
        return userRepo.findById(id);
    }

}
