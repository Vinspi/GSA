package fr.uniamu.ibdm.gsa_server.requests.JsonData;

public class EditableReportYearQuarterData {
  private String quarter;
  private Integer year;
  
  public EditableReportYearQuarterData() {
  }
  
  public EditableReportYearQuarterData(String quarter, Integer year) {
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
