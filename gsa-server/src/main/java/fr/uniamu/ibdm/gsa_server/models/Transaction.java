package fr.uniamu.ibdm.gsa_server.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class Transaction implements Serializable {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long transactionId;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private TransactionMotif transactionMotif;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private TransactionType transactionType;

  @Column(nullable = false)
  private LocalDate transactionDate;

  @Column(nullable = false)
  private int transactionQuantity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "report_id")
  private TeamTrimestrialReport report;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "team_id", nullable = false)
  private Team team;

  public Transaction() {
  }

  public long getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(long transactionId) {
    this.transactionId = transactionId;
  }

  public TransactionMotif getTransactionMotif() {
    return transactionMotif;
  }

  public void setTransactionMotif(TransactionMotif transactionMotif) {
    this.transactionMotif = transactionMotif;
  }

  public TransactionType getTransactionType() {
    return transactionType;
  }

  public void setTransactionType(TransactionType transactionType) {
    this.transactionType = transactionType;
  }

  public LocalDate getTransactionDate() {
    return transactionDate;
  }

  public void setTransactionDate(LocalDate transactionDate) {
    this.transactionDate = transactionDate;
  }

  public int getTransactionQuantity() {
    return transactionQuantity;
  }

  public void setTransactionQuantity(int transactionQuantity) {
    this.transactionQuantity = transactionQuantity;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public TeamTrimestrialReport getReport() {
    return report;
  }

  public void setReport(TeamTrimestrialReport report) {
    this.report = report;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public Team getTeam() {
    return team;
  }

  public void setTeam(Team team) {
    this.team = team;
  }
}
