package fr.uniamu.ibdm.gsa_server.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import fr.uniamu.ibdm.gsa_server.models.enumerations.Quarter;
import fr.uniamu.ibdm.gsa_server.models.primarykeys.TeamTrimestrialReportPk;

@Entity
@IdClass(TeamTrimestrialReportPk.class)
public class TeamTrimestrialReport implements Serializable {

  private float losses;

  /* if final flag is up, the report can't be modified */
  private boolean finalFlag;

  @Id
  private int year;

  @Id
  @Enumerated(EnumType.STRING)
  @Column(length = 9)
  private Quarter quarter;

  @Id
  @ManyToOne()
  @JoinColumn(name = "team_id", nullable = false)
  private Team team;

  public TeamTrimestrialReport() {
  }


  public float getLosses() {
    return losses;
  }

  public void setLosses(float losses) {
    this.losses = losses;
  }

  public boolean isFinalFlag() {
    return finalFlag;
  }

  public void setFinalFlag(boolean finalFlag) {
    this.finalFlag = finalFlag;
  }

  public Quarter getQuarter() {
    return quarter;
  }

  public void setQuarter(Quarter quarter) {
    this.quarter = quarter;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }


  public Team getTeam() {
    return team;
  }

  public void setTeam(Team team) {
    this.team = team;
  }

}
