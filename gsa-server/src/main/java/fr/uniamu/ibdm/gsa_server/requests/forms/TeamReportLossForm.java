package fr.uniamu.ibdm.gsa_server.requests.forms;

public class TeamReportLossForm implements Form {
  private String teamName;
  private Float loss;

  public TeamReportLossForm() {
  }

  public TeamReportLossForm(String teamName, Float loss) {
    this.teamName = teamName;
    this.loss = loss;
  }

  public String getTeamName() {
    return teamName;
  }

  public void setTeamName(String teamName) {
    this.teamName = teamName;
  }

  public Float getLoss() {
    return loss;
  }

  public void setLoss(Float loss) {
    this.loss = loss;
  }

  @Override
  public boolean validate() {
    if (teamName == null || loss == null) {
      return false;
    }

    return true;

  }
}
