package fr.uniamu.ibdm.gsa_server.services;

import fr.uniamu.ibdm.gsa_server.models.User;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.ProductOverviewData;
import fr.uniamu.ibdm.gsa_server.requests.forms.WithdrowForm;

import java.util.List;

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


  /**
   * This method retrieve all products and the quantity of them left in the visible storage.
   * @return A list of objects containing the product name and the quantity.
   */
  List<ProductOverviewData> getAllOverviewProducts();

  /**
   * This method retrieve the name of the product based on the aliquot's lot number.
   * @param nlot The lot number of the aliquot.
   * @return The name of the product for the nlot specified.
   */
  String getProductNameFromNlot(Long nlot);

  /**
   * This method perform a withdrow for every aliquot in the shopping cart.
   * @param cart The shopping cart.
   * @return true if the withdrow can be performed, false otherwise.
   */
  boolean withdrawCart(List<WithdrowForm> cart);

}
