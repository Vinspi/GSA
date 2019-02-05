package fr.uniamu.ibdm.gsa_server.models;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long teamId;

    private String teamName;

    @OneToMany(mappedBy = "userTeam", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Collection<User> teamMember;

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

    public Collection<User> getTeamMember() {
        return teamMember;
    }

    public void setTeamMember(Collection<User> teamMember) {
        this.teamMember = teamMember;
    }

    public Collection<TeamTrimestrialReport> getReports() {
        return reports;
    }

    public void setReports(Collection<TeamTrimestrialReport> reports) {
        this.reports = reports;
    }
}
