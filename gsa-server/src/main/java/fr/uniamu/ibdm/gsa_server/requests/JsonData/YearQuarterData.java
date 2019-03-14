package fr.uniamu.ibdm.gsa_server.requests.JsonData;

public class YearQuarterData {
  private String quarter;
  private Integer year;

  public YearQuarterData() {
  }

  public YearQuarterData(String quarter, Integer year) {
    this.quarter = quarter;
    this.year = year;
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

}
