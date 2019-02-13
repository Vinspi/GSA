package fr.uniamu.ibdm.gsa_server.controllers;

import fr.uniamu.ibdm.gsa_server.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/admin")
@CrossOrigin(allowCredentials = "true", origins = {"http://localhost:4200"})
public class AdminController {

  @Autowired
  HttpSession session;

  @Autowired
  AdminService adminService;

}
