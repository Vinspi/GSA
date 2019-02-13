package fr.uniamu.ibdm.gsa_server.models.primarykeys;

import java.io.Serializable;
import java.util.Objects;

public class TeamTrimestrialReportPK implements Serializable {

  private String quarter;
  private int year;
  private Long team;

  public TeamTrimestrialReportPK() {
  }

  public String getQuarter() {
    return quarter;
  }

  public void setQuarter(String quarter) {
    this.quarter = quarter;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public Long getTeam() {
    return team;
  }

  public void setTeam(Long team) {
    this.team = team;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TeamTrimestrialReportPK that = (TeamTrimestrialReportPK) o;
    return year == that.year
        &&
        Objects.equals(quarter, that.quarter)
        &&
        Objects.equals(team, that.team);
  }

  @Override
  public int hashCode() {
    return Objects.hash(quarter, year, team);
  }
}
