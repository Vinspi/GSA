package fr.uniamu.ibdm.gsa_server.controllers;

import fr.uniamu.ibdm.gsa_server.models.Member;
import fr.uniamu.ibdm.gsa_server.models.User;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.LoginData;
import fr.uniamu.ibdm.gsa_server.requests.JsonResponse;
import fr.uniamu.ibdm.gsa_server.requests.RequestStatus;
import fr.uniamu.ibdm.gsa_server.requests.forms.LoginForm;
import fr.uniamu.ibdm.gsa_server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/auth")
@CrossOrigin(allowCredentials = "true", origins = {"http://localhost:4200", "http://localhost", "http://51.77.147.140"})
public class AuthController {

  @Autowired
  UserService userService;

  @Autowired
  HttpSession session;

  /**
   * REST endpoint for login request.
   *
   * @return This method returns a json formatted response for the login request.
   */
  @PostMapping("/login")
  public JsonResponse<LoginData> login(@RequestBody LoginForm loginForm) {

    JsonResponse<LoginData> response;
    User user = userService.login(loginForm.getEmail(), loginForm.getPassword());

    LoginData data = new LoginData();

    if (user == null) {
      response = new JsonResponse<>("bad credentials", RequestStatus.FAIL);
    } else {
      List<String> teams = new ArrayList<>();

      for (Member member : user.getMembers()) {
        teams.add(member.getTeam().getTeamName());
      }

      session.setAttribute("user", user);
      data.setAdmin(user.isAdmin());
      data.setUserName(user.getUserName());
      data.setUserTeam(teams);
      response = new JsonResponse<>(RequestStatus.SUCCESS, data);
    }

    return response;
  }

  /**
   * REST endpoint for checking if the user is already logged in.
   *
   * @return This method returns a json formatted response for the checkLogin request.
   */
  @GetMapping("/checkLogin")
  public JsonResponse<LoginData> checkLogin() {

    JsonResponse<LoginData> response;
    LoginData data = new LoginData();


    if (session.getAttribute("user") == null) {
      response = new JsonResponse<>("not connected", RequestStatus.FAIL);
    } else {
      User u = (User) session.getAttribute("user");

      List<String> teams = new ArrayList<>();

      for (Member member : u.getMembers()) {
        teams.add(member.getTeam().getTeamName());
      }

      data.setAdmin(u.isAdmin());
      data.setUserName(u.getUserName());
      data.setUserTeam(teams);
      response = new JsonResponse<>(RequestStatus.SUCCESS, data);
    }

    return response;
  }

  /**
   * REST endpoint for logging out the users.
   * @return This method returns a json formatted response for the logout request.
   */
  @GetMapping("/logout")
  public JsonResponse<String> logout() {

    session.removeAttribute("user");

    return new JsonResponse<>(RequestStatus.SUCCESS);
  }
}
