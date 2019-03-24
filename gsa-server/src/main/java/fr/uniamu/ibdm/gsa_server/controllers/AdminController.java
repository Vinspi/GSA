package fr.uniamu.ibdm.gsa_server.controllers;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.uniamu.ibdm.gsa_server.conf.CustomConfig;
import fr.uniamu.ibdm.gsa_server.conf.MaintenanceBean;
import fr.uniamu.ibdm.gsa_server.dao.QueryObjects.StatsWithdrawQuery;
import fr.uniamu.ibdm.gsa_server.dao.QueryObjects.TriggeredAlertsQuery;
import fr.uniamu.ibdm.gsa_server.models.Aliquot;
import fr.uniamu.ibdm.gsa_server.models.Product;
import fr.uniamu.ibdm.gsa_server.models.User;
import fr.uniamu.ibdm.gsa_server.models.enumerations.Quarter;
import fr.uniamu.ibdm.gsa_server.requests.JsonResponse;
import fr.uniamu.ibdm.gsa_server.requests.RequestStatus;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.AlertsData;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.NextReportData;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.ProductsStatsData;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.ProvidersStatsData;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.TransactionData;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.TransactionLossesData;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.WithdrawnTransactionData;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.YearQuarterData;
import fr.uniamu.ibdm.gsa_server.requests.forms.AddAlertForm;
import fr.uniamu.ibdm.gsa_server.requests.forms.AddAliquoteForm;
import fr.uniamu.ibdm.gsa_server.requests.forms.AddProductForm;
import fr.uniamu.ibdm.gsa_server.requests.forms.AddTeamTrimestrialReportForm;
import fr.uniamu.ibdm.gsa_server.requests.forms.InventoryForm;
import fr.uniamu.ibdm.gsa_server.requests.forms.PeriodForm;
import fr.uniamu.ibdm.gsa_server.requests.forms.RemoveAlertForm;
import fr.uniamu.ibdm.gsa_server.requests.forms.SetupMaintenanceForm;
import fr.uniamu.ibdm.gsa_server.requests.forms.TeamReportLossForm;
import fr.uniamu.ibdm.gsa_server.requests.forms.TransfertAliquotForm;
import fr.uniamu.ibdm.gsa_server.requests.forms.UpdateAlertForm;
import fr.uniamu.ibdm.gsa_server.requests.forms.WithdrawStatsForm;
import fr.uniamu.ibdm.gsa_server.requests.forms.QuarterForm;
import fr.uniamu.ibdm.gsa_server.services.AdminService;
import fr.uniamu.ibdm.gsa_server.services.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {

  @Autowired
  HttpSession session;

  @Autowired
  AdminService adminService;

  @Autowired
  UserService userService;

  @Autowired
  MaintenanceBean maintenanceBean;

  @Autowired
  CustomConfig config;

  /**
   * REST endpoint for /stats call, return stats needed for building admin chart.
   *
   * @param form The information needed to compute data.
   * @return a JSON formatted response.
   */
  @PostMapping("/stats")
  public JsonResponse<List<StatsWithdrawQuery>> getWithdrawStats(
      @RequestBody WithdrawStatsForm form) {




    return new JsonResponse<>(RequestStatus.SUCCESS, adminService.getWithdrawStats(form));

  }

  /**
   * /Endpoint returning all of species names.
   *
   * @return JSON response containing all of species name.
   */
  @GetMapping("/allspeciesnames")
  public JsonResponse<List<String>> getAllSpeciesNames() {



    List<String> names = adminService.getAllSpeciesNames();
    if (names != null) {
      return new JsonResponse<>(RequestStatus.SUCCESS, names);
    } else {
      return new JsonResponse<>("Could not retrieve all of species names", RequestStatus.FAIL);
    }


  }

  /**
   * Endpoint enabling well-formatted POST requests to add a product.
   *
   * @param form contains "targetName" and "sourceName" keys.
   * @return if successful, a JSON response with a success status, otherwise a JSON response with a
   *         fail status and the sent form as data.
   */
  @PostMapping("/addproduct")
  public JsonResponse<AddProductForm> addProduct(@RequestBody AddProductForm form) {



    JsonResponse<AddProductForm> failedRequestResponse = new JsonResponse<>(RequestStatus.FAIL);
    failedRequestResponse.setData(form);

    String sourceName = form.getSourceName();
    String targetName = form.getTargetName();

    if (sourceName == null || targetName == null) {
      failedRequestResponse.setError("Missing attributes within request body");
      return failedRequestResponse;
    }

    boolean success = adminService.addProduct(sourceName, targetName);
    if (success) {
      return new JsonResponse<>(RequestStatus.SUCCESS);
    } else {
      failedRequestResponse.setError("Could not add the product");
      return failedRequestResponse;
    }

  }

  /**
   * Endpoint for /history. Return a list of withdrawals depending of the period given in argument.
   *
   * @param form the form containing the date of begin and the date of end of the period.
   * @return if successful, a JSON response with a success status, otherwise a
   *     JSON response with a fail status and an error message.
   */
  @PostMapping("/history")
  public JsonResponse<List<TransactionData>> getWithdrawalsHistory(@RequestBody PeriodForm form) {
    List<TransactionData> withdrawalsHistory;

    if (form.validate()) {
      if (form.getBegin() != null && form.getEnd() != null) {
        withdrawalsHistory = adminService.getWithdrawalsHistoryBetween(form.getBegin(), form.getEnd());
      } else if (form.getBegin() != null && form.getEnd() == null) {
        withdrawalsHistory = adminService.getWithdrawalsHistorySince(form.getBegin());
      } else if (form.getBegin() == null && form.getEnd() != null) {
        withdrawalsHistory = adminService.getWithdrawalsHistoryUpTo(form.getEnd());
      } else {
        withdrawalsHistory = adminService.getWithdrawalsHistory();
      }

      return new JsonResponse<>(RequestStatus.SUCCESS, withdrawalsHistory);
    }

    return new JsonResponse<>("Could not find all withdrawals", RequestStatus.FAIL);
  }

  /**
   * Endpoint enabling well-formatted POST requests to add an aliquot.
   *
   * @param form contains n°aliquote & quantity in visible stock & quantity in hidden stock price &
   *          provider & product of aliquote.
   * @return if successful, a JSON response with a success status, otherwise a JSON response with a
   *         fail status and the sent form as data.
   */
  @PostMapping("/addAliquote")
  public JsonResponse<AddAliquoteForm> addAliquote(@RequestBody AddAliquoteForm form) {



    JsonResponse<AddAliquoteForm> failedRequestResponse = new JsonResponse<>(RequestStatus.FAIL);
    failedRequestResponse.setData(form);

    /* form validation */

    if (form.validate()) {
      boolean success = adminService.addAliquot(form);
      if (success) {
        return new JsonResponse<>(RequestStatus.SUCCESS);
      } else {
        failedRequestResponse.setError("Could not add the aliquote");
        return failedRequestResponse;
      }
    } else {
      failedRequestResponse.setError("form is not valid");
      return failedRequestResponse;
    }


  }

  /**
   * /Endpoint returning all of species names.
   *
   * @return JSON response containing all of species name.
   */
  @GetMapping("/allProducts")
  public JsonResponse<List<String>> getAllProductsName() {


    List<String> productsName = userService.getAllProductName();
    if (productsName != null) {
      return new JsonResponse<>(RequestStatus.SUCCESS, productsName);
    } else {
      return new JsonResponse<>("Could not retrieve all of products names", RequestStatus.FAIL);
    }


  }

  /**
   * REST endpoint, return all triggered alerts.
   *
   * @return a list of triggered alerts with their corresponding aliquots.
   */
  @GetMapping("/triggeredAlerts")
  public JsonResponse<List<TriggeredAlertsQuery>> getTriggeredAlerts() {


    return new JsonResponse<>(RequestStatus.SUCCESS, adminService.getTriggeredAlerts());


  }

  /**
   * REST endpoint, return all alerts present in the database.
   *
   * @return a list of wrappers containing product name, seuil and type of the alert.
   */
  @GetMapping("/getAllAlerts")
  public JsonResponse<List<AlertsData>> getAllAlerts() {


    return new JsonResponse<>(RequestStatus.SUCCESS, adminService.getAllAlerts());


  }

  /**
   * REST endpoint, remove the specified alert from the database.
   *
   * @param form wrapper containing targeted alert id.
   * @return SUCCESS status if the product exist, FAIL status otherwise.
   */
  @PostMapping("/removeAlert")
  public JsonResponse<Boolean> removeAlert(@RequestBody RemoveAlertForm form) {


    if (adminService.removeAlert(form.getAlertId())) {
      return new JsonResponse<>(RequestStatus.SUCCESS, true);
    } else {
      return new JsonResponse<>("This alert doesn't exists or has already been removed",
          RequestStatus.FAIL);
    }

  }

  /**
   * REST endpoint, update the specified alert from the database.
   *
   * @param form wrapper containing targeted alert id end new seuil.
   * @return SUCCESS status if the product exist, FAIL status otherwise.
   */
  @PostMapping("/updateAlert")
  public JsonResponse<Boolean> updateAlert(@RequestBody UpdateAlertForm form) {


    if (form.getSeuil() < 1) {
      return new JsonResponse<>("Seuil must be > 0", RequestStatus.FAIL);
    }

    if (adminService.updateAlertSeuil(form)) {
      return new JsonResponse<>(RequestStatus.SUCCESS, true);
    } else {
      return new JsonResponse<>("The specified alert doesn't exists", RequestStatus.FAIL);
    }


  }

  /**
   * REST endpoint, transfer entity of an aliquot from a storage type to another one.
   *
   * @param form Wrapper containing nlot, quantity, from and destination sent by the client.
   * @return SUCCESS status if the operation can be done, FAIL status otherwise.
   */
  @PostMapping("/transfertAliquot")
  public JsonResponse<TransfertAliquotForm> transfertAliquot(
      @RequestBody TransfertAliquotForm form) {


    JsonResponse<TransfertAliquotForm> response;
    boolean success;

    if (form.validate()) {
      success = adminService.transfertAliquot(form);
      if (success) {
        response = new JsonResponse<>(RequestStatus.SUCCESS);
      } else {
        response = new JsonResponse<>(RequestStatus.FAIL);
        response.setData(form);
      }
    } else {
      response = new JsonResponse<>(RequestStatus.FAIL);
      response.setData(form);
    }
    return response;


  }

  /**
   * REST endpoint, add a new alert in the database.
   *
   * @param form Wrapper containing product name, quantity and storage type.
   * @return SUCCESS status if the operation can be done, FAIL status otherwise.
   */
  @PostMapping("/addAlert")
  public JsonResponse<AddAlertForm> addAlert(@RequestBody AddAlertForm form) {


    JsonResponse<AddAlertForm> failRequest = new JsonResponse<>(RequestStatus.FAIL);
    failRequest.setData(form);
    boolean success;

    if (form.validate()) {
      System.out.println("form is valid");
      success = adminService.addAlert(form);
      if (success) {
        return new JsonResponse<>(RequestStatus.SUCCESS);
      }
    }

    return failRequest;


  }

  /**
   * Endpoint enabling well-formatted POST requests to add a team trimestrial report.
   * 
   * @param form contains "teamReportLosses", "finalFlag", "year", "quarter" keys.
   * 
   * @return if successful, a JSON response with a success status, otherwise a JSON response with a
   *         fail status and the sent form as data.
   */
  @PostMapping("/saveReport")
  public JsonResponse<AddTeamTrimestrialReportForm> editTeamTrimestrialReport(
      @RequestBody AddTeamTrimestrialReportForm form) {
    

    JsonResponse<AddTeamTrimestrialReportForm> failedRequestResponse = new JsonResponse<>(
        RequestStatus.FAIL);
    failedRequestResponse.setData(form);

    if (!form.validate()) {
      failedRequestResponse.setError("bad form");
      return failedRequestResponse;
    }

    Map<String, BigDecimal> teamReportLosses = new HashMap<>();
    for (TeamReportLossForm teamLoss : form.getTeamReportLosses()) {
      if (teamReportLosses.containsKey(teamLoss.getTeamName())) {
        failedRequestResponse.setError("Duplicate team name " + teamLoss.getTeamName() + ".");
        return failedRequestResponse;
      }

      teamReportLosses.put(teamLoss.getTeamName(), teamLoss.getLoss());
    }

    if (adminService.saveTeamTrimestrialReport(teamReportLosses, form.getFinalFlag(),
        form.getYear(), Quarter.valueOf(form.getQuarter()))) {
      return new JsonResponse<>(RequestStatus.SUCCESS);
    } else {
      failedRequestResponse.setError("Report could not be saved");
      return failedRequestResponse;
    }

  }

  /**
   * REST endpoint returning all relevant information about withdrawn transactions made by a team in
   * a specific year's quarter.
   *
   * @return JSON response containing all relevant information.
   */
  @GetMapping("/withdrawnTransactions")
  public JsonResponse<List<WithdrawnTransactionData>> getWithdrawnTransactionsByTeamAndQuarterAndYear(
      @RequestParam String teamName, @RequestParam String quarter, @RequestParam Integer year) {
    


    QuarterForm yearQuarterForm = new QuarterForm(quarter);
    if (!yearQuarterForm.validate()) {
      return new JsonResponse<>("Bad parameters values", RequestStatus.FAIL);
    }
    
    List<WithdrawnTransactionData> reportTransactions = adminService
        .getWithdrawnTransactionsByTeamNameAndQuarterAndYear(teamName, Quarter.valueOf(quarter), year);


    if (reportTransactions != null) {
      return new JsonResponse<>(RequestStatus.SUCCESS, reportTransactions);
    } else {
      return new JsonResponse<>("Could not retrieve a list of transactions", RequestStatus.FAIL);
    }

  }

  /**
   * REST endpoint returning the total price of outdated and lost aliquot transactions in a specific
   * year's quarter.
   *
   * @return JSON response containing a value.
   */
  @GetMapping("/transactionLosses")
  public JsonResponse<TransactionLossesData> getTransactionLossesByQuarterAndYear(
      @RequestParam String quarter, @RequestParam Integer year) {


    
    QuarterForm yearQuarterForm = new QuarterForm(quarter);
    if (!yearQuarterForm.validate()) {
      return new JsonResponse<>("Bad parameters values", RequestStatus.FAIL);
    }

    TransactionLossesData data = adminService.getSumAndProductsOfOutdatedAndLostProductOfQuarter(Quarter.valueOf(quarter), year);

    if (data == null) {
      return new JsonResponse<>("Could not retrieve any transaction losses", RequestStatus.FAIL);
    }
    return new JsonResponse<>(RequestStatus.SUCCESS, data);

  }

  /**
   * REST endpoint returning the quarter and year of all editable reports.
   *
   * @return JSON response containing a list of quarter and year.
   */
  @GetMapping("/quarterYearOfAllEditableReports")
  public JsonResponse<List<YearQuarterData>> getQuarterAndYearOfEditableReports() {


    return new JsonResponse<>(RequestStatus.SUCCESS,
        adminService.getQuarterAndYearOfAllEditableReports());



  }

  /**
   * REST endpoint returning all team names and their associated loss of a trimestrial report.
   *
   * @return JSON response containing a list of team names and losses.
   */
  @GetMapping("/teamReportLosses")
  public JsonResponse<List<TeamReportLossForm>> getQuarterAndYearOfEditableReports(
      @RequestParam String quarter, @RequestParam Integer year) {


    QuarterForm yearQuarterForm = new QuarterForm(quarter);
    if (!yearQuarterForm.validate()) {
      return new JsonResponse<>("Bad parameters values", RequestStatus.FAIL);
    }

    List<TeamReportLossForm> data = adminService
        .getReportLossesAndTeamNameByYearAndQuarter(Quarter.valueOf(quarter), year);
    if (data != null) {
      return new JsonResponse<>(RequestStatus.SUCCESS, data);
    } else {
      return new JsonResponse<>("Could not retrieve any report losses", RequestStatus.FAIL);
    }


  }

  /**
   * REST endpoint returning the remaining cost of report losses in a given quarter.
   *
   * @return JSON response containing a BigDecimal value.
   */
  @GetMapping("/remainingReportLosses")
  public JsonResponse<BigDecimal> getRemainingReportLosses(@RequestParam String quarter,
      @RequestParam Integer year) {


    
    QuarterForm yearQuarterForm = new QuarterForm(quarter);
    if (!yearQuarterForm.validate()) {
      return new JsonResponse<>("Bad parameters values", RequestStatus.FAIL);
    }

    BigDecimal sum = adminService.getRemainingReportLosses(Quarter.valueOf(quarter), year);
    if (sum != null) {
      return new JsonResponse<>(RequestStatus.SUCCESS, sum);
    } else {
      return new JsonResponse<>("Could not retrieve any losses", RequestStatus.FAIL);
    }


  }
  
  /**
   * REST endpoint returning the total cost of all withdrawn transactions of a given quarter.
   *
   * @return JSON response containing a BigDecimal value.
   */
  @GetMapping("/withdrawalTotalCost")
  public JsonResponse<BigDecimal> getWithdrawalTotalCost(@RequestParam String quarter,
      @RequestParam Integer year) {

    QuarterForm yearQuarterForm = new QuarterForm(quarter);
    if (!yearQuarterForm.validate()) {
      return new JsonResponse<>("Bad parameters values", RequestStatus.FAIL);
    }

    BigDecimal sum = adminService.getSumOfCostOfAllWithdrawnProductsByQuarter(Quarter.valueOf(quarter), year);
    if (sum != null) {
      return new JsonResponse<>(RequestStatus.SUCCESS, sum);
    } else {
      return new JsonResponse<>("Could not retrieve any transactions", RequestStatus.FAIL);
    }


  }
  
  /**
   * REST endpoint, retrieve all products and their aliquots.
   *
   * @return a JsonResponse containing a list of Products.
   */
  @GetMapping("/getAllProductsWithAliquots")
  public JsonResponse<List<Product>> getAllProductsWithAliquots() {


    return new JsonResponse<>(RequestStatus.SUCCESS, adminService.getAllProductsWithAliquots());


  }

  /**
   * REST endpoint, perform the inventory.
   *
   * @param forms list of form that contains nlot and quantity.
   * @return a JsonResponse SUCCESS if the form is valid, a JsonResponse FAIl otherwise
   */
  @PostMapping("/handleInventory")
  public JsonResponse<List<InventoryForm>> handleInventory(@RequestBody List<InventoryForm> forms) {


    for (InventoryForm form : forms) {
      if (!form.validate()) {
        return new JsonResponse<>(RequestStatus.FAIL, forms);
      }
    }

    adminService.makeInventory(forms);

    return new JsonResponse<>(RequestStatus.SUCCESS);


  }

  /**
   * REST endpoint, retrieve stats on provider for the admin homepage.
   *
   * @return a JsonResponse SUCCESS with a list of provider stat.
   */
  @GetMapping("/getProvidersStats")
  public JsonResponse<List<ProvidersStatsData>> getProvidersStats() {



    return new JsonResponse<>(RequestStatus.SUCCESS, adminService.generateProvidersStats());


  }

  /**
   * REST endpoint, retrieve the number of triggered alerts.
   *
   * @return a JsonResponse SUCCESS containing the number of triggered alerts.
   */
  @GetMapping("/getAlertsNotification")
  public JsonResponse<Integer> getAlertsNotification() {



    return new JsonResponse<>(RequestStatus.SUCCESS, adminService.getAlertsNotification());


  }

  /**
   * REST endpoint, retrieve the number of days until the next report.
   *
   * @return a JsonResponse SUCCESS containing the number of days until the next report.
   */
  @GetMapping("/getNextReport")
  public JsonResponse<NextReportData> getNextReport() {

    return new JsonResponse<>(RequestStatus.SUCCESS, adminService.getNextReportData());


  }

  /**
   * REST endpoint, retrieve the average price for all product.
   *
   * @return a JsonResponse containing a list of stats.
   */
  @GetMapping("/getProductsStats")
  public JsonResponse<List<ProductsStatsData>> getProductsStats() {


    return new JsonResponse<>(RequestStatus.SUCCESS, adminService.generateProductsStats());


  }

  /**
   * REST endpoint, allow a superAdministrator to set up maintenance mode.
   *
   * @param form wrapper containing mode and super admin password to activate maintenance mode.
   * @return a JsonResponse containing the set up mode.
   */
  @PostMapping("/setupMaintenanceMode")
  public JsonResponse<SetupMaintenanceForm> setUpMaintenanceMode(
      @RequestBody SetupMaintenanceForm form) {

    if (form.validate() && form.getPassword().equals(config.getSuperAdminPassword())) {

      maintenanceBean.setMaintenanceMode(form.isMode());

      return new JsonResponse<>(RequestStatus.SUCCESS, form);
    }

    return new JsonResponse<>(RequestStatus.FAIL, form);

  }

  /**
   * REST endpoint, retrieve all outdated aliquot left in the stocks.
   *
   * @return a JsonResponse containing a list of all outdated aliquot.
   */
  @GetMapping("/getAllOutdatedAliquot")
  public JsonResponse<List<Product>> getAllOutdatedAliquot() {

    return new JsonResponse<>(RequestStatus.SUCCESS, adminService.getAllOutdatedAliquot());

  }

  /**
   * REST endpoint, delete an outdated aliquot.
   *
   * @return a JsonResponse containing the deleted aliquot.
   */
  @PostMapping("/deleteOutdatedAliquot")
  public JsonResponse<Aliquot> deleteOutdatedAliquot(@RequestBody Aliquot aliquot) {

    boolean success = adminService.deleteOutdatedAliquot(aliquot);

    if (success) {
      return new JsonResponse<>(RequestStatus.SUCCESS, aliquot);
    }

    return new JsonResponse<>("Not allowed", RequestStatus.FAIL);

  }


}
