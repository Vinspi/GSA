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
    private Float loss;
    private String name;

    public ProductLossData() {
    }
    
    public ProductLossData(Float loss, String name) {
      this.loss = loss;
      this.name = name;
    }
    
    public Float getLoss() {
      return loss;
    }

    public void setLoss(Float loss) {
      this.loss = loss;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

  }

}
