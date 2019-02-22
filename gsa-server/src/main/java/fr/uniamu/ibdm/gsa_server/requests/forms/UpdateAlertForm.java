package fr.uniamu.ibdm.gsa_server.requests.forms;

public class UpdateAlertForm {

  private long alertId;
  private int seuil;

  public UpdateAlertForm(long alertId, int seuil) {
    this.alertId = alertId;
    this.seuil = seuil;
  }

  public UpdateAlertForm() {
  }

  public long getAlertId() {
    return alertId;
  }

  public void setAlertId(long alertId) {
    this.alertId = alertId;
  }

  public int getSeuil() {
    return seuil;
  }

  public void setSeuil(int seuil) {
    this.seuil = seuil;
  }
}
