package fr.uniamu.ibdm.gsa_server.controllers;
import fr.uniamu.ibdm.gsa_server.conf.CustomConfig;
import fr.uniamu.ibdm.gsa_server.conf.MaintenanceBean;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.uniamu.ibdm.gsa_server.dao.TeamTrimestrialReportRepository;
import fr.uniamu.ibdm.gsa_server.dao.QueryObjects.StatsWithdrawQuery;
import fr.uniamu.ibdm.gsa_server.dao.QueryObjects.TriggeredAlertsQuery;
import fr.uniamu.ibdm.gsa_server.models.Product;
import fr.uniamu.ibdm.gsa_server.models.User;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.AlertsData;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.EditableReportYearQuarterData;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.NextReportData;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.ProductsStatsData;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.ProvidersStatsData;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.TeamPriceLossesData;
import fr.uniamu.ibdm.gsa_server.requests.JsonResponse;
import fr.uniamu.ibdm.gsa_server.requests.RequestStatus;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.TeamWithdrawnTransactionsData;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.TransactionLossesData;
import fr.uniamu.ibdm.gsa_server.requests.forms.AddAlertForm;
import fr.uniamu.ibdm.gsa_server.requests.forms.AddAliquoteForm;
import fr.uniamu.ibdm.gsa_server.requests.forms.AddProductForm;
import fr.uniamu.ibdm.gsa_server.requests.forms.AddTeamTrimestrialReportForm;
import fr.uniamu.ibdm.gsa_server.requests.forms.InventoryForm;
import fr.uniamu.ibdm.gsa_server.requests.forms.RemoveAlertForm;
import fr.uniamu.ibdm.gsa_server.requests.forms.SetupMaintenanceForm;
import fr.uniamu.ibdm.gsa_server.requests.forms.TransfertAliquotForm;
import fr.uniamu.ibdm.gsa_server.requests.forms.UpdateAlertForm;
import fr.uniamu.ibdm.gsa_server.requests.forms.WithdrawStatsForm;
import fr.uniamu.ibdm.gsa_server.services.AdminService;
import fr.uniamu.ibdm.gsa_server.services.UserService;
import org.openqa.selenium.json.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin(allowCredentials = "true", origins = {"http://localhost:4200", "http://localhost", "http://51.77.147.140"})
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

  final double minPrice = 0.0001;

  /**
   * REST endpoint for /stats call, return stats needed for building admin chart.
   *
   * @param form The information needed to compute data.
   * @return a JSON formatted response.
   */
  @PostMapping("/stats")
  public JsonResponse<List<StatsWithdrawQuery>> getWithdrawStats(@RequestBody WithdrawStatsForm form) {

    if (maintenanceBean.isMaintenanceMode()){
      return new JsonResponse<>(RequestStatus.MAINTENANCE);
    }

    if (this.isAdmin()) {
      return new JsonResponse<>(RequestStatus.SUCCESS, adminService.getWithdrawStats(form));
    } else {
      return new JsonResponse<>("Not allowed", RequestStatus.FAIL);
    }
  }


  /**
   * /Endpoint returning all of species names.
   *
   * @return JSON response containing all of species name.
   */
  @GetMapping("/allspeciesnames")
  public JsonResponse<List<String>> getAllSpeciesNames() {

    if (maintenanceBean.isMaintenanceMode()){
      return new JsonResponse<>(RequestStatus.MAINTENANCE);
    }

    if (this.isAdmin()) {
      List<String> names = adminService.getAllSpeciesNames();
      if (names != null) {
        return new JsonResponse<>(RequestStatus.SUCCESS, names);
      } else {
        return new JsonResponse<>("Could not retrieve all of species names", RequestStatus.FAIL);
      }
    } else {
      return new JsonResponse<>("Not allowed", RequestStatus.FAIL);
    }

  }

  /**
   * Endpoint enabling well-formatted POST requests to add a product.
   *
   * @param form contains "targetName" and "sourceName" keys.
   * @return if successful, a JSON response with a success status, otherwise a
   *     JSON response with a fail status and the sent form as data.
   */
  @PostMapping("/addproduct")
  public JsonResponse<AddProductForm> addProduct(@RequestBody AddProductForm form) {

    if (maintenanceBean.isMaintenanceMode()){
      return new JsonResponse<>(RequestStatus.MAINTENANCE);
    }

    if (this.isAdmin()) {
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
    } else {
      return new JsonResponse<>("Not allowed", RequestStatus.FAIL);
    }
  }

  /**
   * Endpoint enabling well-formatted POST requests to add an aliquot.
   *
   * @param form contains n°aliquote & quantity in visible stock & quantity in hidden stock
   *             price & provider & product of aliquote.
   * @return if successful, a JSON response with a success status, otherwise a
   *     JSON response with a fail status and the sent form as data.
   */
  @PostMapping("/addAliquote")
  public JsonResponse<AddAliquoteForm> addAliquote(@RequestBody AddAliquoteForm form) {

    if (maintenanceBean.isMaintenanceMode()){
      return new JsonResponse<>(RequestStatus.MAINTENANCE);
    }

    if (isAdmin()) {
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
        failedRequestResponse.setError("Could not add the aliquote");
        return failedRequestResponse;
      }
    } else {
      return new JsonResponse<>("Not allowed", RequestStatus.FAIL);
    }

  }

  /**
   * /Endpoint returning all of species names.
   *
   * @return JSON response containing all of species name.
   */
  @GetMapping("/allProducts")
  public JsonResponse<List<String>> getAllProductsName() {

    if (maintenanceBean.isMaintenanceMode()){
      return new JsonResponse<>(RequestStatus.MAINTENANCE);
    }

    if (isAdmin()) {
      List<String> productsName = userService.getAllProductName();
      if (productsName != null) {
        return new JsonResponse<>(RequestStatus.SUCCESS, productsName);
      } else {
        return new JsonResponse<>("Could not retrieve all of products names", RequestStatus.FAIL);
      }
    } else {
      return new JsonResponse<>("Not allowed", RequestStatus.FAIL);
    }

  }

  /**
   * REST endpoint, return all triggered alerts.
   *
   * @return a list of triggered alerts with their corresponding aliquots.
   */
  @GetMapping("/triggeredAlerts")
  public JsonResponse<List<TriggeredAlertsQuery>> getTriggeredAlerts() {

    if (maintenanceBean.isMaintenanceMode()){
      return new JsonResponse<>(RequestStatus.MAINTENANCE);
    }

    if (isAdmin()) {
      return new JsonResponse<>(RequestStatus.SUCCESS, adminService.getTriggeredAlerts());
    } else {
      return new JsonResponse<>("Not allowed", RequestStatus.FAIL);
    }

  }


  /**
   * REST endpoint, return all alerts present in the database.
   *
   * @return a list of wrappers containing product name, seuil and type of the alert.
   */
  @GetMapping("/getAllAlerts")
  public JsonResponse<List<AlertsData>> getAllAlerts() {

    if (maintenanceBean.isMaintenanceMode()){
      return new JsonResponse<>(RequestStatus.MAINTENANCE);
    }

    if (isAdmin()) {
      return new JsonResponse<>(RequestStatus.SUCCESS, adminService.getAllAlerts());
    } else {
      return new JsonResponse<>("Not allowed", RequestStatus.FAIL);
    }

  }

  /**
   * REST endpoint, remove the specified alert from the database.
   *
   * @param form wrapper containing targeted alert id.
   * @return SUCCESS status if the product exist, FAIL status otherwise.
   */
  @PostMapping("/removeAlert")
  public JsonResponse<Boolean> removeAlert(@RequestBody RemoveAlertForm form) {

    if (maintenanceBean.isMaintenanceMode()){
      return new JsonResponse<>(RequestStatus.MAINTENANCE);
    }

    if (isAdmin()) {
      if (adminService.removeAlert(form.getAlertId())) {
        return new JsonResponse<>(RequestStatus.SUCCESS, true);
      } else {
        return new JsonResponse<>("This alert doesn't exists or has already been removed", RequestStatus.FAIL);
      }
    } else {
      return new JsonResponse<>("Not allowed", RequestStatus.FAIL);
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

    if (maintenanceBean.isMaintenanceMode()){
      return new JsonResponse<>(RequestStatus.MAINTENANCE);
    }

    if (isAdmin()) {
      if (form.getSeuil() < 1) {
        return new JsonResponse<>("Seuil must be > 0", RequestStatus.FAIL);
      }

      if (adminService.updateAlertSeuil(form)) {
        return new JsonResponse<>(RequestStatus.SUCCESS, true);
      } else {
        return new JsonResponse<>("The specified alert doesn't exists", RequestStatus.FAIL);
      }
    } else {
      return new JsonResponse<>("Not allowed", RequestStatus.FAIL);
    }

  }

  /**
   * REST endpoint, transfer entity of an aliquot from a storage type to another one.
   *
   * @param form Wrapper containing nlot, quantity, from and destination sent by the client.
   * @return SUCCESS status if the operation can be done, FAIL status otherwise.
   */
  @PostMapping("/transfertAliquot")
  public JsonResponse<TransfertAliquotForm> transfertAliquot(@RequestBody TransfertAliquotForm form) {

    if (maintenanceBean.isMaintenanceMode()){
      return new JsonResponse<>(RequestStatus.MAINTENANCE);
    }

    if (isAdmin()) {
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
    } else {
      return new JsonResponse<>("Not allowed", RequestStatus.FAIL);
    }

  }

  /**
   * REST endpoint, add a new alert in the database.
   *
   * @param form Wrapper containing product name, quantity and storage type.
   * @return SUCCESS status if the operation can be done, FAIL status otherwise.
   */
  @PostMapping("/addAlert")
  public JsonResponse<AddAlertForm> addAlert(@RequestBody AddAlertForm form) {

    if (maintenanceBean.isMaintenanceMode()){
      return new JsonResponse<>(RequestStatus.MAINTENANCE);
    }

    if (isAdmin()) {
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
    } else {
      return new JsonResponse<>("Not allowed", RequestStatus.FAIL);
    }

  }

  /**
   * Endpoint enabling well-formatted POST requests to add a team trimestrial report.
   * 
   * @param form contains "losses", "finalFlag", "year", "quarter", "teamName" keys.
   * 
   * @return if successful, a JSON response with a success status, otherwise a JSON response with a
   *         fail status and the sent form as data.
   */
  @PostMapping("/saveReport")
  public JsonResponse<AddTeamTrimestrialReportForm> addTeamTrimestrialReport(
      @RequestBody AddTeamTrimestrialReportForm form) {
    if (maintenanceBean.isMaintenanceMode()){
      return new JsonResponse<>(RequestStatus.MAINTENANCE);
    }

    if (isAdmin()) {
      JsonResponse<AddTeamTrimestrialReportForm> failedRequestResponse = new JsonResponse<>(
          RequestStatus.FAIL);
      failedRequestResponse.setData(form);

      if (!form.validate()) {
        failedRequestResponse.setError("Missing attributes within request body");
        return failedRequestResponse;
      }

      if (adminService.saveTeamTrimestrialReport(form)) {
        return new JsonResponse<>(RequestStatus.SUCCESS);
      } else {
        failedRequestResponse.setError("Report could not be saved");
        return failedRequestResponse;
      }
    } else {
      return new JsonResponse<>("Not allowed", RequestStatus.FAIL);
    }
  }

  /**
   * REST endpoint returning all relevant information about withdrawn transactions made by a team in
   * a specific year's quarter.
   *
   * @return JSON response containing all relevant information.
   */
  @GetMapping("/withdrawnTransactions")
  public JsonResponse<TeamWithdrawnTransactionsData> getWithdrawnTransactionsByTeamAndQuarterAndYear(
      @RequestParam String teamName, @RequestParam String quarter, @RequestParam int year) {
    TeamWithdrawnTransactionsData reportTransactions = adminService
        .getWithdrawnTransactionsByTeamNameAndQuarterAndYear(teamName, quarter, year);

    if (maintenanceBean.isMaintenanceMode()){
      return new JsonResponse<>(RequestStatus.MAINTENANCE);
    }

    if (isAdmin()) {
      if (reportTransactions != null) {
        return new JsonResponse<>(RequestStatus.SUCCESS, reportTransactions);
      } else {
        return new JsonResponse<>("Could not retrieve a list of transactions", RequestStatus.FAIL);
      }
    } else {
      return new JsonResponse<>("Not allowed", RequestStatus.FAIL);
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

    if (maintenanceBean.isMaintenanceMode()){
      return new JsonResponse<>(RequestStatus.MAINTENANCE);
    }

    if (isAdmin()) {
      TransactionLossesData data = adminService.getTransactionLossesByQuarterAndYear(quarter, year);

      if (data == null) {
        return new JsonResponse<>("Could not retrieve any transaction losses", RequestStatus.FAIL);
      }
      return new JsonResponse<>(RequestStatus.SUCCESS, data);
    } else {
      return new JsonResponse<>("Not allowed", RequestStatus.FAIL);
    }
  }

  /**
   * REST endpoint returning the quarter and year of all editable reports.
   *
   * @return JSON response containing a list of quarter and year.
   */
  @GetMapping("/quarterYearOfEditableReports")
  public JsonResponse<List<EditableReportYearQuarterData>> getQuarterAndYearOfEditableReports() {

    if (maintenanceBean.isMaintenanceMode()){
      return new JsonResponse<>(RequestStatus.MAINTENANCE);
    }

    if (isAdmin()) {
      return new JsonResponse<>(RequestStatus.SUCCESS,
          adminService.getQuarterAndYearOfEditableReports());

    } else {
      return new JsonResponse<>("Not allowed", RequestStatus.FAIL);
    }

  }

  /**
   * REST endpoint returning all team names and their associated loss of a trimestrial report.
   *
   * @return JSON response containing a list of team names and losses.
   */
  @GetMapping("/teamReportLosses")
  public JsonResponse<List<TeamPriceLossesData>> getQuarterAndYearOfEditableReports(
      @RequestParam String quarter, @RequestParam Integer year) {

    if (maintenanceBean.isMaintenanceMode()){
      return new JsonResponse<>(RequestStatus.MAINTENANCE);
    }

    if (isAdmin()) {
      List<TeamPriceLossesData> data = adminService
          .getReportLossesAndTeamNameByYearAndQuarter(quarter, year);
      if (data != null) {
        return new JsonResponse<>(RequestStatus.SUCCESS, data);
      } else {
        return new JsonResponse<>("Could not retrieve any report losses", RequestStatus.FAIL);
      }
    } else {
      return new JsonResponse<>("Not allowed", RequestStatus.FAIL);
    }

  }

  /**
   * REST endpoint, retrieve all products and their aliquots.
   *
   * @return a JsonResponse containing a list of Products.
   */
  @GetMapping("/getAllProductsWithAliquots")
  public JsonResponse<List<Product>> getAllProductsWithAliquots() {

    if (maintenanceBean.isMaintenanceMode()){
      return new JsonResponse<>(RequestStatus.MAINTENANCE);
    }

    if (isAdmin()) {
      return new JsonResponse<>(RequestStatus.SUCCESS, adminService.getAllProductsWithAliquots());
    } else {
      return new JsonResponse<>("Not allowed", RequestStatus.FAIL);
    }

  }

  /**
   * REST endpoint, perform the inventory.
   *
   * @param forms list of form that contains nlot and quantity.
   * @return a JsonResponse SUCCESS if the form is valid, a JsonResponse FAIl otherwise
   */
  @PostMapping("/handleInventory")
  public JsonResponse<List<InventoryForm>> handleInventory(@RequestBody List<InventoryForm> forms) {

    if (maintenanceBean.isMaintenanceMode()){
      return new JsonResponse<>(RequestStatus.MAINTENANCE);
    }

    if (isAdmin()) {
      for (InventoryForm form : forms) {
        if (!form.validate()) {
          return new JsonResponse<>(RequestStatus.FAIL, forms);
        }
      }

      adminService.makeInventory(forms);

      return new JsonResponse<>(RequestStatus.SUCCESS);
    } else {
      return new JsonResponse<>("Not allowed", RequestStatus.FAIL);
    }

  }

  /**
   * REST endpoint, retrieve stats on provider for the admin homepage.
   *
   * @return a JsonResponse SUCCESS with a list of provider stat.
   */
  @GetMapping("/getProvidersStats")
  public JsonResponse<List<ProvidersStatsData>> getProvidersStats() {

    if (maintenanceBean.isMaintenanceMode()){
      return new JsonResponse<>(RequestStatus.MAINTENANCE);
    }

    if (isAdmin()) {
      return new JsonResponse<>(RequestStatus.SUCCESS, adminService.generateProvidersStats());
    } else {
      return new JsonResponse<>("Not allowed", RequestStatus.FAIL);
    }

  }

  /**
   * REST endpoint, retrieve the number of triggered alerts.
   *
   * @return a JsonResponse SUCCESS containing the number of triggered alerts.
   */
  @GetMapping("/getAlertsNotification")
  public JsonResponse<Integer> getAlertsNotification() {

    if (maintenanceBean.isMaintenanceMode()){
      return new JsonResponse<>(RequestStatus.MAINTENANCE);
    }

    if (isAdmin()) {
      return new JsonResponse(RequestStatus.SUCCESS, adminService.getAlertsNotification());
    } else {
      return new JsonResponse<>("Not allowed", RequestStatus.FAIL);
    }

  }


  /**
   * REST endpoint, retrieve the number of days until the next report.
   *
   * @return a JsonResponse SUCCESS containing the number of days until the next report.
   */
  @GetMapping("/getNextReport")
  public JsonResponse<NextReportData> getNextReport() {

    if (maintenanceBean.isMaintenanceMode()){
      return new JsonResponse<>(RequestStatus.MAINTENANCE);
    }

    if (isAdmin()) {
      return new JsonResponse<>(RequestStatus.SUCCESS, adminService.getNextReportData());
    } else {
      return new JsonResponse<>("Not allowed", RequestStatus.FAIL);
    }

  }

  /**
   * REST endpoint, retrieve the average price for all product.
   *
   * @return a JsonResponse containing a list of stats.
   */
  @GetMapping("/getProductsStats")
  public JsonResponse<List<ProductsStatsData>> getProductsStats() {

    if (maintenanceBean.isMaintenanceMode()){
      return new JsonResponse<>(RequestStatus.MAINTENANCE);
    }

    if (isAdmin()) {
      return new JsonResponse<>(RequestStatus.SUCCESS, adminService.generateProductsStats());
    } else {
      return new JsonResponse<>("Not allowed", RequestStatus.FAIL);
    }

  }

  /**
   * REST endpoint, allow a superAdministrator to set up maintenance mode.
   *
   * @param form wrapper containing mode and super admin password to activate maintenance mode.
   * @return a JsonResponse containing the set up mode.
   */
  @PostMapping("/setupMaintenanceMode")
  public JsonResponse<SetupMaintenanceForm> setUpMaintenanceMode(@RequestBody SetupMaintenanceForm form) {

    if (isAdmin() && form.validate() && form.getPassword().equals(config.getSuperAdminPassword())){

      maintenanceBean.setMaintenanceMode(form.isMode());

      return new JsonResponse<>(RequestStatus.SUCCESS, form);
    }

    return new JsonResponse<>(RequestStatus.FAIL, form);

  }

  /**
   * Utility function to tell us if the user logged in is admin or not.
   *
   * @return true if the user is admin, false otherwise.
   */
  private boolean isAdmin() {
    if (session.getAttribute("user") == null) {
      return false;
    }
    return ((User) session.getAttribute("user")).isAdmin();
  }

}
