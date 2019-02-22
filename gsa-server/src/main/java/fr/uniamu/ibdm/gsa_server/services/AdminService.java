package fr.uniamu.ibdm.gsa_server.services;

import fr.uniamu.ibdm.gsa_server.dao.QueryObjects.StatsWithdrawQuery;
import fr.uniamu.ibdm.gsa_server.models.Aliquot;
import fr.uniamu.ibdm.gsa_server.requests.forms.WithdrawStatsForm;

import java.time.LocalDate;
import java.util.List;

public interface AdminService {

  /**
   * This method retrieve stats for building admin chart.
   *
   * @param form container for building options of the chart.
   * @return a list of months, years, and withdrawals
   */
  List<StatsWithdrawQuery> getWithdrawStats(WithdrawStatsForm form);
  
  /**
   * This method retrieves the name of all species found in the database.
   *   
   * @return a list of names or null if an error occurred.
   */
  List<String> getAllSpeciesNames();
  

  /**
   * This method adds a new product named after the source and target species name.
   * 
   * @param sourceName string
   * @param targetName string
   * @return true if adding the product is successful, false otherwise.
   */
  boolean addProduct(String sourceName, String targetName);

  boolean addAliquote(int aliquotQuantityVisibleStock
          , int aliquotQuantityHiddenStock, float aliquotPrice, String provider, String source, String target);
}
