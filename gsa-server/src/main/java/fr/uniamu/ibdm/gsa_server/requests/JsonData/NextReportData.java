package fr.uniamu.ibdm.gsa_server.requests.JsonData;

public class NextReportData {

  private boolean available;
  private long nextReportIn;

  public NextReportData(boolean available, long nextReportIn) {
    this.available = available;
    this.nextReportIn = nextReportIn;
  }

  public NextReportData() {
  }

  public boolean isAvailable() {
    return available;
  }

  public void setAvailable(boolean available) {
    this.available = available;
  }

  public long getNextReportIn() {
    return nextReportIn;
  }

  public void setNextReportIn(long nextReportIn) {
    this.nextReportIn = nextReportIn;
  }
}
