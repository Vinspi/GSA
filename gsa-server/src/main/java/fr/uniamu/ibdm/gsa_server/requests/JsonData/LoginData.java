package fr.uniamu.ibdm.gsa_server.requests.JsonData;

public class LoginData {

  private boolean admin;
  private String userName;
  private String userTeam;

  public LoginData() {
  }

  public boolean isAdmin() {
    return admin;
  }

  public void setAdmin(boolean admin) {
    this.admin = admin;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserTeam() {
    return userTeam;
  }

  public void setUserTeam(String userTeam) {
    this.userTeam = userTeam;
  }
}
