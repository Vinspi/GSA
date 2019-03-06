package fr.uniamu.ibdm.gsa_server.requests.JsonData;

import java.util.List;

public class TransactionLossesData {
  private Float totalLosses;
  private List<ProductLossData> productLosses;

  public Float getTotalLosses() {
    return totalLosses;
  }

  public void setTotalLosses(Float totalLosses) {
    this.totalLosses = totalLosses;
  }

  public List<ProductLossData> getProductLosses() {
    return productLosses;
  }

  public void setProductLosses(List<ProductLossData> productLosses) {
    this.productLosses = productLosses;
  }

  public class ProductLossData {
    private Float productLoss;
    private String productName;

    public ProductLossData() {
    }

    public ProductLossData(Float productLoss, String productName) {
      this.productLoss = productLoss;
      this.productName = productName;
    }

    public Float getProductLoss() {
      return productLoss;
    }

    public void setProductLoss(Float productLoss) {
      this.productLoss = productLoss;
    }

    public String getProductName() {
      return productName;
    }

    public void setProductName(String productName) {
      this.productName = productName;
    }
  }

}
