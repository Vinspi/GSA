package fr.uniamu.ibdm.gsa_server.requests.JsonData;

import java.util.List;

public class TeamWithdrawnTransactionsData {
  private Float totalPrice;
  private List<WithdrawnTransactionData> transactions;
  
  public TeamWithdrawnTransactionsData() {
  }

  public TeamWithdrawnTransactionsData(Float totalPrice, List<WithdrawnTransactionData> transactions) {
    this.totalPrice = totalPrice;
    this.transactions = transactions;
  }

  public Float getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(Float totalPrice) {
    this.totalPrice = totalPrice;
  }

  public List<WithdrawnTransactionData> getTransactions() {
    return transactions;
  }

  public void setTransactions(List<WithdrawnTransactionData> transactions) {
    this.transactions = transactions;
  }
  
  public class WithdrawnTransactionData {
    private float aliquotPrice;
    private String productName;
    private String transactionDate;
    private int transactionQuantity;
    private String userName;

    public WithdrawnTransactionData() {
    }

    /**
     * Contructor for ReportTransactionData.
     * 
     * @param aliquotPrice float
     * @param productName String
     * @param transactionDate String
     * @param transactionQuantity int
     * @param userName String
     */
    public WithdrawnTransactionData(float aliquotPrice, String productName, String transactionDate,
        int transactionQuantity, String userName) {
      this.aliquotPrice = aliquotPrice;
      this.productName = productName;
      this.transactionDate = transactionDate;
      this.transactionQuantity = transactionQuantity;
      this.userName = userName;
    }

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
}
