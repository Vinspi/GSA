package fr.uniamu.ibdm.gsa_server.requests.JsonData;

public class TransactionReportData {
  private float aliquotPrice;
  private String productName;
  private String transactionDate;
  private int transactionQuantity;
  private String userName;

  public float getAliquotPrice() {
    return aliquotPrice;
  }

  public void setAliquotPrice(float aliquotPrice) {
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
