package vn.techmaster.login_authentication.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


import org.springframework.stereotype.Repository;

import vn.techmaster.login_authentication.model.State;
import vn.techmaster.login_authentication.model.User;

@Repository
public class UserRepo {
  private ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();
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

  }



