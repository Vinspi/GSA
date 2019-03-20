package fr.uniamu.ibdm.gsa_server.requests.JsonData;

import fr.uniamu.ibdm.gsa_server.models.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionData {
  private LocalDate date;
  private String userName;
  private String aliquotName;
  private String teamName;
  private int quantity;
  private BigDecimal price;

  public TransactionData() {
  }

  /**
   * Constructor for TransactionData.
   *
   * @param elem a transaction.
   */
  public TransactionData(Transaction elem) {
    this.date = elem.getTransactionDate();
    this.userName = elem.getMember().getUser().getUserName();
    this.aliquotName = elem.getAliquot().getProduct().getProductName();
    this.teamName = elem.getMember().getTeam().getTeamName();
    this.quantity = elem.getTransactionQuantity();
    BigDecimal priceUnit = elem.getAliquot().getAliquotPrice();
    this.price = priceUnit.multiply(BigDecimal.valueOf(quantity));
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getAliquotName() {
    return aliquotName;
  }

  public void setAliquot(String aliquot) {
    this.aliquotName = aliquot;
  }

  public String getTeamName() {
    return teamName;
  }

  public void setTeam(String teamName) {
    this.teamName = teamName;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

}
