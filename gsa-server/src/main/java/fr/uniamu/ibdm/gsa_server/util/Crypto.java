package fr.uniamu.ibdm.gsa_server.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Crypto {

  /**
   * Hash a password with SHA-512 algorithm, a salt must be provided.
   * @param salt salt use to hash the password.
   * @param password password to be hash.
   * @return password hashed.
   */
  public static byte[] hashPassword(byte[] salt, byte[] password) {

    try {

      MessageDigest md = MessageDigest.getInstance("SHA-512");
      md.update(salt);
      byte[] hash = md.digest(password);
      return hash;

    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      return null;
    }
  }

}
