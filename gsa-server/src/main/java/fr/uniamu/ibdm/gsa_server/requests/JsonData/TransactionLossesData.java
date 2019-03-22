package fr.uniamu.ibdm.gsa_server.requests.JsonData;

import java.math.BigDecimal;
import java.util.List;

public class TransactionLossesData {
  private BigDecimal totalLosses;
  private List<ProductLossData> productLosses;

  public BigDecimal getTotalLosses() {
    return totalLosses;
  }

  public void setTotalLosses(BigDecimal totalLosses) {
    this.totalLosses = totalLosses;
  }

  public List<ProductLossData> getProductLosses() {
    return productLosses;
  }

  public void setProductLosses(List<ProductLossData> productLosses) {
    this.productLosses = productLosses;
  }

  public class ProductLossData {
    private BigDecimal loss;
    private String name;

    public ProductLossData() {
    }
    
    public ProductLossData(BigDecimal loss, String name) {
      this.loss = loss;
      this.name = name;
    }
    
    public BigDecimal getLoss() {
      return loss;
    }

    public void setLoss(BigDecimal loss) {
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
