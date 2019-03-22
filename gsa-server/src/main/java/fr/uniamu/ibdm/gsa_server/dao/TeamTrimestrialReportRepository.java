package fr.uniamu.ibdm.gsa_server.dao;

import fr.uniamu.ibdm.gsa_server.models.TeamTrimestrialReport;
import fr.uniamu.ibdm.gsa_server.models.enumerations.Quarter;
import fr.uniamu.ibdm.gsa_server.models.primarykeys.TeamTrimestrialReportPk;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface TeamTrimestrialReportRepository
    extends CrudRepository<TeamTrimestrialReport, TeamTrimestrialReportPk> {

  List<TeamTrimestrialReport> findAllByYearAndQuarter(int year, Quarter quarter);
  
  @Query(value = "SELECT losses\n"
      + "FROM team_trimestrial_report\n"
      + "WHERE team_id = :id AND quarter LIKE :quarter AND year = :year", nativeQuery = true)  
  BigDecimal findQuarterLossesByTeam(@Param("id")long id, @Param("quarter") String quarter, @Param("year") int year);

  @Query(value = "SELECT quarter, year\n"
      + "FROM team_trimestrial_report\n"
      + "WHERE final_flag = 0\n"
      + "GROUP BY quarter, year\n"
      + "ORDER BY quarter, year", nativeQuery = true)
  List<Object[]> findQuarterAndYearOfEditableReports();
  
  @Query(value = "SELECT quarter, year\n"
      + "FROM team_trimestrial_report\n"
      + "WHERE final_flag = 1\n"
      + "GROUP BY quarter, year\n"
      + "ORDER BY quarter, year", nativeQuery = true)
  List<Object[]> findQuarterAndYearOfNonEditableReports();
  
  @Query(value = "SELECT SUM(losses)\n"
      + "FROM team_trimestrial_report\n"
      + "WHERE year = :year AND quarter LIKE :quarter\n"
      + "GROUP BY quarter, year", nativeQuery = true)
  BigDecimal getSumOfQuarterLosses(@Param("quarter") String quarter, @Param("year") int year);

}
