package fr.uniamu.ibdm.gsa_server.requests.JsonData;

public class TeamPriceLossesData {
  private String teamName;
  private Float losses;
  
  public TeamPriceLossesData() {
  }
  
  public TeamPriceLossesData(String teamName, Float losses) {
    this.teamName = teamName;
    this.losses = losses;
  }
  
  public String getTeamName() {
    return teamName;
  }

  public void setTeamName(String teamName) {
    this.teamName = teamName;
  }

  public Float getLosses() {
    return losses;
  }

  public void setLosses(Float losses) {
    this.losses = losses;
  }
  
}
