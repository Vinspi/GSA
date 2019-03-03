package fr.uniamu.ibdm.gsa_server.dao.QueryObjects;

import fr.uniamu.ibdm.gsa_server.models.enumerations.AlertType;

import java.util.List;


public class TriggeredAlertsQuery {

  private String source;
  private String target;
  private int qte;
  private int seuil;
  private AlertType alertType;
  private List<AlertAliquot> aliquots;
  private long alertId;

  /**
   * Constructor for triggered alert object.
   *
   * @param source Source of the product on which the alert if for.
   * @param target Source of the product on which the alert if for.
   * @param qte Quantity left for the current product in the current stock.
   * @param seuil Seuil of the alert.
   * @param alertType Type of the alert.
   * @param aliquots All aliquots linked to the current product.
   * @param alertId Id of the alert.
   */
  public TriggeredAlertsQuery(String source, String target, int qte, int seuil, AlertType alertType, List<AlertAliquot> aliquots, long alertId) {
    this.source = source;
    this.target = target;
    this.qte = qte;
    this.seuil = seuil;
    this.alertType = alertType;
    this.aliquots = aliquots;
    this.alertId = alertId;
  }

  public TriggeredAlertsQuery() {
  }

  public List<AlertAliquot> getAliquots() {
    return aliquots;
  }

  public void setAliquots(List<AlertAliquot> aliquots) {
    this.aliquots = aliquots;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public String getTarget() {
    return target;
  }

  public void setTarget(String target) {
    this.target = target;
  }

  public int getQte() {
    return qte;
  }

  public void setQte(int qte) {
    this.qte = qte;
  }

  public Integer getSeuil() {
    return seuil;
  }

  public void setSeuil(Integer seuil) {
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
