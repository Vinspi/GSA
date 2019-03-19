package fr.uniamu.ibdm.gsa_server.requests.JsonData;

import java.math.BigDecimal;

public class WithdrawnTransactionData {
  private BigDecimal aliquotPrice;
  private String productName;
  private String transactionDate;
  private int transactionQuantity;
  private String userName;

  public WithdrawnTransactionData() {
  }

  /**
   * Contructor for WithdrawnTransactionData.
   * 
   * @param aliquotPrice BigDecimal
   * @param productName String
   * @param transactionDate String
   * @param transactionQuantity int
   * @param userName String
   */
  public WithdrawnTransactionData(BigDecimal aliquotPrice, String productName,
      String transactionDate, int transactionQuantity, String userName) {
    this.aliquotPrice = aliquotPrice;
    this.productName = productName;
    this.transactionDate = transactionDate;
    this.transactionQuantity = transactionQuantity;
    this.userName = userName;
  }

  public BigDecimal getAliquotPrice() {
    return aliquotPrice;
  }

  public void setAliquotPrice(BigDecimal aliquotPrice) {
    this.aliquotPrice = aliquotPrice;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public String getTransactionDate() {
    return transactionDate;
  }

  public void setTransactionDate(String transactionDate) {
    this.transactionDate = transactionDate;
  }

  public int getTransactionQuantity() {
    return transactionQuantity;
  }

  public void setTransactionQuantity(int transactionQuantity) {
    this.transactionQuantity = transactionQuantity;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }
}
