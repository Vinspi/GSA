package fr.uniamu.ibdm.gsa_server.controllers;

import fr.uniamu.ibdm.gsa_server.dao.QueryObjects.StatsWithdrawQuery;
import fr.uniamu.ibdm.gsa_server.dao.QueryObjects.TriggeredAlertsQuery;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.AlertsData;
import fr.uniamu.ibdm.gsa_server.requests.JsonResponse;
import fr.uniamu.ibdm.gsa_server.requests.RequestStatus;
import fr.uniamu.ibdm.gsa_server.requests.forms.AddAliquoteForm;
import fr.uniamu.ibdm.gsa_server.requests.forms.AddProductForm;
import fr.uniamu.ibdm.gsa_server.requests.forms.RemoveAlertForm;
import fr.uniamu.ibdm.gsa_server.requests.forms.UpdateAlertForm;
import fr.uniamu.ibdm.gsa_server.requests.forms.WithdrawStatsForm;
import fr.uniamu.ibdm.gsa_server.services.AdminService;
import fr.uniamu.ibdm.gsa_server.services.UserService;
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
@CrossOrigin(allowCredentials = "true", origins = { "http://localhost:4200" })
public class AdminController {

  @Autowired
  HttpSession session;

  @Autowired
  AdminService adminService;

  @Autowired
  UserService userService;

  /**
   * REST endpoint for /stats call, return stats needed for building admin chart.
   *
   * @param form The information needed to compute data.
   * @return a JSON formatted response.
   */
  @PostMapping("/stats")
  public JsonResponse<List<StatsWithdrawQuery>> getWithdrawStats(@RequestBody WithdrawStatsForm form) {

    System.out.println(form.getProductName());
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
   * @param form
   *          contains "targetName" and "sourceName" keys.
   * @return if successful, a JSON response with a success status, otherwise a
   *         JSON response with a fail status and the sent form as data.
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
   * Endpoint enabling well-formatted POST requests to add a aliquote.
   * @param form contains n°aliquote & quantity in visible stock & quantity in hidden stock
   *             price & provider & source & target of aliquote.
   * @return if successful, a JSON response with a success status, otherwise a
   *         JSON response with a fail status and the sent form as data.
   */
  @PostMapping("/addAliquote")
  public JsonResponse<AddAliquoteForm> addAliquote(@RequestBody AddAliquoteForm form){

    JsonResponse<AddAliquoteForm> failedRequestResponse = new JsonResponse<>(RequestStatus.FAIL);
    failedRequestResponse.setData(form);

    long aliquotNLot = form.getAliquotNLot();
    int aliquotQuantityVisibleStock = form.getAliquotQuantityVisibleStock();
    int aliquotQuantityHiddenStock = form.getAliquotQuantityHiddenStock();
    float aliquotPrice = form.getAliquotPrice();
    String provider = form.getAliquoteProvider();
    String product = form.getAliquoteproduct();

    if (aliquotNLot == 0 || aliquotPrice == 0 || provider.length() ==0 || product.length() ==0 )
    {
      failedRequestResponse.setError("Missing values");
      return failedRequestResponse;
    }

    if (aliquotNLot < 0 || aliquotQuantityVisibleStock < 0 || aliquotQuantityHiddenStock < 0 || aliquotPrice < 0){
        failedRequestResponse.setError("values can not be negative");
        return failedRequestResponse;
    }

    boolean success = adminService.addAliquote(aliquotNLot, aliquotQuantityVisibleStock,
            aliquotQuantityHiddenStock, aliquotPrice, provider, product );
    if (success)
    {
      return new JsonResponse<>(RequestStatus.SUCCESS);
    } else {
      failedRequestResponse.setError("Could not add the aliquote");
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
  public JsonResponse<List<TriggeredAlertsQuery>> getTriggeredAlerts(){
    return new JsonResponse<>(RequestStatus.SUCCESS, adminService.getTriggeredAlerts());
  }


  /**
   * REST endpoint, return all alerts present in the database.
   *
   * @return a list of wrappers containing product name, seuil and type of the alert.
   */
  @GetMapping("/getAllAlerts")
  public JsonResponse<List<AlertsData>> getAllAlerts(){
    return new JsonResponse<>(RequestStatus.SUCCESS, adminService.getAllAlerts());
  }

  /**
   * REST endpoint, remove the specified alert from the database.
   *
   * @param form wrapper containing targeted alert id.
   * @return SUCCESS status if the product exist, FAIL status otherwise.
   */
  @PostMapping("/removeAlert")
  public JsonResponse<Boolean> removeAlert(@RequestBody RemoveAlertForm form){
    if (adminService.removeAlert(form.getAlertId())) {
      return new JsonResponse<>(RequestStatus.SUCCESS, true);
    }
    else {
      return new JsonResponse<>("This alert doesn't exists or has already been removed",RequestStatus.FAIL);
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

    if(adminService.updateAlertSeuil(form)) {
      return new JsonResponse<>(RequestStatus.SUCCESS, true);
    }
    else {
      return new JsonResponse<>("The specified alert doesn't exists", RequestStatus.FAIL);
    }
  }

}
