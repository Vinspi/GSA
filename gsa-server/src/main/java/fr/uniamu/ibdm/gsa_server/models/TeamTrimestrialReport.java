package fr.uniamu.ibdm.gsa_server.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;

@Entity
public class TeamTrimestrialReport implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long reportId;

  private int pertes;
  private int year;

  private LocalDate begin;
  private LocalDate end;

  @OneToMany(mappedBy = "report")
  private Collection<Transaction> transactions;

  @ManyToOne()
  @JoinColumn(name = "team_id", nullable = false)
  private Team team;

  public TeamTrimestrialReport() {
  }

  public long getReportId() {
    return reportId;
  }

  public void setReportId(long reportId) {
    this.reportId = reportId;
  }

  public int getPertes() {
    return pertes;
  }

  public void setPertes(int pertes) {
    this.pertes = pertes;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public LocalDate getBegin() {
    return begin;
  }

  public void setBegin(LocalDate begin) {
    this.begin = begin;
  }

  public LocalDate getEnd() {
    return end;
  }

  public void setEnd(LocalDate end) {
    this.end = end;
  }

  public Collection<Transaction> getTransactions() {
    return transactions;
  }

  public void setTransactions(Collection<Transaction> transactions) {
    this.transactions = transactions;
  }

  public Team getTeam() {
    return team;
  }

  public void setTeam(Team team) {
    this.team = team;
  }
}
