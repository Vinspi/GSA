package fr.uniamu.ibdm.gsa_server.requests.JsonData;

import fr.uniamu.ibdm.gsa_server.models.enumerations.AlertType;

public class AlertsData {

  private String productName;
  private int seuil;
  private AlertType alertType;
  private long alertId;

  /**
   * Constructor for AlertsData object.
   *
   * @param productName The name of the product.
   * @param seuil Seuil of the alert.
   * @param alertType Type of the alert.
   * @param alertId Id of the alert.
   */
  public AlertsData(String productName, int seuil, AlertType alertType, long alertId) {
    this.productName = productName;
    this.seuil = seuil;
    this.alertType = alertType;
    this.alertId = alertId;
  }

  public AlertsData() {
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public int getSeuil() {
    return seuil;
  }

  public void setSeuil(int seuil) {
    this.seuil = seuil;
  }

  public AlertType getAlertType() {
    return alertType;
  }

  public void setAlertType(AlertType alertType) {
    this.alertType = alertType;
  }

  public long getAlertId() {
    return alertId;
  }

  public void setAlertId(long alertId) {
    this.alertId = alertId;
  }
}
