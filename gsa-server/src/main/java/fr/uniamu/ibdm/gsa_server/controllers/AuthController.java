package fr.uniamu.ibdm.gsa_server.controllers;

import fr.uniamu.ibdm.gsa_server.requests.JsonResponse;
import fr.uniamu.ibdm.gsa_server.requests.RequestStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

  /**
   * REST controller method for login request.
   * @return This method returns a json formatted response for the login request.
   */
  @PostMapping("/login")
  public JsonResponse<String> login() {
    JsonResponse<String> response = new JsonResponse<>(RequestStatus.SUCCESS, "coucou");

    return response;
  }

}
