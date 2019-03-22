package fr.uniamu.ibdm.gsa_server.requests.forms;

import java.math.BigDecimal;

public class TeamReportLossForm implements Form {
  private String teamName;
  private BigDecimal loss;

  public TeamReportLossForm() {
  }

  public TeamReportLossForm(String teamName, BigDecimal loss) {
    this.teamName = teamName;
    this.loss = loss;
  }

  public String getTeamName() {
    return teamName;
  }

  public void setTeamName(String teamName) {
    this.teamName = teamName;
  }

  public BigDecimal getLoss() {
    return loss;
  }

  public void setLoss(BigDecimal loss) {
    this.loss = loss;
  }

  @Override
  public boolean validate() {
    if (teamName == null || loss == null) {
      return false;
    }

    PriceForm priceForm = new PriceForm(loss);
    if (!priceForm.validate()) {
      return false;
    }

    return true;

  }
}
