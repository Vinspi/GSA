package fr.uniamu.ibdm.gsa_server.requests.JsonData;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.uniamu.ibdm.gsa_server.models.Species;

public class ProductsStatsData {

  @JsonIgnore
  private Species source;
  @JsonIgnore
  private Species target;
  private double productPrice;

  /**
   * Constructor for ProductsStatsData.
   *
   * @param source       source of the product.
   * @param target       target of the product.
   * @param productPrice average price for this product.
   */
  public ProductsStatsData(Species source, Species target, double productPrice) {
    this.source = source;
    this.target = target;
    this.productPrice = productPrice;
  }

  public ProductsStatsData() {
  }

  public Species getSource() {
    return source;
  }

  public void setSource(Species source) {
    this.source = source;
  }

  public Species getTarget() {
    return target;
  }

  public void setTarget(Species target) {
    this.target = target;
  }

  public double getProductPrice() {
    return productPrice;
  }

  public void setProductPrice(double productPrice) {
    this.productPrice = productPrice;
  }

  public String getProductName() {
    return this.source.getSpeciesName().toUpperCase() + "_ANTI_" + this.target.getSpeciesName().toUpperCase();
  }
}
