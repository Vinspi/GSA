package fr.uniamu.ibdm.gsa_server.requests.forms;

public class LoginForm {

  private String email;
  private String password;

  public LoginForm() {
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
