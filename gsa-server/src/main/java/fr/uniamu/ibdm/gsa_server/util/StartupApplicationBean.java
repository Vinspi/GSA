package fr.uniamu.ibdm.gsa_server.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import fr.uniamu.ibdm.gsa_server.services.impl.UserServiceImpl;

@Component
public class StartupApplicationBean implements ApplicationListener<ContextRefreshedEvent> {

  @Autowired
  private UserServiceImpl userService;

  @Override
  public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
    userService.registerAccount("Michel", "michel@univ-amu.fr", "pantoufle", "WOCKHARDT LIMITED", false);
    userService.registerAccount("Rosa", "rosa@univ-amu.fr", "pantoufle", "WOCKHARDT LIMITED", true);

    System.out.println("application started");
  }
}
