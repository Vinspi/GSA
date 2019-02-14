package fr.uniamu.ibdm.gsa_server.controllers;

import fr.uniamu.ibdm.gsa_server.requests.JsonData.ProductOverviewData;
import fr.uniamu.ibdm.gsa_server.requests.JsonResponse;
import fr.uniamu.ibdm.gsa_server.requests.RequestStatus;
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
  @PostMapping("/withdrowCart")
  public JsonResponse<String> withdrowCart(@RequestBody List<WithdrowForm> form) {


    return null;
  }

  /**
   * REST controller for the getProductName request.
   *
   * @return A json formatted response.
   */
  @GetMapping("/getProductName")
  public JsonResponse<String> getProductName(@RequestBody Long nlot) {

    return null;
  }

}
