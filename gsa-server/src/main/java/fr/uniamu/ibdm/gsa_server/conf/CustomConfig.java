package fr.uniamu.ibdm.gsa_server.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
@ConfigurationProperties("app")
@EnableWebMvc
public class CustomConfig implements WebMvcConfigurer {

  @Autowired
  MaintenanceInterceptor maintenanceInterceptor;

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
  
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(maintenanceInterceptor).excludePathPatterns("/admin/setupMaintenanceMode");
  }

}
