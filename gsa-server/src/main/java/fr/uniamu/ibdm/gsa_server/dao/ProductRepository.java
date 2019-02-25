package fr.uniamu.ibdm.gsa_server.dao;

import fr.uniamu.ibdm.gsa_server.models.Product;
import fr.uniamu.ibdm.gsa_server.models.primarykeys.ProductPK;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, ProductPK> {

  @Query(value = "SELECT MONTH(transaction_date) as month, YEAR(transaction_date) as year, SUM(transaction_quantity)\n"
      + "FROM transaction\n"
      + "JOIN member ON (member_id = member.id)\n"
      + "JOIN team USING (team_id)\n"
      + "JOIN aliquot ON (aliquot_id = aliquot.aliquotnlot)\n"
      + "JOIN product ON (aliquot.source = product.source_pk AND aliquot.target = product.target_pk)\n"
      + "WHERE (team.team_name LIKE :teamName\n"
      + "AND transaction.transaction_date >= :monthLowerBound AND transaction_date <= :monthUpperBound\n"
      + "AND source_pk LIKE :source\n"
      + "AND target_pk LIKE :target)\n"
      + "GROUP BY month, year\n"
      + "ORDER BY year, month", nativeQuery = true)
  List<Object[]> getWithdrawStats(@Param("teamName") String teamName,
                                            @Param("monthLowerBound") String monthLowerBound,
                                            @Param("monthUpperBound") String monthUpperBound,
                                            @Param("source") String source,
                                            @Param("target") String target);
  

  @Query(value = "SELECT source_pk, target_pk, SUM(aliquot.aliquot_quantity_visible_stock) as qte, seuil, alert_type\n"
      + "FROM product\n"
      + "JOIN alert ON (source_pk LIKE alert.source AND target_pk LIKE alert.target)\n"
      + "JOIN aliquot ON (aliquot.source LIKE source_pk AND aliquot.target LIKE target_pk)\n"
      + "GROUP BY source_pk, target_pk, alert_type\n"
      + "HAVING (qte < seuil)", nativeQuery = true)
  List<Object[]> getTriggeredAlerts();

  @Query(value = "SELECT source_pk, target_pk, SUM(aliquot.aliquot_quantity_visible_stock) as qte, seuil, alert_type, alert_id\n"
      + "FROM product\n"
      + "JOIN alert ON (source_pk LIKE alert.source AND target_pk LIKE alert.target)\n"
      + "JOIN aliquot ON (aliquot.source LIKE source_pk AND aliquot.target LIKE target_pk)\n"
      + "GROUP BY source_pk, target_pk, alert_type\n"
      + "HAVING (qte < seuil AND alert_type LIKE 'VISIBLE_STOCK')", nativeQuery = true)
  List<Object[]> getTriggeredAlertsVisible();

  @Query(value = "SELECT source_pk, target_pk, SUM(aliquot.aliquot_quantity_hidden_stock) as qte, seuil, alert_type, alert_id\n"
      + "FROM product\n"
      + "JOIN alert ON (source_pk LIKE alert.source AND target_pk LIKE alert.target)\n"
      + "JOIN aliquot ON (aliquot.source LIKE source_pk AND aliquot.target LIKE target_pk)\n"
      + "GROUP BY source_pk, target_pk, alert_type\n"
      + "HAVING (qte < seuil AND alert_type LIKE 'HIDDEN_STOCK')", nativeQuery = true)
  List<Object[]> getTriggeredAlertsHidden();

  @Query(value = "SELECT source_pk, target_pk, (SUM(aliquot.aliquot_quantity_hidden_stock)+SUM(aliquot.aliquot_quantity_visible_stock)) as qte, seuil, alert_type, alert_id\n"
      + "FROM product\n"
      + "JOIN alert ON (source_pk LIKE alert.source AND target_pk LIKE alert.target)\n"
      + "JOIN aliquot ON (aliquot.source LIKE source_pk AND aliquot.target LIKE target_pk)\n"
      + "GROUP BY source_pk, target_pk, alert_type\n"
      + "HAVING (qte < seuil AND alert_type LIKE 'GENERAL')", nativeQuery = true)
  List<Object[]> getTriggeredAlertsGeneral();

}
