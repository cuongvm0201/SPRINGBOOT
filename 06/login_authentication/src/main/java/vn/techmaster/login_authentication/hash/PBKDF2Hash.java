package vn.techmaster.login_authentication.hash;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
/*
https://howtodoinjava.com/java/java-security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/
*/

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
public class PBKDF2Hash implements Hashing {
  public String hashPassword(String password) {
    int iterations = 1000;
    char[] chars = password.toCharArray();
    byte[] salt;
    try {
      salt = getSalt();
    } catch (NoSuchAlgorithmException e) {
      return null;
    }

    PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
    SecretKeyFactory skf;
    try {
      skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
    } catch (NoSuchAlgorithmException e) {
      return null;
    }

    byte[] hash;
    try {
      hash = skf.generateSecret(spec).getEncoded();
    } catch (InvalidKeySpecException e) {
      return null;
    }
    try {
      return iterations + ":" + toHex(salt) + ":" + toHex(hash);
    } catch (NoSuchAlgorithmException e) {
      return null;
    }
  }

  public boolean validatePassword(String originalPassword, String storedPassword) {
    String[] parts = storedPassword.split(":");
    int iterations = Integer.parseInt(parts[0]);

    byte[] salt;
    byte[] hash;
    try {
      salt = fromHex(parts[1]);
      hash = fromHex(parts[2]);
    } catch (NoSuchAlgorithmException e) {
      return false;
    }

    PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(),
        salt, iterations, hash.length * 8);
    SecretKeyFactory skf;
    try {
      skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
    } catch (NoSuchAlgorithmException e) {
      return false;
    }
    byte[] testHash;
    try {
      testHash = skf.generateSecret(spec).getEncoded();
    } catch (InvalidKeySpecException e) {
      return false;
    }

    int diff = hash.length ^ testHash.length;
    for (int i = 0; i < hash.length && i < testHash.length; i++) {
      diff |= hash[i] ^ testHash[i];
    }
    return diff == 0;
  }

  private byte[] getSalt() throws NoSuchAlgorithmException {
    SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
    byte[] salt = new byte[16];
    sr.nextBytes(salt);
    return salt;
  }

  private String toHex(byte[] array) throws NoSuchAlgorithmException {
    BigInteger bi = new BigInteger(1, array);
    String hex = bi.toString(16);

    int paddingLength = (array.length * 2) - hex.length();
    if (paddingLength > 0) {
      return String.format("%0" + paddingLength + "d", 0) + hex;
    } else {
      return hex;
    }
  }

  private byte[] fromHex(String hex) throws NoSuchAlgorithmException {
    byte[] bytes = new byte[hex.length() / 2];
    for (int i = 0; i < bytes.length; i++) {
      bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
    }
    return bytes;
  }
}
