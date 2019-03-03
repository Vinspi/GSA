package fr.uniamu.ibdm.gsa_server.requests.forms;

public class AddTeamTrimestrialReportForm implements Form {

  private Float losses;
  private Boolean finalFlag;
  private Integer year;
  private String quarter;
  private String teamName;

  public AddTeamTrimestrialReportForm() {
  }
  
  /**
   * Constructor for AddTeamTrimestrialReportForm object.
   * 
   * @param losses bill 
   * @param finalFlag is editable flag
   * @param year report year
   * @param quarter value of Quarter enumeration
   * @param teamName String
   */
  public AddTeamTrimestrialReportForm(Float losses, Boolean finalFlag, Integer year, String quarter,
      String teamName) {
    this.losses = losses;
    this.finalFlag = finalFlag;
    this.year = year;
    this.quarter = quarter;
    this.teamName = teamName;
  }  
  
  public Float getLosses() {
    return losses;
  }

  public void setLosses(Float losses) {
    this.losses = losses;
  }

  public Boolean getFinalFlag() {
    return finalFlag;
  }

  public void setFinalFlag(Boolean finalFlag) {
    this.finalFlag = finalFlag;
  }

  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  public String getQuarter() {
    return quarter;
  }

  public void setQuarter(String quarter) {
    this.quarter = quarter;
  }

  public String getTeamName() {
    return teamName;
  }

  public void setTeamName(String teamName) {
    this.teamName = teamName;
  }

  @Override
  public boolean validate() {
    if (quarter == null || finalFlag == null || teamName == null || losses == null || year == null) {
      return false;
    }
    return true;
  }

}
