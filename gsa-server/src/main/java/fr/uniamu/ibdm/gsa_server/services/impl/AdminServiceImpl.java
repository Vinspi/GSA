package fr.uniamu.ibdm.gsa_server.services.impl;

import fr.uniamu.ibdm.gsa_server.dao.AlertRepository;
import fr.uniamu.ibdm.gsa_server.dao.AliquotRepository;
import fr.uniamu.ibdm.gsa_server.dao.ProductRepository;
import fr.uniamu.ibdm.gsa_server.dao.QueryObjects.AlertAliquot;
import fr.uniamu.ibdm.gsa_server.dao.QueryObjects.StatsWithdrawQuery;
import fr.uniamu.ibdm.gsa_server.dao.QueryObjects.TriggeredAlertsQuery;
import fr.uniamu.ibdm.gsa_server.dao.SpeciesRepository;
import fr.uniamu.ibdm.gsa_server.dao.TeamTrimestrialReportRepository;
import fr.uniamu.ibdm.gsa_server.dao.TransactionRepository;
import fr.uniamu.ibdm.gsa_server.models.Alert;
import fr.uniamu.ibdm.gsa_server.models.Aliquot;
import fr.uniamu.ibdm.gsa_server.models.Product;
import fr.uniamu.ibdm.gsa_server.models.Species;
import fr.uniamu.ibdm.gsa_server.models.TeamTrimestrialReport;
import fr.uniamu.ibdm.gsa_server.models.Transaction;
import fr.uniamu.ibdm.gsa_server.models.enumerations.AlertType;
import fr.uniamu.ibdm.gsa_server.models.enumerations.Quarter;
import fr.uniamu.ibdm.gsa_server.models.enumerations.StorageType;
import fr.uniamu.ibdm.gsa_server.models.enumerations.TransactionMotif;
import fr.uniamu.ibdm.gsa_server.models.enumerations.TransactionType;
import fr.uniamu.ibdm.gsa_server.models.enumerations.TransactionType;
import fr.uniamu.ibdm.gsa_server.models.primarykeys.ProductPK;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.AlertsData;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.NextReportData;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.ProductsStatsData;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.ProvidersStatsData;
import fr.uniamu.ibdm.gsa_server.requests.forms.AddAlertForm;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.TransactionData;
import fr.uniamu.ibdm.gsa_server.requests.forms.AddAliquoteForm;
import fr.uniamu.ibdm.gsa_server.requests.forms.InventoryForm;
import fr.uniamu.ibdm.gsa_server.requests.forms.TransfertAliquotForm;
import fr.uniamu.ibdm.gsa_server.requests.forms.UpdateAlertForm;
import fr.uniamu.ibdm.gsa_server.requests.forms.WithdrawStatsForm;
import fr.uniamu.ibdm.gsa_server.services.AdminService;
import fr.uniamu.ibdm.gsa_server.util.DateConverter;
import fr.uniamu.ibdm.gsa_server.util.EnumConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

  private ProductRepository productRepository;
  private TransactionRepository transactionRepository;
  private AliquotRepository aliquotRepository;
  private AlertRepository alertRepository;
  private SpeciesRepository speciesRepository;
  private TeamTrimestrialReportRepository teamTrimestrialReportRepository;

  /**
   * Constructor for the AdminService.
   *
   * @param productRepository Autowired repository.
   * @param transactionRepository Autowired repository.
   * @param aliquotRepository Autowired repository.
   * @param speciesRepository Autowired repository.
   * @param alertRepository   Autowired repository.
   */
  @Autowired
  public AdminServiceImpl(ProductRepository productRepository, TransactionRepository transactionRepository, AliquotRepository aliquotRepository, SpeciesRepository speciesRepository, AlertRepository alertRepository, TeamTrimestrialReportRepository teamTrimestrialReportRepository) {
    this.productRepository = productRepository;
    this.transactionRepository = transactionRepository;
    this.aliquotRepository = aliquotRepository;
    this.speciesRepository = speciesRepository;
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
    public List<TransactionData> getWithdrawalsHistoryBetween(LocalDate begin, LocalDate end) {
        List<TransactionData> history = new ArrayList<>();

        transactionRepository.findAllByTransactionDateGreaterThanEqualAndTransactionDateLessThanEqualAndTransactionTypeLike(begin, end, TransactionType.WITHDRAW).forEach(elem ->
            history.add(new TransactionData(elem))
        );
         return history;
    }

    @Override
    public List<TransactionData> getWithdrawalsHistorySince(LocalDate begin) {
        List<TransactionData> history = new ArrayList<>();

        transactionRepository.findAllByTransactionDateGreaterThanEqualAndTransactionTypeLike(begin, TransactionType.WITHDRAW).forEach(elem ->
                history.add(new TransactionData(elem))
        );
        return history;
    }

    @Override
    public List<TransactionData> getWithdrawalsHistoryUpTo(LocalDate end) {
        List<TransactionData> history = new ArrayList<>();

        transactionRepository.findAllByTransactionDateLessThanEqualAndTransactionTypeLike(end, TransactionType.WITHDRAW).forEach(elem ->
                history.add(new TransactionData(elem))
        );
        return history;
    }

    @Override
    public List<TransactionData> getWithdrawalsHistory() {
        List<TransactionData> history = new ArrayList<>();

        transactionRepository.findAll().forEach(elem ->
            history.add(new TransactionData(elem))
        );

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
  public List<Product> getAllProductsWithAliquots() {

    return (List) productRepository.findAll();

  }

  @Override
  public void makeInventory(List<InventoryForm> forms) {

    forms.forEach(form -> {
      Optional<Aliquot> actualOne = aliquotRepository.findById(form.getAliquotNLot());
      if (actualOne.isPresent()) {
        /* if we got losses */
        long losses = form.getQuantity() - actualOne.get().getAliquotQuantityVisibleStock();
        if (losses < 0) {
          Transaction lossesTransaction = new Transaction(TransactionMotif.INVENTORY, TransactionType.WITHDRAW, LocalDate.now(), (int) -losses, actualOne.get(), null);
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
    Quarter quarter;
    if (LocalDate.of(year, Month.MARCH, 31).isAfter(LocalDate.now())) {
      quarter = Quarter.QUARTER_1;
    } else if (LocalDate.of(year, Month.JUNE, 30).isAfter(LocalDate.now())) {
      quarter = Quarter.QUARTER_2;
    } else if (LocalDate.of(year, Month.SEPTEMBER, 31).isAfter(LocalDate.now())) {
      quarter = Quarter.QUARTER_3;
    } else {
      quarter = Quarter.QUARTER_4;
    }

    /* step 3 : search for a report */
    List<TeamTrimestrialReport> listReports = new ArrayList<>();
    switch (quarter) {
      case QUARTER_1:
        daysUntilNextOne = ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.of(year, Month.MARCH, 31));
        listReports = teamTrimestrialReportRepository.findAllByYearAndQuarter(year - 1, Quarter.QUARTER_4);
        break;
      case QUARTER_2:
        daysUntilNextOne = ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.of(year, Month.JUNE, 30));
        listReports = teamTrimestrialReportRepository.findAllByYearAndQuarter(year, Quarter.QUARTER_1);
        break;
      case QUARTER_3:
        daysUntilNextOne = ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.of(year, Month.SEPTEMBER, 31));
        listReports = teamTrimestrialReportRepository.findAllByYearAndQuarter(year, Quarter.QUARTER_2);
        break;
      case QUARTER_4:
        daysUntilNextOne = ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.of(year, Month.DECEMBER, 31));
        listReports = teamTrimestrialReportRepository.findAllByYearAndQuarter(year, Quarter.QUARTER_3);
        break;
      default:
        listReports = teamTrimestrialReportRepository.findAllByYearAndQuarter(year, Quarter.QUARTER_3);
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
        return aliquot.getAliquotExpirationDate().isAfter(LocalDate.now()) || (aliquot.getAliquotQuantityVisibleStock()+aliquot.getAliquotQuantityHiddenStock() == 0);
      });
    });

    return products;
  }

  @Override
  public boolean deleteOutdatedAliquot(Aliquot a) {

    Optional<Aliquot> aliquotOpt = aliquotRepository.findById(a.getAliquotNLot());

    if (aliquotOpt.isPresent() && aliquotOpt.get().getAliquotExpirationDate().isBefore(LocalDate.now())) {
      /* we add a OUTDATED transaction  */
      Transaction t = new Transaction(
          TransactionMotif.OUTDATED,
          TransactionType.WITHDRAW,
          LocalDate.now(),
          (int) (aliquotOpt.get().getAliquotQuantityHiddenStock()+aliquotOpt.get().getAliquotQuantityVisibleStock()),
          aliquotOpt.get(),
          null);
      transactionRepository.save(t);
      /* then we put all storage to 0 */
      aliquotOpt.get().setAliquotQuantityVisibleStock(0);
      aliquotOpt.get().setAliquotQuantityHiddenStock(0);
      aliquotRepository.save(aliquotOpt.get());
      return true;
    }

    return false;
  }
}
