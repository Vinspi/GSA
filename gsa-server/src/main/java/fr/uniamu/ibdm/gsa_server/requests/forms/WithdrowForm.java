package fr.uniamu.ibdm.gsa_server.requests.forms;

public class WithdrowForm {

  private Long nlot;
  private int quantity;
  private String teamName;

  public WithdrowForm() {
  }

  public WithdrowForm(Long nlot, int quantity, String teamName) {
    this.nlot = nlot;
    this.quantity = quantity;
    this.teamName = teamName;
  }

  public Long getNlot() {
    return nlot;
  }

  public void setNlot(Long nlot) {
    this.nlot = nlot;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public String getTeamName() {
    return teamName;
  }

  public void setTeamName(String teamName) {
    this.teamName = teamName;
  }
}
