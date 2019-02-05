package fr.uniamu.ibdm.gsa_server.models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long transactionId;

    @Enumerated(EnumType.STRING)
    private TransactionMotif transactionMotif;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private LocalDate transactionDate;
    private int transactionQuantity;

    @OneToOne
    private Aliquot aliquot;

    @OneToOne
    private User user;

    @ManyToOne
    private TeamTrimestrialReport report;

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

    public Aliquot getAliquot() {
        return aliquot;
    }

    public void setAliquot(Aliquot aliquot) {
        this.aliquot = aliquot;
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
}
