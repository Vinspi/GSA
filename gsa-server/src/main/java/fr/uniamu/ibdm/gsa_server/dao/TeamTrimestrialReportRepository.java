package fr.uniamu.ibdm.gsa_server.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.uniamu.ibdm.gsa_server.models.TeamTrimestrialReport;
import fr.uniamu.ibdm.gsa_server.models.primarykeys.TeamTrimestrialReportPK;

@Repository
public interface TeamTrimestrialReportRepository extends CrudRepository<TeamTrimestrialReport, TeamTrimestrialReportPK>{

}
