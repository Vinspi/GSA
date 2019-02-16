package fr.uniamu.ibdm.gsa_server.dao.QueryObjects;

import java.math.BigDecimal;

public class StatsWithdrawQuery {
  private int month;
  private int year;
  private int withdraw;


  public StatsWithdrawQuery() {
  }

  /**
   * Constructor.
   *
   * @param month Month of the data.
   * @param year Year of the data.
   * @param withdraw Number of withdrawals performed for this month and year
   */
  public StatsWithdrawQuery(int month, int year, BigDecimal withdraw) {
    this.month = month;
    this.withdraw = withdraw.intValue();
    this.year = year;
  }

  /**
   * Constructor.
   *
   * @param month Month of the data.
   * @param year Year of the data.
   * @param withdraw Number of withdrawals performed for this month and year
   */
  public StatsWithdrawQuery(int month, int year, int withdraw) {
    this.month = month;
    this.year = year;
    this.withdraw = withdraw;
  }

  public int getMonth() {
    return month;
  }

  public void setMonth(int month) {
    this.month = month;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public void setWithdraw(int withdraw) {
    this.withdraw = withdraw;
  }

  public void setWithdraw(BigDecimal withdraw) {
    this.withdraw = withdraw.intValue();
  }

  public int getWithdraw() {
    return withdraw;
  }


}
