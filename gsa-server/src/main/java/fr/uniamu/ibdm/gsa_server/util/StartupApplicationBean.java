package fr.uniamu.ibdm.gsa_server.util;

import fr.uniamu.ibdm.gsa_server.services.impl.EmailServiceImpl;
import fr.uniamu.ibdm.gsa_server.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class StartupApplicationBean implements ApplicationListener<ContextRefreshedEvent> {

  @Autowired
  private UserServiceImpl userService;

  @Autowired
  private EmailServiceImpl emailService;

  @Override
  public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
    userService.registerAccount("testA", "testA@test.com", "pantoufle", "Walgreen Company", true);
    userService.registerAccount("testU", "testU@test.com", "pantoufle", "Walgreen Company", false);



    System.out.println("application started");
  }
}
