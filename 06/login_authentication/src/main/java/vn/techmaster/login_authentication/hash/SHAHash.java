package vn.techmaster.login_authentication.hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
@Primary
@Component
public class SHAHash implements Hashing {

  //Chú ý đây là một lỗ hổng bảo mật, đúng ra cần phải sinh động salt mới mỗi khi hash password.
  //Sau đó lưu cả salt và hash password vào CSDL !
  private String salt = "Õ1X-343n42mnl3905u";
  
  @Override
  public String hashPassword(String password) {
    String generatedPassword = null;
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      md.update(salt.getBytes());
      byte[] bytes = md.digest(password.getBytes());
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < bytes.length; i++) {
        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
            .substring(1));
      }
      generatedPassword = sb.toString();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return generatedPassword;
  }

  @Override
  public boolean validatePassword(String originalPassword, String storedPassword) {
    String hashed_pass = hashPassword(originalPassword);
    System.out.println(originalPassword);
    System.out.println("-------");
    System.out.println(hashed_pass);
    return storedPassword.equals(hashed_pass);
  }
}
