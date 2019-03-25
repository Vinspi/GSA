package fr.uniamu.ibdm.gsa_server.requests.JsonData;

import java.math.BigDecimal;
import java.util.List;

public class ReportData {

  public ReportData() {
  }

  /**
   * Constructor for the ReportData object.
   * 
   * @param withdrawnTransactions list of team withdrawn transaction information
   * @param quarter value of Quarter Enumeration
   * @param year int
   * @param teamLoss param on used by admins
   * @param teamWithdrawalCost sum of cost of all withdrawn transactions
   */
  public ReportData(List<WithdrawnTransactionData> withdrawnTransactions, String quarter,
      Integer year, BigDecimal teamLoss, BigDecimal teamWithdrawalCost) {
    this.withdrawnTransactions = withdrawnTransactions;
    this.quarter = quarter;
    this.year = year;
    this.teamLoss = teamLoss;
    this.teamWithdrawalCost = teamWithdrawalCost;
  }

  private List<WithdrawnTransactionData> withdrawnTransactions;
  private String quarter;
  private Integer year;
  private BigDecimal teamLoss;
  private BigDecimal teamWithdrawalCost;

  public List<WithdrawnTransactionData> getWithdrawnTransactions() {
    return withdrawnTransactions;
  }

  public void setWithdrawnTransactions(List<WithdrawnTransactionData> withdrawnTransactions) {
    this.withdrawnTransactions = withdrawnTransactions;
  }

  public String getQuarter() {
    return quarter;
  }

  public void setQuarter(String quarter) {
    this.quarter = quarter;
  }

  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  public BigDecimal getTeamLoss() {
    return teamLoss;
  }

  public void setTeamLoss(BigDecimal teamLoss) {
    this.teamLoss = teamLoss;
  }

  public BigDecimal getTeamWithdrawalCost() {
    return teamWithdrawalCost;
  }

  public void setTeamWithdrawalCost(BigDecimal teamWithdrawalCost) {
    this.teamWithdrawalCost = teamWithdrawalCost;
  }
}
