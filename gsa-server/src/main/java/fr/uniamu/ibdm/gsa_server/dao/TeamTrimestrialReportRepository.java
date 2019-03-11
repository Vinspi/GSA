package fr.uniamu.ibdm.gsa_server.dao;

import fr.uniamu.ibdm.gsa_server.models.TeamTrimestrialReport;
import fr.uniamu.ibdm.gsa_server.models.enumerations.Quarter;
import fr.uniamu.ibdm.gsa_server.models.primarykeys.TeamTrimestrialReportPK;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TeamTrimestrialReportRepository extends CrudRepository<TeamTrimestrialReport, TeamTrimestrialReportPK> {

  List<TeamTrimestrialReport> findAllByYearAndQuarter(int year, Quarter quarter);

}