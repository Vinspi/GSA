package fr.uniamu.ibdm.gsa_server.requests.JsonData;

import java.util.List;

public class TeamReportData {
  private List<ReportData> reports;
  private String teamName;

  public TeamReportData() {
  }

  public TeamReportData(List<ReportData> reports, String teamName) {
    this.reports = reports;
    this.teamName = teamName;
  }

  public List<ReportData> getReports() {
    return reports;
  }

  public void setReports(List<ReportData> reports) {
    this.reports = reports;
  }

  public String getTeamName() {
    return teamName;
  }

  public void setTeamName(String teamName) {
    this.teamName = teamName;
  }

}
