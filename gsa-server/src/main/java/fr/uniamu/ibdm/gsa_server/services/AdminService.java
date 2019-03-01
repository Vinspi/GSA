package fr.uniamu.ibdm.gsa_server.services;

import fr.uniamu.ibdm.gsa_server.dao.QueryObjects.StatsWithdrawQuery;
import fr.uniamu.ibdm.gsa_server.dao.QueryObjects.TriggeredAlertsQuery;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.AlertsData;
import fr.uniamu.ibdm.gsa_server.requests.forms.AddAliquoteForm;
import fr.uniamu.ibdm.gsa_server.requests.forms.UpdateAlertForm;
import fr.uniamu.ibdm.gsa_server.requests.forms.WithdrawStatsForm;

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

  /**
   * This method adds a new aliquote.
   *
   * @param  form Wrapper containing informations about the aliquot.
   * @return true if adding the aliquote is successful, false otherwise.
   */
  boolean addAliquote(AddAliquoteForm form);

  /**
   * This method retrieve all products on which an
   * alert has been triggered.
   *
   * @return A list of wrapper containing product names, the quantity left
   *     and the threshold of the alert.
   */
  List<TriggeredAlertsQuery> getTriggeredAlerts();

  /**
   * This method retrieve all alerts in the database.
   *
   * @return A list of alerts.
   */
  List<AlertsData> getAllAlerts();

  /**
   * This method update the alert seuil of the given alert.
   *
   * @param form Wrapper containing new seuil and alert id.
   */
  boolean updateAlertSeuil(UpdateAlertForm form);

  /**
   * This method remove the given alert from the database.
   *
   * @param id targeted alert id.
   */
  boolean removeAlert(long id);

}
