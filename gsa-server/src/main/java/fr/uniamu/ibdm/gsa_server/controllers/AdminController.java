package fr.uniamu.ibdm.gsa_server.controllers;

import fr.uniamu.ibdm.gsa_server.dao.QueryObjects.StatsWithdrawQuery;
import fr.uniamu.ibdm.gsa_server.models.Aliquot;
import fr.uniamu.ibdm.gsa_server.requests.JsonResponse;
import fr.uniamu.ibdm.gsa_server.requests.RequestStatus;
import fr.uniamu.ibdm.gsa_server.requests.forms.AddAliquoteForm;
import fr.uniamu.ibdm.gsa_server.requests.forms.WithdrawStatsForm;
import fr.uniamu.ibdm.gsa_server.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin(allowCredentials = "true", origins = {"http://localhost:4200"})
public class AdminController {

  @Autowired
  HttpSession session;

  @Autowired
  AdminService adminService;

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

  @PostMapping("/addAliquote")
  public JsonResponse <AddAliquoteForm> addAliquote(@RequestBody AddAliquoteForm form){

    JsonResponse<AddAliquoteForm> failedRequestResponse = new JsonResponse<>(RequestStatus.FAIL);
    failedRequestResponse.setData(form);

    int aliquotNLot = form.getAliquotNLot();
    String aliquotExpirationDate = form.getAliquotExpirationDate();
    int aliquotQuantityVisibleStock = form.getAliquotQuantityVisibleStock();
    int aliquotQuantityHiddenStock = form.getAliquotQuantityHiddenStock();
    float aliquotPrice = form.getAliquotPrice();
    String provider = form.getProvider();
    String source = "goat";
    String target = "WOLF";

    if (aliquotNLot == 0|| aliquotExpirationDate == null || aliquotQuantityVisibleStock == 0 ||
            aliquotQuantityHiddenStock == 0 || aliquotPrice == 0 || provider == null ) {
      failedRequestResponse.setError("Missing attributes within request body");
      return failedRequestResponse;

    }

    boolean success = adminService.addAliquote(aliquotNLot, aliquotExpirationDate, aliquotQuantityVisibleStock,
            aliquotQuantityHiddenStock, aliquotPrice, provider, source,target );
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
  @GetMapping("/allSpeciesName")
  public JsonResponse<List<String>> getAllSpeciesName() {
    List<String> names = adminService.getAllSpeciesName();
    if (names != null) {
      return new JsonResponse<>(RequestStatus.SUCCESS, names);
    } else {
      return new JsonResponse<>("Could not retrieve all of species names", RequestStatus.FAIL);
    }
  }


}
