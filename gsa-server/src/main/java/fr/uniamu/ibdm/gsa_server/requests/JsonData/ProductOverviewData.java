package fr.uniamu.ibdm.gsa_server.requests.JsonData;

public class ProductOverviewData {

  private String productName;
  private int quantity;

  public ProductOverviewData(String productName, int quantity) {
    this.productName = productName;
    this.quantity = quantity;
  }

  public ProductOverviewData() {
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
}
