package fr.uniamu.ibdm.gsa_server.requests.forms;

public class RemoveAlertForm {

  private long alertId;

  public RemoveAlertForm(long alertId) {
    this.alertId = alertId;
  }

  public RemoveAlertForm() {
  }

  public long getAlertId() {
    return alertId;
  }

  public void setAlertId(long alertId) {
    this.alertId = alertId;
  }
}
