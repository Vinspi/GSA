package fr.uniamu.ibdm.gsa_server.requests.JsonData;

import java.util.List;

public class LoginData {

  private boolean admin;
  private String userName;
  private List<String> userTeams;

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

  public List<String> getUserTeams() {
    return userTeams;
  }

  public void setUserTeam(List<String> userTeams) {
    this.userTeams = userTeams;
  }
}
