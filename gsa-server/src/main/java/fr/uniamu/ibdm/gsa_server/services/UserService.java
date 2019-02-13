package fr.uniamu.ibdm.gsa_server.services;

import fr.uniamu.ibdm.gsa_server.models.User;

public interface UserService {

  /**
   * Register an account in the database, hashing the password.
   * @param name Name of the user.
   * @param email Email of the user.
   * @param password Password of the user.
   * @param isAdmin admin or not.
   * @return The account created.
   */
  User registerAccount(String name, String email, String password,String teamName, boolean isAdmin);

  /**
   * Log a user, if not in the database return null, otherwise return the user himself.
   * @param email email of the user.
   * @param password password of the user.
   * @return The user logged or null.
   */
  User login(String email, String password);

}
