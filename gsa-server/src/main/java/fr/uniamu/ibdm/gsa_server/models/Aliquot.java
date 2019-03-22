package fr.uniamu.ibdm.gsa_server.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

@Entity
public class Aliquot implements Serializable {

  @Id
  private long aliquotNLot;

  private LocalDate aliquotExpirationDate;
  private long aliquotQuantityVisibleStock;
  private long aliquotQuantityHiddenStock;
  
  @Column(precision = 6, scale = 2)
  private BigDecimal aliquotPrice;
  private String provider;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumns({
          @JoinColumn(name = "source", nullable = false),
          @JoinColumn(name = "target", nullable = false)
  })
  @JsonBackReference
  private Product product;

  public Aliquot() {
  }

  public Aliquot(long aliquotQuantityVisibleStock, long aliquotQuantityHiddenStock) {
    this.aliquotQuantityVisibleStock = aliquotQuantityVisibleStock;
    this.aliquotQuantityHiddenStock = aliquotQuantityHiddenStock;
  }

  /**
   * The method withdraw a quantity of the visible stock, if the quantity is higher than the actual,
   * set the quantity of the aliquot to 0.
   *
   * @param q quantity to withdraw
   */
  public int withdrawFromVisibleStock(int q) {

    if (q > this.getAliquotQuantityVisibleStock()) {
      int returnValue = (int) this.getAliquotQuantityVisibleStock();
      this.setAliquotQuantityVisibleStock(0);
      return returnValue;
    } else {
      this.setAliquotQuantityVisibleStock(this.getAliquotQuantityVisibleStock() - q);
      return q;
    }

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

  public long getAliquotQuantityVisibleStock() {
    return aliquotQuantityVisibleStock;
  }

  public void setAliquotQuantityVisibleStock(long aliquotQuantityVisibleStock) {
    this.aliquotQuantityVisibleStock = aliquotQuantityVisibleStock;
  }

  public long getAliquotQuantityHiddenStock() {
    return aliquotQuantityHiddenStock;
  }

  public void setAliquotQuantityHiddenStock(long aliquotQuantityHiddenStock) {
    this.aliquotQuantityHiddenStock = aliquotQuantityHiddenStock;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public BigDecimal getAliquotPrice() {
    return aliquotPrice;
  }

  public void setAliquotPrice(BigDecimal aliquotPrice) {
    this.aliquotPrice = aliquotPrice;
  }

  public String getProvider() {
    return provider;
  }

  public void setProvider(String provider) {
    this.provider = provider;
  }

}
