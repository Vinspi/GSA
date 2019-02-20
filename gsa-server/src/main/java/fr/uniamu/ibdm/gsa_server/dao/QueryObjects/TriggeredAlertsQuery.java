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

  public TriggeredAlertsQuery(String source, String target, int qte, int seuil, AlertType alertType, List<AlertAliquot> aliquots) {
    this.source = source;
    this.target = target;
    this.qte = qte;
    this.seuil = seuil;
    this.alertType = alertType;
    this.aliquots = aliquots;
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

  public void setSeuil(int seuil) {
    this.seuil = seuil;
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
}
