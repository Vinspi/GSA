package fr.uniamu.ibdm.gsa_server.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.Collection;

@Entity
public class Team implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long teamId;

  private String teamName;

  @OneToMany(mappedBy = "team", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private Collection<Member> members;

  @OneToMany(mappedBy = "team", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private Collection<TeamTrimestrialReport> reports;

  public Team() {
  }

  public long getTeamId() {
    return teamId;
  }

  public void setTeamId(long teamId) {
    this.teamId = teamId;
  }

  public String getTeamName() {
    return teamName;
  }

  public void setTeamName(String teamName) {
    this.teamName = teamName;
  }

  public Collection<TeamTrimestrialReport> getReports() {
    return reports;
  }

  public void setReports(Collection<TeamTrimestrialReport> reports) {
    this.reports = reports;
  }

  public Collection<Member> getMembers() {
    return members;
  }

  public void setMembers(Collection<Member> members) {
    this.members = members;
  }

}
