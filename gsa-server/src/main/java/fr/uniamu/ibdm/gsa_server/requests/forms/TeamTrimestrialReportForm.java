package fr.uniamu.ibdm.gsa_server.requests.forms;

import java.util.Arrays;
import java.util.stream.Collectors;

import fr.uniamu.ibdm.gsa_server.models.enumerations.Quarter;

public class TeamTrimestrialReportForm {

  private Integer losts;
  private Boolean finalFlag;
  private Integer year;
  private String quarter;
  private Long teamId;

  public Integer getLosts() {
    return losts;
  }

  public void setLosts(Integer losts) {
    this.losts = losts;
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

  public Long getTeamId() {
    return teamId;
  }

  public void setTeamId(Long teamId) {
    this.teamId = teamId;
  }

  //Should be part of a Form interface.
  public boolean isValid() {
    if (quarter == null || finalFlag == null || teamId == null || losts == null || year == null) {
      return false;
    }

    return Arrays.stream(Quarter.values()).map(Quarter::name).collect(Collectors.toSet()).contains(quarter);
  }
}
