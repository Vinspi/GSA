package fr.uniamu.ibdm.gsa_server.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.uniamu.ibdm.gsa_server.dao.QueryObjects.StatsWithdrawQuery;
import fr.uniamu.ibdm.gsa_server.models.Aliquot;
import fr.uniamu.ibdm.gsa_server.requests.JsonResponse;
import fr.uniamu.ibdm.gsa_server.requests.RequestStatus;
import fr.uniamu.ibdm.gsa_server.requests.forms.WithdrawStatsForm;
import fr.uniamu.ibdm.gsa_server.services.AdminService;


@RestController
@RequestMapping("/aliquot")
@CrossOrigin(allowCredentials = "true", origins = {"http://localhost:4200"})
public class AliquotController {

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
    @GetMapping("/get-aliquots")
  public JsonResponse<List<Aliquot>> getAliquots() {
    return new JsonResponse<>(RequestStatus.SUCCESS, adminService.getAllAliquots());
  }
  
  @DeleteMapping("/delete-aliquot/{id}")
  public void deleteAliquot(@PathVariable long id) {
	  adminService.makeQuantityZero(id);
  }

}
