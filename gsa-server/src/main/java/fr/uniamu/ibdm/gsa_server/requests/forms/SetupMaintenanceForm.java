package fr.uniamu.ibdm.gsa_server.requests.forms;

public class SetupMaintenanceForm implements Form {

  private String password;
  private boolean mode;

  public SetupMaintenanceForm() {
  }

  public SetupMaintenanceForm(String password, boolean mode) {
    this.password = password;
    this.mode = mode;
  }

  @Override
  public boolean validate() {
    return true;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean isMode() {
    return mode;
  }

  public void setMode(boolean mode) {
    this.mode = mode;
  }
}
