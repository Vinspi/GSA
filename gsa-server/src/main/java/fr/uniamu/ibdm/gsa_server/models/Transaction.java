package fr.uniamu.ibdm.gsa_server.models;

import java.io.Serializable;
import java.time.LocalDate;

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

import fr.uniamu.ibdm.gsa_server.models.enumerations.TransactionMotif;
import fr.uniamu.ibdm.gsa_server.models.enumerations.TransactionType;

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
  @JoinColumn(name = "aliquot_id", nullable = false)
  private Aliquot aliquot;

  // Admins add transactions as null members as they are not member of any team.
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  public Transaction() {
  }

  /**
   * Constructor.
   *
   * @param transactionMotif Motif of the transaction.
   * @param transactionType Type of the transaction.
   * @param transactionDate Date of the transaction.
   * @param transactionQuantity Quantity of the transaction.
   * @param aliquot Aliquot for this transaction.
   * @param member Member who performed this transaction.
   */
  public Transaction(TransactionMotif transactionMotif, TransactionType transactionType, LocalDate transactionDate, int transactionQuantity, Aliquot aliquot, Member member) {
    this.transactionMotif = transactionMotif;
    this.transactionType = transactionType;
    this.transactionDate = transactionDate;
    this.transactionQuantity = transactionQuantity;
    this.aliquot = aliquot;
    this.member = member;
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

  public Aliquot getAliquot() {
    return aliquot;
  }

  public void setAliquot(Aliquot aliquot) {
    this.aliquot = aliquot;
  }

  public Member getMember() {
    return member;
  }

  public void setMember(Member member) {
    this.member = member;
  }
}
