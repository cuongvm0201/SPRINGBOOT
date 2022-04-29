package vn.techmaster.login_authentication.hash;

public interface Hashing {
    public String hashPassword(String password);
    public boolean validatePassword(String originalPassword, String storedPassword);
  }
  
