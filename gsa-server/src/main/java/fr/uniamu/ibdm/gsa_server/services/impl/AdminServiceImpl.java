package fr.uniamu.ibdm.gsa_server.services.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.uniamu.ibdm.gsa_server.dao.AlertRepository;
import fr.uniamu.ibdm.gsa_server.dao.AliquotRepository;
import fr.uniamu.ibdm.gsa_server.dao.ProductRepository;
import fr.uniamu.ibdm.gsa_server.dao.SpeciesRepository;
import fr.uniamu.ibdm.gsa_server.dao.TeamRepository;
import fr.uniamu.ibdm.gsa_server.dao.TeamTrimestrialReportRepository;
import fr.uniamu.ibdm.gsa_server.dao.QueryObjects.AlertAliquot;
import fr.uniamu.ibdm.gsa_server.dao.QueryObjects.StatsWithdrawQuery;
import fr.uniamu.ibdm.gsa_server.dao.QueryObjects.TriggeredAlertsQuery;
import fr.uniamu.ibdm.gsa_server.models.Alert;
import fr.uniamu.ibdm.gsa_server.models.Aliquot;
import fr.uniamu.ibdm.gsa_server.models.Product;
import fr.uniamu.ibdm.gsa_server.models.Species;
import fr.uniamu.ibdm.gsa_server.models.Team;
import fr.uniamu.ibdm.gsa_server.models.TeamTrimestrialReport;
import fr.uniamu.ibdm.gsa_server.models.Transaction;
import fr.uniamu.ibdm.gsa_server.models.enumerations.AlertType;
import fr.uniamu.ibdm.gsa_server.models.enumerations.Quarter;
import fr.uniamu.ibdm.gsa_server.models.primarykeys.ProductPK;
import fr.uniamu.ibdm.gsa_server.models.primarykeys.TeamTrimestrialReportPK;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.AlertsData;
import fr.uniamu.ibdm.gsa_server.requests.forms.TeamTrimestrialReportForm;
import fr.uniamu.ibdm.gsa_server.requests.forms.UpdateAlertForm;
import fr.uniamu.ibdm.gsa_server.requests.forms.WithdrawStatsForm;
import fr.uniamu.ibdm.gsa_server.services.AdminService;
import fr.uniamu.ibdm.gsa_server.util.DateConverter;

@Service
public class AdminServiceImpl implements AdminService {

  private ProductRepository productRepository;
  private AliquotRepository aliquotRepository;
  private AlertRepository alertRepository;
  private TeamRepository teamRepository;
  private SpeciesRepository speciesRepository;
  private TeamTrimestrialReportRepository teamTrimestrialReportRepository;

  /**
   * Constructor for the AdminService.
   *
   * @param productRepository Autowired repository.
   * @param aliquotRepository Autowired repository.
   * @param speciesRepository Autowired repository.
   * @param teamRepository Autowired repository. 
   * @param alertRepository Autowired repository.
   * @param teamTrimestrialReportRepository Autowired repository.
   */
  @Autowired
  public AdminServiceImpl(ProductRepository productRepository, AliquotRepository aliquotRepository, SpeciesRepository speciesRepository, TeamRepository teamRepository, AlertRepository alertRepository, TeamTrimestrialReportRepository teamTrimestrialReportRepository) {
    this.productRepository = productRepository;
    this.aliquotRepository = aliquotRepository;
    this.speciesRepository = speciesRepository;
    this.teamRepository = teamRepository;
    this.alertRepository = alertRepository;
    this.teamTrimestrialReportRepository = teamTrimestrialReportRepository;
  }

  @Override
  public List<StatsWithdrawQuery> getWithdrawStats(WithdrawStatsForm form) {

    String[] shards = form.getProductName().split("_");

    if (shards.length < 3) {
      return new ArrayList<>();
    }

    String lowerBound;
    String upperBound;

    lowerBound = form.getYearLowerBound() + "-" + DateConverter.monthToNumberConvertor(form.getMonthLowerBound())
        + "-01 00:00:00";

    upperBound = form.getYearUpperBound() + "-" + DateConverter.monthToNumberConvertor(form.getMonthUpperBound())
        + "-31 00:00:00";

    System.out.println("lower bound : " + lowerBound);

    List<Object[]> result = productRepository.getWithdrawStats(form.getTeamName(), lowerBound, upperBound, shards[0],
        shards[2]);
    List<StatsWithdrawQuery> returnValue = new ArrayList<>();

    for (int i = 0; i < result.size(); i++) {

      returnValue
          .add(new StatsWithdrawQuery((int) result.get(i)[0], (int) result.get(i)[1], (BigDecimal) result.get(i)[2]));


    }

    /* remove 'holes' in the data */
    StatsWithdrawQuery tmp;
    for (int i = 0; i < returnValue.size() - 1; i++) {

      tmp = returnValue.get(i);

      if (tmp.getMonth() == 12 && returnValue.get(i + 1).getMonth() != 1) {
        returnValue.add(i + 1, new StatsWithdrawQuery(1, tmp.getYear(), 0));
      } else if (returnValue.get(i + 1).getMonth() != returnValue.get(i).getMonth() + 1 && tmp.getMonth() != 12) {
        returnValue.add(i + 1, new StatsWithdrawQuery(tmp.getMonth() + 1, tmp.getYear(), 0));
      }
    }

    return returnValue;
  }

  @Override
  public List<String> getAllSpeciesNames() {
    return speciesRepository.getAllSpeciesNames();
  }

  @Override
  public boolean addProduct(String sourceName, String targetName) {

    Species sourceSpecies = null;
    Species targetSpecies = null;
    Optional<Species> nullableSourceSpecies = speciesRepository.findById(sourceName);
    Optional<Species> nullableTargetSpecies = speciesRepository.findById(targetName);

    if (nullableTargetSpecies.isPresent() && nullableSourceSpecies.isPresent()) {
      sourceSpecies = nullableSourceSpecies.get();
      targetSpecies = nullableTargetSpecies.get();

    } else {
      return false;
    }

    Collection<Aliquot> aliquots = new ArrayList<>();
    Product newProduct = new Product(targetSpecies, sourceSpecies, aliquots);

    ProductPK productPk = new ProductPK();
    productPk.setSource(sourceName);
    productPk.setTarget(targetName);
    Optional<Product> nullableProduct = productRepository.findById(productPk);

    if (nullableProduct.isPresent()) {
      return false;
    } else {
      productRepository.save(newProduct);
      return true;
    }
  }

  @Override
  public List<TriggeredAlertsQuery> getTriggeredAlerts() {

    List<Object[]> queryResult = productRepository.getTriggeredAlertsVisible();
    queryResult.addAll(productRepository.getTriggeredAlertsHidden());
    queryResult.addAll(productRepository.getTriggeredAlertsGeneral());

    List<Object[]> aliquotsNativeQuery;
    List<TriggeredAlertsQuery> returnValue = new ArrayList<>();
    List<AlertAliquot> alertAliquots;
    int qte;
    AlertType type;
    int qteHidden;
    int qteVisible;

    for (Object[] o : queryResult) {
      aliquotsNativeQuery = aliquotRepository.findAllBySourceAndTargetQuery((String) o[0], (String) o[1]);
      alertAliquots = new ArrayList<>();

      type = AlertType.valueOf((String) o[4]);


      for (Object[] a : aliquotsNativeQuery) {
        qteHidden = ((BigInteger) a[3]).intValue();
        qteVisible = ((BigInteger) a[2]).intValue();
        if (type.equals(AlertType.VISIBLE_STOCK)) {
          qte = qteVisible;
        } else if (type.equals(AlertType.HIDDEN_STOCK)) {
          qte = qteHidden;
        } else {
          qte = qteVisible + qteHidden;
        }
        alertAliquots.add(new AlertAliquot(((BigInteger) a[0]).longValue(), ((Timestamp) a[1]).toLocalDateTime().toLocalDate(), qte));
      }

      returnValue.add(new TriggeredAlertsQuery(
          (String) o[0],
          (String) o[1],
          ((BigDecimal) o[2]).intValue(),
          (int) o[3], type,
          alertAliquots,
          ((BigInteger) o[5]).longValue()));

    }
    return returnValue;
  }

  @Override
  public List<AlertsData> getAllAlerts() {

    List<AlertsData> data = new ArrayList<>();

    alertRepository.findAll().forEach(alert -> {
      data.add(new AlertsData(alert.getProduct().getProductName(), alert.getSeuil(), alert.getAlertType(), alert.getAlertId()));
    });

    return data;
  }

  @Override
  public boolean updateAlertSeuil(UpdateAlertForm form) {

    Optional<fr.uniamu.ibdm.gsa_server.models.Alert> optAlert = alertRepository.findById(form.getAlertId());

    if (optAlert.isPresent()) {
      fr.uniamu.ibdm.gsa_server.models.Alert a = optAlert.get();
      a.setSeuil(form.getSeuil());
      alertRepository.save(a);
      return true;
    } else {
      return false;
    }

  }

  @Override
  public boolean removeAlert(long id) {

    Optional<Alert> optAlert = alertRepository.findById(id);

    if (optAlert.isPresent()) {
      alertRepository.delete(optAlert.get());
      return true;
    } else {
      return false;
    }

  }
  
  @Override
  public boolean addTeamTrimestrialReport(TeamTrimestrialReportForm form) {
    
    // Checking that the stringified quarter is a valid value of Quarter Enum. 
    if(!Arrays.stream(Quarter.values()).map(Quarter::name).collect(Collectors.toSet()).contains(form.getQuarter())) {
      return false;
    }
    
    Optional<Team> nullableTeam = teamRepository.findById(form.getTeamId());
    Team team;

    // Checking that the team is valid
    if (nullableTeam.isPresent()) {
      team = nullableTeam.get();
    } else {
      return false;
    }

    TeamTrimestrialReportPK teamTrimestrialReportPk = new TeamTrimestrialReportPK();
    Quarter quarter = Quarter.valueOf(form.getQuarter());

    teamTrimestrialReportPk.setTeam(form.getTeamId());
    teamTrimestrialReportPk.setYear(form.getYear());
    teamTrimestrialReportPk.setQuarter(quarter);

    Optional<TeamTrimestrialReport> nullableReport = teamTrimestrialReportRepository.findById(teamTrimestrialReportPk);

    if (nullableReport.isPresent()) {
      return false;
    }

    TeamTrimestrialReport teamTrimestrialReport = new TeamTrimestrialReport();
    teamTrimestrialReport.setFinalFlag(form.getFinalFlag());
    teamTrimestrialReport.setLosts(form.getLosts());
    teamTrimestrialReport.setQuarter(quarter);
    teamTrimestrialReport.setTeam(team);
    teamTrimestrialReport.setYear(form.getYear());

    teamTrimestrialReportRepository.save(teamTrimestrialReport);
    
    return true;
  }

  @Override
  public List<Transaction> getTransactionsByTeamAndQuarter(Long id, String quarter) {
    // TODO Auto-generated method stub
    return null;
  }
}
