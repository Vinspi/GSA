package fr.uniamu.ibdm.gsa_server.models;

import fr.uniamu.ibdm.gsa_server.models.enumerations.AlertType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
public class Alert implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long alertId;

  private int seuil;

  @Enumerated(EnumType.STRING)
  private AlertType alertType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns({
      @JoinColumn(name = "source", insertable = false, updatable = false),
      @JoinColumn(name = "target", insertable = false, updatable = false)
  })
  private Product product;

  public Alert() {
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

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public AlertType getAlertType() {
    return alertType;
  }

  public void setAlertType(AlertType alertType) {
    this.alertType = alertType;
  }

}
