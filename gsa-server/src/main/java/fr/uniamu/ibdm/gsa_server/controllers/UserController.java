package fr.uniamu.ibdm.gsa_server.controllers;

import fr.uniamu.ibdm.gsa_server.conf.MaintenanceBean;
import fr.uniamu.ibdm.gsa_server.models.User;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.ProductOverviewData;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.TeamReportData;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.TransactionData;
import fr.uniamu.ibdm.gsa_server.requests.JsonResponse;
import fr.uniamu.ibdm.gsa_server.requests.RequestStatus;
import fr.uniamu.ibdm.gsa_server.requests.forms.GetProductNameForm;
import fr.uniamu.ibdm.gsa_server.requests.forms.PeriodForm;
import fr.uniamu.ibdm.gsa_server.requests.forms.WithdrowForm;
import fr.uniamu.ibdm.gsa_server.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@CrossOrigin(allowCredentials = "true", origins = { "http://localhost:4200", "http://localhost",
    "http://51.77.147.140" })
public class UserController {

  @Autowired
  HttpSession session;

  @Autowired
  UserServiceImpl userService;

  @Autowired
  MaintenanceBean maintenanceBean;

  /**
   * REST controller for the stockOverview request.
   *
   * @return A json formatted response.
   */
  @GetMapping("/stockOverview")
  public JsonResponse<List<ProductOverviewData>> stockOverview() {

    if (maintenanceBean.isMaintenanceMode()) {
      return new JsonResponse<>(RequestStatus.MAINTENANCE);
    }

    List<ProductOverviewData> data = userService.getAllOverviewProducts();

    return new JsonResponse<>(RequestStatus.SUCCESS, data);
  }

  /**
   * REST controller for the withdrowCart request.
   *
   * @return A json formatted response.
   */
  @PostMapping("/withdrawCart")
  public JsonResponse<Boolean> withdrawCart(@RequestBody List<WithdrowForm> form) {

    if (maintenanceBean.isMaintenanceMode()) {
      return new JsonResponse<>(RequestStatus.MAINTENANCE);
    }

    if (isLoggedIn() && ((boolean) session.getAttribute("techArea"))) {
      boolean data = userService.withdrawCart(form, (User) session.getAttribute("user"));
      JsonResponse<Boolean> response;

      if (data) {
        response = new JsonResponse<>(RequestStatus.SUCCESS);
      } else {
        response = new JsonResponse<>("bad aliquot nlot", RequestStatus.FAIL);
      }

      return response;
    }
    return new JsonResponse<>("Please log in", RequestStatus.FAIL);
  }

  /**
   * REST controller for the getProductName request, return a product name from a lot number.
   *
   * @return A json formatted response.
   */
  @PostMapping("/getProductName")
  public JsonResponse<String> getProductName(@RequestBody GetProductNameForm form) {

    if (maintenanceBean.isMaintenanceMode()) {
      return new JsonResponse<>(RequestStatus.MAINTENANCE);
    }

    String productName = userService.getProductNameFromNlot(form.getNlot());
    JsonResponse<String> response;

    if (productName == null) {
      response = new JsonResponse<>("bad lot number", RequestStatus.FAIL);
    } else {
      response = new JsonResponse<>(RequestStatus.SUCCESS, productName);
    }

    return response;
  }

  /**
   * Endpoint allowing the user to retrieve all of its past and current team trimestrial reports.
   *
   * @return A json formatted response.
   */
  @GetMapping("/teamReports")
  public JsonResponse<List<TeamReportData>> getUserTeamReports() {

    if (maintenanceBean.isMaintenanceMode()) {
      return new JsonResponse<>(RequestStatus.MAINTENANCE);
    }

    User user = (User) session.getAttribute("user");
    JsonResponse<List<TeamReportData>> failedResponse = new JsonResponse<>(
        "Could not retrieve any data", RequestStatus.FAIL);
    if (!isLoggedIn()) {
      return failedResponse;
    }

    List<TeamReportData> teamReports = userService.getUserTeamReports(user.getUserId());

    if (teamReports == null) {
      return failedResponse;
    } else {
      return new JsonResponse<>(RequestStatus.SUCCESS, teamReports);
    }

  }

  /**
   * REST controller for the getAllTeamName request, return a list of all team name.
   *
   * @return A json formatted response.
   */
  @GetMapping("/getAllTeamName")
  public JsonResponse<List<String>> getAllTeamName() {

    if (maintenanceBean.isMaintenanceMode()) {
      return new JsonResponse<>(RequestStatus.MAINTENANCE);
    }

    return new JsonResponse<>(RequestStatus.SUCCESS, userService.getAllTeamName());
  }

  /**
   * REST controller for the getAllProductName request, return a list of all product name.
   *
   * @return A json formatted response.
   */
  @GetMapping("/getAllProductName")
  public JsonResponse<List<String>> getAllProductName() {

    if (maintenanceBean.isMaintenanceMode()) {
      return new JsonResponse<>(RequestStatus.MAINTENANCE);
    }

    return new JsonResponse<>(RequestStatus.SUCCESS, userService.getAllProductName());
  }

  /**
   * REST endpoint, this endpoint let us know if the app is in maintenance mode.
   *
   * @return A JsonResponse containing a boolean.
   */
  @GetMapping("/isMaintenanceMode")
  public JsonResponse<Boolean> isMaintenanceMode() {
    return new JsonResponse<>(RequestStatus.SUCCESS, maintenanceBean.isMaintenanceMode());
  }

  /**
   * Utility function, tell us if the user is logged in or not.
   *
   * @return tru if user is logged, false otherwise.
   */
  private boolean isLoggedIn() {
    if (session.getAttribute("user") == null) {
      return false;
    } else {
      return true;
    }
  }

  /**
   * Endpoint for /history. Return a list of withdrawals depending of the period given in argument.
   *
   * @param form the form containing the date of begin and the date of end of the period.
   * @return if successful, a JSON response with a success status, otherwise a JSON response with a
   *         fail status and an error message.
   */
  @PostMapping("/history")
  public JsonResponse<List<TransactionData>> getUserWithdrawalsHistory(
      @RequestBody PeriodForm form) {
    List<TransactionData> withdrawalsHistory;

    User user = (User) session.getAttribute("user");

    if (isLoggedIn() && form.validate()) {
      if (form.getBegin() != null && form.getEnd() != null) {
        withdrawalsHistory = userService.getUserWithdrawalsHistoryBetween(user.getUserId(),
            form.getBegin(), form.getEnd());
      } else if (form.getBegin() != null && form.getEnd() == null) {
        withdrawalsHistory = userService.getUserWithdrawalsHistorySince(user.getUserId(),
            form.getBegin());
      } else if (form.getBegin() == null && form.getEnd() != null) {
        withdrawalsHistory = userService.getUserWithdrawalsHistoryUpTo(user.getUserId(),
            form.getEnd());
      } else {
        withdrawalsHistory = userService.getUserWithdrawalsHistory(user.getUserId());
      }

      if (withdrawalsHistory != null) {
        return new JsonResponse<>(RequestStatus.SUCCESS, withdrawalsHistory);
      }

      return new JsonResponse<>("Could not find withdrawals", RequestStatus.FAIL);
    }

    return new JsonResponse<>("Could not find withdrawals", RequestStatus.FAIL);
  }

}
