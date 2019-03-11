package fr.uniamu.ibdm.gsa_server.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@ConfigurationProperties("app")
public class CustomConfig {

  private String ipTechArea;
  private String superAdminPassword;

  private String adminAddresses;


  public CustomConfig() {
  }

  public String getIpTechArea() {
    return ipTechArea;
  }

  public void setIpTechArea(String ipTechArea) {
    this.ipTechArea = ipTechArea;
  }

  public String getSuperAdminPassword() {
    return superAdminPassword;
  }

  public void setSuperAdminPassword(String superAdminPassword) {
    this.superAdminPassword = superAdminPassword;
  }

  public List<String> getAdminAddresses() {
    return Arrays.asList(adminAddresses.split(","));
  }

  public void setAdminAddresses(String adminAddresses) {
    this.adminAddresses = adminAddresses;
  }
}
