package fr.uniamu.ibdm.gsa_server.services.impl;

import fr.uniamu.ibdm.gsa_server.dao.AlertRepository;
import fr.uniamu.ibdm.gsa_server.dao.AliquotRepository;
import fr.uniamu.ibdm.gsa_server.dao.ProductRepository;
import fr.uniamu.ibdm.gsa_server.dao.QueryObjects.AlertAliquot;
import fr.uniamu.ibdm.gsa_server.dao.QueryObjects.StatsWithdrawQuery;
import fr.uniamu.ibdm.gsa_server.dao.QueryObjects.TriggeredAlertsQuery;
import fr.uniamu.ibdm.gsa_server.dao.SpeciesRepository;
import fr.uniamu.ibdm.gsa_server.models.Alert;
import fr.uniamu.ibdm.gsa_server.models.Aliquot;
import fr.uniamu.ibdm.gsa_server.models.Product;
import fr.uniamu.ibdm.gsa_server.models.Species;
import fr.uniamu.ibdm.gsa_server.models.Team;
import fr.uniamu.ibdm.gsa_server.models.TeamTrimestrialReport;
import fr.uniamu.ibdm.gsa_server.models.enumerations.AlertType;
import fr.uniamu.ibdm.gsa_server.models.primarykeys.ProductPK;
import fr.uniamu.ibdm.gsa_server.models.primarykeys.TeamTrimestrialReportPk;
import fr.uniamu.ibdm.gsa_server.requests.forms.AddAlertForm;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.AlertsData;
import fr.uniamu.ibdm.gsa_server.requests.forms.AddAliquoteForm;
import fr.uniamu.ibdm.gsa_server.requests.forms.TransfertAliquotForm;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.TransactionLossesData;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.TransactionLossesData.ProductLossData;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.ReportData;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.ReportData.ReportTransactionData;
import fr.uniamu.ibdm.gsa_server.requests.forms.AddAliquoteForm;
import fr.uniamu.ibdm.gsa_server.requests.forms.AddTeamTrimestrialReportForm;
import fr.uniamu.ibdm.gsa_server.requests.forms.UpdateAlertForm;
import fr.uniamu.ibdm.gsa_server.requests.forms.WithdrawStatsForm;
import fr.uniamu.ibdm.gsa_server.services.AdminService;
import fr.uniamu.ibdm.gsa_server.util.BigDecimalAttributeConverter;
import fr.uniamu.ibdm.gsa_server.util.DateConverter;
import fr.uniamu.ibdm.gsa_server.util.QuarterDateConverter;
import fr.uniamu.ibdm.gsa_server.util.TimeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

  @Autowired
  private TimeFactory clock;

  private ProductRepository productRepository;
  private AliquotRepository aliquotRepository;
  private AlertRepository alertRepository;

  private SpeciesRepository speciesRepository;
  private TeamRepository teamRepository;
  private TeamTrimestrialReportRepository teamTrimestrialReportRepository;
  private TransactionRepository transactionRepository;

  /**
   * Constructor for the AdminService.
   * 
   * @param productRepository Autowired repository
   * @param aliquotRepository Autowired repository
   * @param speciesRepository Autowired repository
   * @param alertRepository Autowired repository
   * @param teamRepository Autowired repository
   * @param teamTrimestrialReportRepository Autowired repository
   * @param transactionRepository Autowired repository
   */
  @Autowired
  public AdminServiceImpl(ProductRepository productRepository, AliquotRepository aliquotRepository,
      SpeciesRepository speciesRepository, AlertRepository alertRepository,
      TeamRepository teamRepository,
      TeamTrimestrialReportRepository teamTrimestrialReportRepository,
      TransactionRepository transactionRepository) {    this.productRepository = productRepository;
    this.aliquotRepository = aliquotRepository;
    this.speciesRepository = speciesRepository;
    this.alertRepository = alertRepository;
    this.teamRepository = teamRepository;
    this.teamTrimestrialReportRepository = teamTrimestrialReportRepository;
    this.transactionRepository = transactionRepository;
  }

  @Override
  public List<StatsWithdrawQuery> getWithdrawStats(WithdrawStatsForm form) {

    String[] shards = form.getProductName().split("_");

    if (shards.length < 3) {
      return new ArrayList<>();
    }

    String lowerBound;
    String upperBound;

    lowerBound = form.getYearLowerBound() + "-"
        + DateConverter.monthToNumberConvertor(form.getMonthLowerBound()) + "-01 00:00:00";

    upperBound = form.getYearUpperBound() + "-"
        + DateConverter.monthToNumberConvertor(form.getMonthUpperBound()) + "-31 00:00:00";

    System.out.println("lower bound : " + lowerBound);

    List<Object[]> result = productRepository.getWithdrawStats(form.getTeamName(), lowerBound,
        upperBound, shards[0], shards[2]);
    List<StatsWithdrawQuery> returnValue = new ArrayList<>();

    for (int i = 0; i < result.size(); i++) {

      returnValue.add(new StatsWithdrawQuery((int) result.get(i)[0], (int) result.get(i)[1],
          (BigDecimal) result.get(i)[2]));

    }

    /* remove 'holes' in the data */
    StatsWithdrawQuery tmp;
    for (int i = 0; i < returnValue.size() - 1; i++) {

      tmp = returnValue.get(i);

      if (tmp.getMonth() == 12 && returnValue.get(i + 1).getMonth() != 1) {
        returnValue.add(i + 1, new StatsWithdrawQuery(1, tmp.getYear(), 0));
      } else if (returnValue.get(i + 1).getMonth() != returnValue.get(i).getMonth() + 1
          && tmp.getMonth() != 12) {
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
      aliquotsNativeQuery = aliquotRepository.findAllBySourceAndTargetQuery((String) o[0],
          (String) o[1]);
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
        alertAliquots.add(new AlertAliquot(((BigInteger) a[0]).longValue(),
            ((Timestamp) a[1]).toLocalDateTime().toLocalDate(), qte));
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
      data.add(new AlertsData(alert.getProduct().getProductName(), alert.getSeuil(),
          alert.getAlertType(), alert.getAlertId()));
    });

    return data;
  }

  @Override
  public boolean updateAlertSeuil(UpdateAlertForm form) {

    Optional<fr.uniamu.ibdm.gsa_server.models.Alert> optAlert = alertRepository
        .findById(form.getAlertId());

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
  public boolean addAliquot(AddAliquoteForm form) {

    Aliquot newAliquot = new Aliquot();
    newAliquot.setAliquotNLot(form.getAliquotNLot());
    newAliquot.setAliquotExpirationDate(LocalDate.now().plusYears(1));
    newAliquot.setAliquotQuantityVisibleStock(form.getAliquotQuantityVisibleStock());
    newAliquot.setAliquotQuantityHiddenStock(form.getAliquotQuantityHiddenStock());
    newAliquot.setAliquotPrice(form.getAliquotPrice());
    newAliquot.setProvider(form.getAliquotProvider());

    String[] fullName = form.getAliquotProduct().split("_");

    ProductPK productPk = new ProductPK();
    productPk.setSource(fullName[0]);
    productPk.setTarget(fullName[2]);
    Optional<Product> nullableProduct = productRepository.findById(productPk);

    Optional<Aliquot> idExist = aliquotRepository.findById(form.getAliquotNLot());

    if (idExist.isPresent()) {
      return false;
    }

    if (nullableProduct.isPresent()) {
      newAliquot.setProduct(nullableProduct.get());
      aliquotRepository.save(newAliquot);
      return true;
    } else {
      return false;
    }
  }

  @Override
  public boolean transfertAliquot(TransfertAliquotForm form) {

    Optional<Aliquot> aliquotOpt = aliquotRepository.findById(form.getNumLot());
    Aliquot aliquot;
    long transfertQuantity;

    if (aliquotOpt.isPresent()) {
      aliquot = aliquotOpt.get();
      if (form.getFrom() == StorageType.RESERVE) {
        transfertQuantity = form.getQuantity() >= aliquot.getAliquotQuantityHiddenStock() ? aliquot.getAliquotQuantityHiddenStock() : form.getQuantity();
        aliquot.setAliquotQuantityHiddenStock(aliquot.getAliquotQuantityHiddenStock() - transfertQuantity);
        aliquot.setAliquotQuantityVisibleStock(aliquot.getAliquotQuantityVisibleStock() + transfertQuantity);
      } else {
        transfertQuantity = form.getQuantity() >= aliquot.getAliquotQuantityVisibleStock() ? aliquot.getAliquotQuantityVisibleStock() : form.getQuantity();
        aliquot.setAliquotQuantityHiddenStock(aliquot.getAliquotQuantityHiddenStock() + transfertQuantity);
        aliquot.setAliquotQuantityVisibleStock(aliquot.getAliquotQuantityVisibleStock() - transfertQuantity);
      }
      aliquotRepository.save(aliquot);
      return true;
    } else {
      return false;
    }
  }

  @Override
  public boolean addAlert(AddAlertForm form) {

    String[] shards = form.getProductName().split("_");
    Optional<Product> productOPt = productRepository.findById(new ProductPK(shards[2], shards[0]));
    Optional<Alert> alert;

    if (productOPt.isPresent()) {
      alert = alertRepository.findByAlertTypeAndProduct(EnumConvertor.storageTypeToAlertType(form.getStorageType()), productOPt.get());

      if (!alert.isPresent()) {
        /* we can add the alert */
        Alert alert1 = new Alert();
        alert1.setSeuil(form.getQuantity());
        alert1.setProduct(productOPt.get());
        alert1.setAlertType(EnumConvertor.storageTypeToAlertType(form.getStorageType()));


        alertRepository.save(alert1);

        return true;
      } else {
        /* the alert already exists */
        return false;
      }
    } else {
      return false;
    }
  }

 @Override
  public boolean saveTeamTrimestrialReport(AddTeamTrimestrialReportForm form) {

    // Checking that the stringified quarter is a valid value of Quarter Enum.
    if (!Arrays.stream(Quarter.values()).map(Quarter::name).collect(Collectors.toSet())
        .contains(form.getQuarter())) {
      return false;
    }

    // Checking that the quarter is over in order to save
    LocalDate now = clock.now();
    System.out.println("NOW " + now.toString());
    LocalDate lastDay = QuarterDateConverter.getQuarterLastDay(form.getQuarter(), form.getYear());
    System.out.println("lastDay : " + lastDay);
    if (now.isBefore(lastDay)) {
      return false;
    }

    // Checking that the team is valid
    Team team = teamRepository.findByTeamName(form.getTeamName());
    if (team == null) {
      return false;
    }

    TeamTrimestrialReportPk teamTrimestrialReportPk = new TeamTrimestrialReportPk();
    Quarter quarter = Quarter.valueOf(form.getQuarter());

    teamTrimestrialReportPk.setTeam(team.getTeamId());
    teamTrimestrialReportPk.setYear(form.getYear());
    teamTrimestrialReportPk.setQuarter(quarter);

    Optional<TeamTrimestrialReport> nullableReport = teamTrimestrialReportRepository
        .findById(teamTrimestrialReportPk);

    // Checking that the report is still editable
    if (nullableReport.isPresent()) {
      TeamTrimestrialReport currentReport = nullableReport.get();
      if (currentReport.isFinalFlag()) {
        return false;
      }
    }

    TeamTrimestrialReport teamTrimestrialReport = new TeamTrimestrialReport();
    teamTrimestrialReport.setFinalFlag(form.getFinalFlag());
    teamTrimestrialReport.setLosses(form.getLosses());
    teamTrimestrialReport.setQuarter(quarter);
    teamTrimestrialReport.setTeam(team);
    teamTrimestrialReport.setYear(form.getYear());

    teamTrimestrialReportRepository.save(teamTrimestrialReport);

    return true;
  }

  @Override
  public ReportData getWithdrawnTransactionsByTeamNameAndQuarterAndYear(String teamName,
      String quarter, int year) {
    if (teamName == null || quarter == null) {
      return null;
    }

    LocalDate firstDay = QuarterDateConverter.getQuarterFirstDay(quarter, year);
    if (firstDay == null) {
      return null;
    }
    LocalDate lastDay = QuarterDateConverter.getQuarterLastDay(quarter, year);
    if (lastDay == null) {
      return null;
    }

    List<Object[]> resultQuery = transactionRepository.getWithdrawnTransactionsByTeamNameAndQuarter(
        teamName, firstDay.toString(), lastDay.toString());

    ReportData data = new ReportData();
    List<ReportTransactionData> transactions = new ArrayList<>();
    Float totalPrice = 0F;

    for (Object[] o : resultQuery) {

      ReportTransactionData transactionData = data.new ReportTransactionData();

      transactionData.setAliquotPrice(((BigDecimal) o[0]).floatValue());
      transactionData.setTransactionDate((String) o[1].toString());
      transactionData.setTransactionQuantity((Integer) o[2]);
      transactionData.setUserName((String) o[3]);
      String source = (String) o[4];
      String target = (String) o[5];
      ProductPK productPk = new ProductPK();
      productPk.setSource(source);
      productPk.setTarget(target);

      // get() function won't return null as the source and the target are well-defined in the
      // aliquot table.
      transactionData.setProductName(productRepository.findById(productPk).get().getProductName());
      totalPrice += transactionData.getAliquotPrice() * transactionData.getTransactionQuantity();

      transactions.add(transactionData);
    }

    data.setTotalPrice(totalPrice);
    data.setTransactions(transactions);

    return data;
  }

  @Override
  public TransactionLossesData getTransactionLossesByQuarterAndYear(String quarter, int year) {
    LocalDate firstDay = QuarterDateConverter.getQuarterFirstDay(quarter, year);
    if (firstDay == null) {
      return null;
    }
    LocalDate lastDay = QuarterDateConverter.getQuarterLastDay(quarter, year);
    if (lastDay == null) {
      return null;
    }

    TransactionLossesData data = new TransactionLossesData();

    List<Object[]> queryResult = transactionRepository
        .getTransactionLossesByQuarterAndYearGroupedByProducts(firstDay.toString(),
            lastDay.toString());

    Float totalLosses = 0F;
    List<ProductLossData> productLosses = new ArrayList<>();

    for (Object[] row : queryResult) {
      Float loss = ((BigDecimal) row[0]).floatValue();
      if (loss <= 0) {
        continue;
      }

      ProductLossData productLoss = data.new ProductLossData();
      productLoss.setProductLoss(loss);
      Product p = new Product();
      p.setTarget(new Species((String) row[1]));
      p.setSource(new Species((String) row[2]));
      productLoss.setProductName(p.getProductName());

      productLosses.add(productLoss);
      totalLosses += productLoss.getProductLoss();
    }

    data.setProductLosses(productLosses);
    data.setTotalLosses(totalLosses);

    return data;
  }

}
