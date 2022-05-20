package vn.techmaster.demomanageruser_jobhunt.hash;

public interface Hashing {
    public String hashPassword(String password);
    public boolean validatePassword(String originalPassword, String storedPassword);
  }
  
