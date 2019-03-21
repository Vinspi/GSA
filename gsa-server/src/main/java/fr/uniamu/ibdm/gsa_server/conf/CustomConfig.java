package fr.uniamu.ibdm.gsa_server.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("app")
public class CustomConfig {

  private String ipTechArea;
  private String superAdminPassword;

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

}
