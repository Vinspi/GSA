package fr.uniamu.ibdm.gsa_server.controllers;

import fr.uniamu.ibdm.gsa_server.dao.QueryObjects.StatsWithdrawQuery;
import fr.uniamu.ibdm.gsa_server.requests.JsonResponse;
import fr.uniamu.ibdm.gsa_server.requests.RequestStatus;
import fr.uniamu.ibdm.gsa_server.requests.forms.AddProductForm;
import fr.uniamu.ibdm.gsa_server.requests.forms.WithdrawStatsForm;
import fr.uniamu.ibdm.gsa_server.services.AdminService;
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

  /**
   * REST endpoint for /stats call, return stats needed for building admin chart.
   *
   * @param form
   *          The information needed to compute data.
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
}
