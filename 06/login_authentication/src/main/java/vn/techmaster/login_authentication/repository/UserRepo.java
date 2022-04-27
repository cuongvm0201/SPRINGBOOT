package vn.techmaster.login_authentication.repository;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import vn.techmaster.login_authentication.model.State;
import vn.techmaster.login_authentication.model.User;

@Repository
public class UserRepo {
  private ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();
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

  @PostConstruct
  public void addSomeData(){
     this.addUser("Vu Manh Cuong", "vmcuong2192@gmail.com", "Cuong123@", State.ACTIVE);
     this.addUser("Vu Van Toan", "toan123@gmail.com", "ToanMiss@", State.PENDING);
     
  }

  }



