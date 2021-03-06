package fr.uniamu.ibdm.gsa_server.conf;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Scope(value = "application")
@Component
public class MaintenanceBean {

  private boolean maintenanceMode;

  @PostConstruct
  public void postConstruct() {
    
  }

  public MaintenanceBean() {
    maintenanceMode = false;
  }

  public boolean isMaintenanceMode() {
    return maintenanceMode;
  }

  public void setMaintenanceMode(boolean maintenanceMode) {
    this.maintenanceMode = maintenanceMode;
  }
}
