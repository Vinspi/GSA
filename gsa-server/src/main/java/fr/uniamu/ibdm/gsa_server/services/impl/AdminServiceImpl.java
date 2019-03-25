package fr.uniamu.ibdm.gsa_server.services.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import fr.uniamu.ibdm.gsa_server.dao.AlertRepository;
import fr.uniamu.ibdm.gsa_server.dao.AliquotRepository;
import fr.uniamu.ibdm.gsa_server.dao.ProductRepository;
import fr.uniamu.ibdm.gsa_server.dao.SpeciesRepository;
import fr.uniamu.ibdm.gsa_server.dao.TeamRepository;
import fr.uniamu.ibdm.gsa_server.dao.TeamTrimestrialReportRepository;
import fr.uniamu.ibdm.gsa_server.dao.TransactionRepository;
import fr.uniamu.ibdm.gsa_server.dao.QueryObjects.AlertAliquot;
import fr.uniamu.ibdm.gsa_server.dao.QueryObjects.StatsWithdrawQuery;
import fr.uniamu.ibdm.gsa_server.dao.QueryObjects.TriggeredAlertsQuery;
import fr.uniamu.ibdm.gsa_server.models.Alert;
import fr.uniamu.ibdm.gsa_server.models.Aliquot;
import fr.uniamu.ibdm.gsa_server.models.Member;
import fr.uniamu.ibdm.gsa_server.models.Product;
import fr.uniamu.ibdm.gsa_server.models.Species;
import fr.uniamu.ibdm.gsa_server.models.Team;
import fr.uniamu.ibdm.gsa_server.models.TeamTrimestrialReport;
import fr.uniamu.ibdm.gsa_server.models.Transaction;
import fr.uniamu.ibdm.gsa_server.models.User;
import fr.uniamu.ibdm.gsa_server.models.enumerations.AlertType;
import fr.uniamu.ibdm.gsa_server.models.enumerations.Quarter;
import fr.uniamu.ibdm.gsa_server.models.enumerations.StorageType;
import fr.uniamu.ibdm.gsa_server.models.enumerations.TransactionMotif;
import fr.uniamu.ibdm.gsa_server.models.enumerations.TransactionType;
import fr.uniamu.ibdm.gsa_server.models.primarykeys.ProductPK;
import fr.uniamu.ibdm.gsa_server.models.primarykeys.TeamTrimestrialReportPk;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.AlertsData;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.NextReportData;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.ProductsStatsData;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.ProvidersStatsData;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.ReportData;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.TeamReportData;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.TransactionData;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.TransactionLossesData;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.TransactionLossesData.ProductLossData;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.WithdrawnTransactionData;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.YearQuarterData;
import fr.uniamu.ibdm.gsa_server.requests.forms.AddAlertForm;
import fr.uniamu.ibdm.gsa_server.requests.forms.AddAliquoteForm;
import fr.uniamu.ibdm.gsa_server.requests.forms.InventoryForm;
import fr.uniamu.ibdm.gsa_server.requests.forms.TeamReportLossForm;
import fr.uniamu.ibdm.gsa_server.requests.forms.TransfertAliquotForm;
import fr.uniamu.ibdm.gsa_server.requests.forms.UpdateAlertForm;
import fr.uniamu.ibdm.gsa_server.requests.forms.WithdrawStatsForm;
import fr.uniamu.ibdm.gsa_server.services.AdminService;
import fr.uniamu.ibdm.gsa_server.util.DateConverter;
import fr.uniamu.ibdm.gsa_server.util.EnumConvertor;
import fr.uniamu.ibdm.gsa_server.util.QuarterDateConverter;
import fr.uniamu.ibdm.gsa_server.util.TimeFactory;

@Service
public class AdminServiceImpl implements AdminService {

  @Autowired
  private TimeFactory clock;

  private ProductRepository productRepository;
  private TransactionRepository transactionRepository;
  private AliquotRepository aliquotRepository;
  private AlertRepository alertRepository;
  private SpeciesRepository speciesRepository;
  private TeamRepository teamRepository;
  private TeamTrimestrialReportRepository teamTrimestrialReportRepository;

  /**
   * Constructor for the AdminService.
   * 
   * @param productRepository Autowired repository.
   * @param aliquotRepository Autowired repository.
   * @param speciesRepository Autowired repository.
   * @param alertRepository Autowired repository.
   * @param teamRepository Autowired repository.
   * @param teamTrimestrialReportRepository Autowired repository.
   * @param transactionRepository Autowired repository.
   */
  @Autowired
  public AdminServiceImpl(ProductRepository productRepository, AliquotRepository aliquotRepository,
      SpeciesRepository speciesRepository, AlertRepository alertRepository,
      TransactionRepository transactionRepository,
      TeamTrimestrialReportRepository teamTrimestrialReportRepository,
      TeamRepository teamRepository) {
    this.productRepository = productRepository;
    this.transactionRepository = transactionRepository;
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
  @Transactional(isolation = Isolation.SERIALIZABLE)
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
  public List<TransactionData> getWithdrawalsHistoryBetween(LocalDate begin, LocalDate end) {
    List<TransactionData> history = new ArrayList<>();

    transactionRepository
        .findAllByTransactionDateGreaterThanEqualAndTransactionDateLessThanEqualAndTransactionMotifLike(
            begin, end, TransactionMotif.TEAM_WITHDRAW)
        .forEach(elem -> history.add(new TransactionData(elem)));
    return history;
  }

  @Override
  public List<TransactionData> getWithdrawalsHistorySince(LocalDate begin) {
    List<TransactionData> history = new ArrayList<>();

    transactionRepository.findAllByTransactionDateGreaterThanEqualAndTransactionMotifLike(begin,
        TransactionMotif.TEAM_WITHDRAW).forEach(elem -> history.add(new TransactionData(elem)));
    return history;
  }

  @Override
  public List<TransactionData> getWithdrawalsHistoryUpTo(LocalDate end) {
    List<TransactionData> history = new ArrayList<>();

    transactionRepository.findAllByTransactionDateLessThanEqualAndTransactionMotifLike(end,
        TransactionMotif.TEAM_WITHDRAW).forEach(elem -> history.add(new TransactionData(elem)));
    return history;
  }

  @Override
  public List<TransactionData> getWithdrawalsHistory() {
    List<TransactionData> history = new ArrayList<>();

    transactionRepository.findAllByTransactionMotifLike(TransactionMotif.TEAM_WITHDRAW)
        .forEach(elem -> history.add(new TransactionData(elem)));

    return history;
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

      returnValue.add(
          new TriggeredAlertsQuery((String) o[0], (String) o[1], ((BigDecimal) o[2]).intValue(),
              (int) o[3], type, alertAliquots, ((BigInteger) o[5]).longValue()));

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
  @Transactional(isolation = Isolation.SERIALIZABLE)
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
  @Transactional(isolation = Isolation.SERIALIZABLE)
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
  @Transactional(isolation = Isolation.SERIALIZABLE)
  public boolean transfertAliquot(TransfertAliquotForm form) {

    Optional<Aliquot> aliquotOpt = aliquotRepository.findById(form.getNumLot());
    Aliquot aliquot;
    long transfertQuantity;

    if (aliquotOpt.isPresent()) {
      aliquot = aliquotOpt.get();
      if (form.getFrom() == StorageType.RESERVE) {
        transfertQuantity = form.getQuantity() >= aliquot.getAliquotQuantityHiddenStock()
            ? aliquot.getAliquotQuantityHiddenStock()
            : form.getQuantity();
        aliquot.setAliquotQuantityHiddenStock(
            aliquot.getAliquotQuantityHiddenStock() - transfertQuantity);
        aliquot.setAliquotQuantityVisibleStock(
            aliquot.getAliquotQuantityVisibleStock() + transfertQuantity);
      } else {
        transfertQuantity = form.getQuantity() >= aliquot.getAliquotQuantityVisibleStock()
            ? aliquot.getAliquotQuantityVisibleStock()
            : form.getQuantity();
        aliquot.setAliquotQuantityHiddenStock(
            aliquot.getAliquotQuantityHiddenStock() + transfertQuantity);
        aliquot.setAliquotQuantityVisibleStock(
            aliquot.getAliquotQuantityVisibleStock() - transfertQuantity);
      }
      aliquotRepository.save(aliquot);
      return true;
    } else {
      return false;
    }
  }

  @Override
  @Transactional(isolation = Isolation.SERIALIZABLE)
  public boolean addAlert(AddAlertForm form) {

    String[] shards = form.getProductName().split("_");
    Optional<Product> productOPt = productRepository.findById(new ProductPK(shards[2], shards[0]));
    Optional<Alert> alert;

    if (productOPt.isPresent()) {
      alert = alertRepository.findByAlertTypeAndProduct(
          EnumConvertor.storageTypeToAlertType(form.getStorageType()), productOPt.get());

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
  @Transactional(isolation = Isolation.SERIALIZABLE)
  public boolean saveTeamTrimestrialReport(Map<String, BigDecimal> teamReportLosses,
      boolean isValidated, int year, Quarter quarter) {

    // Checking that the quarter is over in order to save
    LocalDate now = clock.now();
    LocalDate lastDay = QuarterDateConverter.getQuarterLastDay(quarter, year);
    LocalDate firstDay = QuarterDateConverter.getQuarterFirstDay(quarter, year);
    if (!now.isAfter(lastDay)) {
      return false;
    }

    // Checking that the teams are valid
    List<Team> teams = new ArrayList<>();
    for (String teamName : teamReportLosses.keySet()) {
      Team team = teamRepository.findByTeamName(teamName);
      if (team == null) {
        return false;
      }
      teams.add(team);
    }

    BigDecimal dbTeamLoss = teamTrimestrialReportRepository.getSumOfQuarterLosses(quarter.name(),
        year);

    // If no teams are found in the report table, then initialise for this quarter's year.
    if (dbTeamLoss == null) {
      Iterable<Team> allTeams = teamRepository.findAll();
      for (Team team : allTeams) {
        TeamTrimestrialReport newReport = new TeamTrimestrialReport();
        newReport.setFinalFlag(false);
        newReport.setLosses(BigDecimal.ZERO);
        newReport.setQuarter(quarter);
        newReport.setYear(year);
        newReport.setTeam(team);
        teamTrimestrialReportRepository.save(newReport);
      }
      dbTeamLoss = BigDecimal.ZERO;
    }

    BigDecimal dbProductLoss = transactionRepository
        .getSumOfOutdatedAndLostProductOfQuarter(firstDay.toString(), lastDay.toString());

    // With no lost products, adding a value means that the losses will be negative
    BigDecimal sumTeamReportLosses = teamReportLosses.values().stream().reduce(BigDecimal.ZERO,
        BigDecimal::add);
    if (!(sumTeamReportLosses.compareTo(BigDecimal.ZERO) == 0) && dbProductLoss == null) {
      return false;
    }

    // Checking that the sum of new team losses is lesser than the loss of products during the
    // quarter
    if (dbProductLoss != null) {
      if (sumTeamReportLosses.compareTo(dbProductLoss) > 0) {
        return false;
      }

      if (isValidated && !(teamRepository.count() == teamReportLosses.size())) {
        return false;
      }

      // Checking that the sum of team losses equals the cost of losses of products during this
      // quarter
      if (isValidated && !(sumTeamReportLosses.compareTo(dbProductLoss) == 0)) {
        return false;
      }
    }

    List<TeamTrimestrialReport> teamTrimestrialReports = new ArrayList<>();

    for (Team team : teams) {
      TeamTrimestrialReportPk teamTrimestrialReportPk = new TeamTrimestrialReportPk();
      teamTrimestrialReportPk.setTeam(team.getTeamId());
      teamTrimestrialReportPk.setYear(year);
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
      teamTrimestrialReport.setFinalFlag(isValidated);
      teamTrimestrialReport.setLosses(teamReportLosses.get(team.getTeamName()));
      teamTrimestrialReport.setQuarter(quarter);
      teamTrimestrialReport.setTeam(team);
      teamTrimestrialReport.setYear(year);

      teamTrimestrialReports.add(teamTrimestrialReport);

    }

    teamTrimestrialReportRepository.saveAll(teamTrimestrialReports);

    return true;
  }

  @Override
  public List<WithdrawnTransactionData> getWithdrawnTransactionsByTeamNameAndQuarterAndYear(
      String teamName, Quarter quarter, int year) {
    if (teamName == null || quarter == null) {
      return null;
    }

    LocalDate firstDay = QuarterDateConverter.getQuarterFirstDay(quarter, year);
    LocalDate lastDay = QuarterDateConverter.getQuarterLastDay(quarter, year);

    List<Object[]> resultQuery = transactionRepository.getWithdrawnTransactionsByTeamNameAndQuarter(
        teamName, firstDay.toString(), lastDay.toString());

    List<WithdrawnTransactionData> transactions = new ArrayList<>();

    for (Object[] o : resultQuery) {

      WithdrawnTransactionData transactionData = new WithdrawnTransactionData();

      transactionData.setAliquotPrice((BigDecimal) o[0]);
      Timestamp date = (Timestamp) o[1];
      transactionData.setTransactionDate(date.toLocalDateTime().toLocalDate().toString());
      transactionData.setTransactionQuantity((Integer) o[2]);
      transactionData.setUserName((String) o[3]);
      String source = (String) o[4];
      String target = (String) o[5];
      ProductPK productPk = new ProductPK();
      productPk.setSource(source);
      productPk.setTarget(target);

      transactionData.setProductName(productRepository.findById(productPk).get().getProductName());

      transactions.add(transactionData);
    }

    return transactions;
  }

  @Override
  public TransactionLossesData getSumAndProductsOfOutdatedAndLostProductOfQuarter(Quarter quarter,
      int year) {
    if (quarter == null) {
      return null;
    }

    LocalDate firstDay = QuarterDateConverter.getQuarterFirstDay(quarter, year);
    LocalDate lastDay = QuarterDateConverter.getQuarterLastDay(quarter, year);

    TransactionLossesData data = new TransactionLossesData();

    List<Object[]> queryResult = transactionRepository
        .getSumAndProductsOfOutdatedAndLostProductOfQuarter(firstDay.toString(),
            lastDay.toString());

    if (queryResult == null) {
      return null;
    }

    BigDecimal totalLosses = BigDecimal.ZERO;
    List<ProductLossData> productLosses = new ArrayList<>();

    for (Object[] row : queryResult) {
      BigDecimal loss = (BigDecimal) row[0];

      ProductLossData productLoss = data.new ProductLossData();
      productLoss.setLoss(loss);
      Product p = new Product();
      p.setTarget(new Species((String) row[1]));
      p.setSource(new Species((String) row[2]));
      productLoss.setName(p.getProductName());

      productLosses.add(productLoss);
      totalLosses = totalLosses.add(loss);
    }

    data.setProductLosses(productLosses);
    data.setTotalLosses(totalLosses);

    return data;
  }

  @Override
  public List<YearQuarterData> getQuarterAndYearOfAllEditableReports() {

    List<YearQuarterData> editableQuarters = new ArrayList<>();
    List<YearQuarterData> nonEditableQuartersTemp = new ArrayList<>();
    List<YearQuarterData> nonEditableQuarters = new ArrayList<>();

    List<Object[]> resultQuery = teamTrimestrialReportRepository
        .findQuarterAndYearOfEditableReports();
    for (Object[] row : resultQuery) {
      String quarter = (String) row[0];
      Integer year = (Integer) row[1];
      editableQuarters.add(new YearQuarterData(quarter, year));
    }

    resultQuery = teamTrimestrialReportRepository.findQuarterAndYearOfNonEditableReports();
    for (Object[] row : resultQuery) {
      String quarter = (String) row[0];
      Integer year = (Integer) row[1];
      nonEditableQuartersTemp.add(new YearQuarterData(quarter, year));
    }

    for (YearQuarterData yearQuarter : nonEditableQuartersTemp) {
      if (!editableQuarters.contains(yearQuarter)) {
        nonEditableQuarters.add(yearQuarter);
      }
    }

    LocalDate now = clock.now();
    int year = now.getYear() - 1;
    Quarter[] quarters = Quarter.values();
    List<YearQuarterData> result = new ArrayList<>();

    for (int index = 0; index < quarters.length; index++) {
      YearQuarterData yearQuarter = new YearQuarterData(quarters[index].name(), year);
      if (!nonEditableQuarters.contains(yearQuarter)) {
        result.add(yearQuarter);
      }
    }

    year++;
    for (int index = 0; index < ((now.getMonthValue() - 1) / 3); index++) {
      YearQuarterData yearQuarter = new YearQuarterData(quarters[index].name(), year);
      if (!nonEditableQuarters.contains(yearQuarter)) {
        result.add(yearQuarter);
      }
    }

    return result;
  }

  @Override
  public List<TeamReportLossForm> getReportLossesAndTeamNameByYearAndQuarter(Quarter quarter,
      int year) {

    List<TeamReportLossForm> data = new ArrayList<>();

    List<TeamTrimestrialReport> reports = teamTrimestrialReportRepository
        .findAllByYearAndQuarter(year, quarter);
    for (TeamTrimestrialReport report : reports) {
      data.add(new TeamReportLossForm(report.getTeam().getTeamName(), report.getLosses()));
    }

    return data;
  }

  @Override
  public BigDecimal getRemainingReportLosses(Quarter quarter, Integer year) {
    BigDecimal sumOfReportLosses = (BigDecimal) teamTrimestrialReportRepository
        .getSumOfQuarterLosses(quarter.name(), year);

    LocalDate firstDay = QuarterDateConverter.getQuarterFirstDay(quarter, year);
    LocalDate lastDay = QuarterDateConverter.getQuarterLastDay(quarter, year);
    BigDecimal sumOfOutdatedAndLostProducts = (BigDecimal) transactionRepository
        .getSumOfOutdatedAndLostProductOfQuarter(firstDay.toString(), lastDay.toString());

    if (sumOfReportLosses == null) {
      return null;
    }

    if (sumOfOutdatedAndLostProducts == null) {
      return null;
    }

    return sumOfOutdatedAndLostProducts.subtract(sumOfReportLosses);
  }

  @Override
  public BigDecimal getSumOfCostOfAllWithdrawnProductsByQuarter(Quarter quarter, int year) {
    LocalDate firstDay = QuarterDateConverter.getQuarterFirstDay(quarter, year);
    LocalDate lastDay = QuarterDateConverter.getQuarterLastDay(quarter, year);
    return transactionRepository.getSumOfWithdrawnProductsOfQuarter(firstDay.toString(),
        lastDay.toString());
  }

  @Override
  public List<Product> getAllProductsWithAliquots() {

    return (List) productRepository.findAll();

  }

  @Override
  @Transactional(isolation = Isolation.SERIALIZABLE)
  public void makeInventory(List<InventoryForm> forms) {

    forms.forEach(form -> {
      Optional<Aliquot> actualOne = aliquotRepository.findById(form.getAliquotNLot());
      if (actualOne.isPresent()) {
        /* if we got losses */
        long losses = form.getQuantity() - actualOne.get().getAliquotQuantityVisibleStock();
        if (losses < 0) {
          Transaction lossesTransaction = new Transaction(TransactionMotif.INVENTORY,
              TransactionType.WITHDRAW, LocalDate.now(), (int) losses, actualOne.get(), null);
          transactionRepository.save(lossesTransaction);
        }
        actualOne.get().setAliquotQuantityVisibleStock(form.getQuantity());
        aliquotRepository.save(actualOne.get());
      }
    });
  }

  @Override
  public List<ProvidersStatsData> generateProvidersStats() {
    return aliquotRepository.generateProviderStats();
  }

  @Override
  public int getAlertsNotification() {

    List<Object[]> queryResult = productRepository.getTriggeredAlertsVisible();
    queryResult.addAll(productRepository.getTriggeredAlertsHidden());
    queryResult.addAll(productRepository.getTriggeredAlertsGeneral());

    return queryResult.size();
  }

  @Override
  public NextReportData getNextReportData() {

    /* see if we already have a report for this quarter */

    /* step 1 : determine the year */
    int year = LocalDate.now().getYear();
    long daysUntilNextOne = 0;

    /* step 2 : determine the quarter */
    Quarter quarter = QuarterDateConverter.getQuarterOfDate(LocalDate.now());

    /* step 3 : search for a report */
    List<TeamTrimestrialReport> listReports = new ArrayList<>();
    switch (quarter) {
    case QUARTER_1:
      daysUntilNextOne = ChronoUnit.DAYS.between(LocalDate.now(),
          LocalDate.of(year, Month.MARCH, 31));
      listReports = teamTrimestrialReportRepository.findAllByYearAndQuarter(year - 1,
          Quarter.QUARTER_4);
      break;
    case QUARTER_2:
      daysUntilNextOne = ChronoUnit.DAYS.between(LocalDate.now(),
          LocalDate.of(year, Month.JUNE, 30));
      listReports = teamTrimestrialReportRepository.findAllByYearAndQuarter(year,
          Quarter.QUARTER_1);
      break;
    case QUARTER_3:
      daysUntilNextOne = ChronoUnit.DAYS.between(LocalDate.now(),
          LocalDate.of(year, Month.SEPTEMBER, 31));
      listReports = teamTrimestrialReportRepository.findAllByYearAndQuarter(year,
          Quarter.QUARTER_2);
      break;
    case QUARTER_4:
      daysUntilNextOne = ChronoUnit.DAYS.between(LocalDate.now(),
          LocalDate.of(year, Month.DECEMBER, 31));
      listReports = teamTrimestrialReportRepository.findAllByYearAndQuarter(year,
          Quarter.QUARTER_3);
      break;
    default:
      listReports = teamTrimestrialReportRepository.findAllByYearAndQuarter(year,
          Quarter.QUARTER_3);
    }

    boolean todo = false;
    if (listReports.isEmpty()) {
      todo = true;
    }
    for (TeamTrimestrialReport report : listReports) {
      if (!report.isFinalFlag()) {
        todo = true;
        break;
      }
    }

    /* if we got one or more report to do */
    if (todo) {
      return new NextReportData(true, 0);
    } else {
      /* else, we count the number of days until the next one */
      return new NextReportData(false, daysUntilNextOne);
    }

  }

  @Override
  public List<ProductsStatsData> generateProductsStats() {
    return aliquotRepository.generateProductsStats();
  }

  @Override
  public List<Product> getAllOutdatedAliquot() {

    List<Product> products = productRepository.findAllOutdatedProduct();

    products.forEach(product -> {
      product.getAliquots().removeIf(aliquot -> {
        return aliquot.getAliquotExpirationDate().isAfter(LocalDate.now())
            || (aliquot.getAliquotQuantityVisibleStock()
                + aliquot.getAliquotQuantityHiddenStock() == 0);
      });
    });

    return products;
  }

  @Override
  @Transactional(isolation = Isolation.SERIALIZABLE)
  public boolean deleteOutdatedAliquot(Aliquot a) {

    Optional<Aliquot> aliquotOpt = aliquotRepository.findById(a.getAliquotNLot());

    if (aliquotOpt.isPresent()
        && aliquotOpt.get().getAliquotExpirationDate().isBefore(LocalDate.now())) {
      /* we add a OUTDATED transaction */
      Transaction t = new Transaction(TransactionMotif.OUTDATED, TransactionType.WITHDRAW,
          LocalDate.now(), (int) (aliquotOpt.get().getAliquotQuantityHiddenStock()
              + aliquotOpt.get().getAliquotQuantityVisibleStock()),
          aliquotOpt.get(), null);
      transactionRepository.save(t);
      /* then we put all storage to 0 */
      aliquotOpt.get().setAliquotQuantityVisibleStock(0);
      aliquotOpt.get().setAliquotQuantityHiddenStock(0);
      aliquotRepository.save(aliquotOpt.get());
      return true;
    }

    return false;
  }

  @Override
  public List<TeamReportData> getAllTeamReports() {

    List<TeamReportData> userTeamReports = new ArrayList<>();
    List<Team> teams = (List<Team>) teamRepository.findAll();

    for (Team team : teams) {
      TeamReportData teamReport = new TeamReportData();
      teamReport.setTeamName(team.getTeamName());

      List<YearQuarterData> userTeamValidatedQuarters = new ArrayList<>();
      List<ReportData> reports = new ArrayList<>();

      List<Object[]> resultQuery = teamTrimestrialReportRepository
          .findQuarterAndYearOfTeamNonEditableReports(team.getTeamId());
      for (Object[] row : resultQuery) {
        String quarter = (String) row[0];
        Integer year = (Integer) row[1];

        LocalDate firstQuarterDay = QuarterDateConverter
            .getQuarterFirstDay(Quarter.valueOf(quarter), year);
        LocalDate lastQuarterDay = QuarterDateConverter.getQuarterLastDay(Quarter.valueOf(quarter),
            year);

        ReportData report = new ReportData();
        BigDecimal teamWithdrawalCost = BigDecimal.ZERO;

        resultQuery = transactionRepository.getWithdrawnTransactionsByTeamNameAndQuarter(
            team.getTeamName(), firstQuarterDay.toString(), lastQuarterDay.toString());

        List<WithdrawnTransactionData> transactions = new ArrayList<>();

        for (Object[] o : resultQuery) {
          WithdrawnTransactionData transactionData = new WithdrawnTransactionData();

          transactionData.setAliquotPrice((BigDecimal) o[0]);
          Timestamp date = (Timestamp) o[1];
          transactionData.setTransactionDate(date.toLocalDateTime().toLocalDate().toString());
          transactionData.setTransactionQuantity((Integer) o[2]);
          transactionData.setUserName((String) o[3]);
          String source = (String) o[4];
          String target = (String) o[5];
          ProductPK productPk = new ProductPK();
          productPk.setSource(source);
          productPk.setTarget(target);
          transactionData
              .setProductName(productRepository.findById(productPk).get().getProductName());
          transactions.add(transactionData);
          BigDecimal transactionCost = transactionData.getAliquotPrice()
              .multiply(BigDecimal.valueOf(transactionData.getTransactionQuantity()));
          teamWithdrawalCost = teamWithdrawalCost.add(transactionCost);
        }

        BigDecimal teamLoss = teamTrimestrialReportRepository.getTeamValidatedReportLoss(quarter,
            year, team.getTeamId());

        report.setWithdrawnTransactions(transactions);
        report.setQuarter(quarter);
        report.setYear(year);
        report.setTeamLoss(teamLoss);
        report.setTeamWithdrawalCost(teamWithdrawalCost);
        reports.add(report);
      }
      userTeamReports.add(new TeamReportData(reports, team.getTeamName()));
    }

    return userTeamReports;

  }

}
