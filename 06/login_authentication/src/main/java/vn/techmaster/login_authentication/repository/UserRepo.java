package vn.techmaster.login_authentication.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import vn.techmaster.login_authentication.exception.UserException;
import vn.techmaster.login_authentication.model.State;
import vn.techmaster.login_authentication.model.User;
import vn.techmaster.login_authentication.service.EmailService;

@Repository
public class UserRepo {
  @Autowired
  EmailService emailService;
  private ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();
  private ConcurrentHashMap<String, String> active_code_user_id = new ConcurrentHashMap<>();
  public List<User> getAll(){
      return users.values().stream().toList();
  }  

  public User addUser(String fullname, String email, String hashed_pass){
      return addUser(fullname, email, hashed_pass, State.PENDING);
  }

  public User addUser(String fullname, String email, String hashed_pass, State state){
      String id = UUID.randomUUID().toString();
      User user = User.builder()
      .id(id)
      .fullname(fullname)
      .email(email)
      .hashed_password(hashed_pass)
      .state(state)
      .build();
      if(state.equals(State.PENDING)){
        String regisCode = UUID.randomUUID().toString();
        active_code_user_id.put(regisCode,id);
        try{
            emailService.sendEmail(email,regisCode);
        }catch (Exception e){
            active_code_user_id.remove(regisCode);
            users.remove(id);
            throw new UserException("Địa chỉ email của bạn không tồn tại");
        }
    }
      users.put(id, user);
      return user;
  }

  public boolean isEmailExist(String email){
      return users.values().stream().filter(user -> user.getEmail().equalsIgnoreCase(email)).count() > 0;
  }

  public Optional<User> findByEmail(String email){
      return users.values().stream().filter(user -> user.getEmail().equalsIgnoreCase(email)).findFirst();
  }

  public void updateUser(User user){
      users.put(user.getId(), user);
  }

public void checkValidate(String code) {
    User user = users.get(active_code_user_id.get(code));
    user.setState(State.ACTIVE);
    users.put(active_code_user_id.get(code),user);
    active_code_user_id.remove(code);
}

  }



