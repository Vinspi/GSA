package fr.uniamu.ibdm.gsa_server.controllers;

import fr.uniamu.ibdm.gsa_server.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@CrossOrigin(allowCredentials = "true", origins = {"http://localhost:4200"})
public class UserController {

  @Autowired
  HttpSession session;

  @Autowired
  UserServiceImpl userService;

}
