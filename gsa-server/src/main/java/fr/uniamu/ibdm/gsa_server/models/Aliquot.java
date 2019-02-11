package fr.uniamu.ibdm.gsa_server.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class Aliquot implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long aliquotId;

  private String aliquotName;
  private long aliquotNLot;
  private LocalDate aliquotExpirationDate;
  private long aliquotQuantity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id")
  private Product product;


  public Aliquot() {
  }

  public long getAliquotId() {
    return aliquotId;
  }

  public void setAliquotId(long aliquotId) {
    this.aliquotId = aliquotId;
  }

  public String getAliquotName() {
    return aliquotName;
  }

  public void setAliquotName(String aliquotName) {
    this.aliquotName = aliquotName;
  }

  public long getAliquotNLot() {
    return aliquotNLot;
  }

  public void setAliquotNLot(long aliquotNLot) {
    this.aliquotNLot = aliquotNLot;
  }

  public LocalDate getAliquotExpirationDate() {
    return aliquotExpirationDate;
  }

  public void setAliquotExpirationDate(LocalDate aliquotExpirationDate) {
    this.aliquotExpirationDate = aliquotExpirationDate;
  }

  public long getAliquotQuantity() {
    return aliquotQuantity;
  }

  public void setAliquotQuantity(long aliquotQuantity) {
    this.aliquotQuantity = aliquotQuantity;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }
}
