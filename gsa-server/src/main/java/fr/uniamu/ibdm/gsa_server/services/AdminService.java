package fr.uniamu.ibdm.gsa_server.services;

import java.util.List;
import java.util.Map;

import fr.uniamu.ibdm.gsa_server.dao.QueryObjects.StatsWithdrawQuery;
import fr.uniamu.ibdm.gsa_server.dao.QueryObjects.TriggeredAlertsQuery;
import fr.uniamu.ibdm.gsa_server.models.Product;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.AlertsData;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.NextReportData;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.ProductsStatsData;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.ProvidersStatsData;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.TeamWithdrawnTransactionsData;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.TransactionLossesData;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.YearQuarterData;
import fr.uniamu.ibdm.gsa_server.requests.forms.AddAlertForm;
import fr.uniamu.ibdm.gsa_server.requests.forms.AddAliquoteForm;
import fr.uniamu.ibdm.gsa_server.requests.forms.InventoryForm;
import fr.uniamu.ibdm.gsa_server.requests.forms.TeamReportLossForm;
import fr.uniamu.ibdm.gsa_server.requests.forms.TransfertAliquotForm;
import fr.uniamu.ibdm.gsa_server.requests.forms.UpdateAlertForm;
import fr.uniamu.ibdm.gsa_server.requests.forms.WithdrawStatsForm;

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
   * @param form Wrapper containing informations about the aliquot.
   * @return true if adding the aliquote is successful, false otherwise.
   */
  boolean addAliquot(AddAliquoteForm form);

  /**
   * This method retrieve all products on which an alert has been triggered.
   *
   * @return A list of wrapper containing product names, the quantity left and the threshold of the
   *         alert.
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
   * @return true if succeed, false otherwise.
   */
  boolean updateAlertSeuil(UpdateAlertForm form);

  /**
   * This method remove the given alert from the database.
   *
   * @param id targeted alert id.
   * @return true if succeed, false otherwise.
   */
  boolean removeAlert(long id);

  /**
   * This method transfers aliquots between storage type.
   *
   * @param form Wrapper containing NLot, quantity and storage type.
   * @return true if succeed, false otherwise.
   */
  boolean transfertAliquot(TransfertAliquotForm form);

  /**
   * This method add a new alert in the database.
   *
   * @param form Wrapper containing product name, quantity and storage type.
   * @return false if the alert alreadyExist, false otherwise.
   */
  boolean addAlert(AddAlertForm form);

  /**
   * This method retrieves all transactions made by a team in a quarter of a given year.
   * 
   * @param teamName String
   * @param quarter value of Quarter enumeration
   * @param year int
   * 
   * @return a list of transactions and the total price or null if an error occurred.
   */
  TeamWithdrawnTransactionsData getWithdrawnTransactionsByTeamNameAndQuarterAndYear(String teamName,
      String quarter, int year);

  /**
   * This method saves a list of team trimestrial reports in the database if there are still all
   * editable and if the quarter is over. If a team trimestrial report of the list is not valid,
   * then none of them will be added in the database.
   * 
   * @param teamReportLosses a list of teamNames and their associated loss.
   * @param finalFlag set to true if the report is final, false otherwise
   * @param year integer
   * @param quarterStr value of Quarter enumeration
   * 
   * @return true if the saving process is successful, false otherwise.
   */
  public boolean saveTeamTrimestrialReport(Map<String, Float> teamReportLosses, boolean finalFlag,
      int year, String quarterStr);

  /**
   * This method returns the sum of prices of outdated and lost aliquots and details of each loss.
   *
   * @param quarter value of Quarter enumeration
   * @param year year value
   * 
   * @return total cost losses and its details (product's name and its associated loss).
   */
  TransactionLossesData getTransactionLossesByQuarterAndYear(String quarter, int year);

  /**
   * This method returns the quarter and year of all editable reports.
   *
   * @return a list of quarter and year.
   */
  List<YearQuarterData> getQuarterAndYearOfAllEditableReports();

  /**
   * This method returns the current losses of each team in the database.
   * 
   * @param quarter value of Quarter enumeration
   * @param year year value
   * 
   * @return a list of cost losses and their associated team name or null if the quarter parameter
   *         is invalid.
   */
  List<TeamReportLossForm> getReportLossesAndTeamNameByYearAndQuarter(String quarter, int year);

  /**
   * Gets the sum of all team losses of a given quarter.
   * 
   * @param quarter string value of Quarter enumeration
   * @param year int 
   * @return the sum of losses in a given quarter
   */
  Float getSumOfQuarterLosses(String quarter, Integer year);
  
  /**
   * This method retrieve all products and their aliquots from the database.
   *
   * @return a list of products.
   */
  List<Product> getAllProductsWithAliquots();

  /**
   * This method perform the inventory. It add losses transactions for every aliquot lost and
   * restore the database to the user inputs.
   *
   * @param forms a list of form containing aliquotNLot and quantity.
   */
  void makeInventory(List<InventoryForm> forms);

  /**
   * This method generate a list of providers stats.
   *
   * @return a list of providers stats.
   */
  List<ProvidersStatsData> generateProvidersStats();

  /**
   * This methods return the number of triggered alerts.
   *
   * @return the number of triggered alerts.
   */
  int getAlertsNotification();

  /**
   * This method retrieve the number of days until the next report available.
   *
   * @return wrapper containing the number of days until the next report.
   */
  NextReportData getNextReportData();

  /**
   * This method generate a list of products stats.
   *
   * @return a list of products stats.
   */
  List<ProductsStatsData> generateProductsStats();

}
