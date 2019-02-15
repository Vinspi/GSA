package fr.uniamu.ibdm.gsa_server.controllers;

import fr.uniamu.ibdm.gsa_server.models.User;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.ProductOverviewData;
import fr.uniamu.ibdm.gsa_server.requests.JsonResponse;
import fr.uniamu.ibdm.gsa_server.requests.RequestStatus;
import fr.uniamu.ibdm.gsa_server.requests.forms.GetProductNameForm;
import fr.uniamu.ibdm.gsa_server.requests.forms.WithdrowForm;
import fr.uniamu.ibdm.gsa_server.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@CrossOrigin(allowCredentials = "true", origins = {"http://localhost:4200"})
public class UserController {

  @Autowired
  HttpSession session;

  @Autowired
  UserServiceImpl userService;

  /**
   * REST controller for the stockOverview request.
   *
   * @return A json formatted response.
   */
  @GetMapping("/stockOverview")
  public JsonResponse<List<ProductOverviewData>> stockOverview() {

    List<ProductOverviewData> data = userService.getAllOverviewProducts();

    return new JsonResponse<>(RequestStatus.SUCCESS, data);
  }

  /**
   * REST controller for the withdrowCart request.
   *
   * @return A json formatted response.
   */
  @PostMapping("/withdrawCart")
  public JsonResponse<Boolean> withdrawCart(@RequestBody List<WithdrowForm> form) {

    boolean data = userService.withdrawCart(form,(User) session.getAttribute("user"));
    JsonResponse<Boolean> response;

    if (data) {
      response = new JsonResponse<>(RequestStatus.SUCCESS);
    } else {
      response = new JsonResponse<>("bad aliquot nlot", RequestStatus.FAIL);
    }

    return response;
  }

  /**
   * REST controller for the getProductName request, return a product name from a lot number.
   *
   * @return A json formatted response.
   */
  @PostMapping("/getProductName")
  public JsonResponse<String> getProductName(@RequestBody GetProductNameForm form) {

    String productName = userService.getProductNameFromNlot(form.getNlot());
    JsonResponse<String> response;

    if (productName == null) {
      response = new JsonResponse<>("bad lot number", RequestStatus.FAIL);
    } else {
      response = new JsonResponse<>(RequestStatus.SUCCESS, productName);
    }

    return response;
  }

  /**
   * REST controller for the getAllTeamName request, return a list of all team name.
   *
   * @return A json formatted response.
   */
  @GetMapping("/getAllTeamName")
  public JsonResponse<List<String>> getAllTeamName() {

    return new JsonResponse<>(RequestStatus.SUCCESS, userService.getAllTeamName());
  }

  /**
   * REST controller for the getAllProductName request, return a list of all product name.
   *
   * @return A json formatted response.
   */
  @GetMapping("/getAllProductName")
  public JsonResponse<List<String>> getAllProductName() {

    return new JsonResponse<>(RequestStatus.SUCCESS, userService.getAllProductName());
  }

}
