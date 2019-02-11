package fr.uniamu.ibdm.gsa_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class GsaServerApplication extends SpringBootServletInitializer {

  public static void main(String[] args) {
    SpringApplication.run(GsaServerApplication.class, args);
  }

}

