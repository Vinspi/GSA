package fr.uniamu.ibdm.gsa_server.requests.JsonData;

import java.time.LocalDate;

public class AliquotExpired {
  private long nlot;
  private LocalDate date;

  public AliquotExpired(long nlot, LocalDate date) {
    this.nlot = nlot;
    this.date = date;
  }

  public AliquotExpired() {
  }

  public long getNlot() {
    return nlot;
  }

  public void setNlot(long nlot) {
    this.nlot = nlot;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }
}
