package fr.uniamu.ibdm.gsa_server;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import fr.uniamu.ibdm.gsa_server.dao.AlertRepository;
import fr.uniamu.ibdm.gsa_server.dao.AliquotRepository;
import fr.uniamu.ibdm.gsa_server.dao.ProductRepository;
import fr.uniamu.ibdm.gsa_server.dao.SpeciesRepository;
import fr.uniamu.ibdm.gsa_server.dao.TeamRepository;
import fr.uniamu.ibdm.gsa_server.dao.TeamTrimestrialReportRepository;
import fr.uniamu.ibdm.gsa_server.dao.TransactionRepository;
import fr.uniamu.ibdm.gsa_server.dao.QueryObjects.StatsWithdrawQuery;
import fr.uniamu.ibdm.gsa_server.dao.QueryObjects.TriggeredAlertsQuery;
import fr.uniamu.ibdm.gsa_server.models.Alert;
import fr.uniamu.ibdm.gsa_server.models.Aliquot;
import fr.uniamu.ibdm.gsa_server.models.Product;
import fr.uniamu.ibdm.gsa_server.models.Species;
import fr.uniamu.ibdm.gsa_server.models.Team;
import fr.uniamu.ibdm.gsa_server.models.TeamTrimestrialReport;
import fr.uniamu.ibdm.gsa_server.models.enumerations.AlertType;
import fr.uniamu.ibdm.gsa_server.models.enumerations.Quarter;
import fr.uniamu.ibdm.gsa_server.models.primarykeys.ProductPK;
import fr.uniamu.ibdm.gsa_server.models.primarykeys.TeamTrimestrialReportPk;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.AlertsData;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.TransactionReportData;
import fr.uniamu.ibdm.gsa_server.requests.forms.AddTeamTrimestrialReportForm;
import fr.uniamu.ibdm.gsa_server.requests.forms.UpdateAlertForm;
import fr.uniamu.ibdm.gsa_server.requests.forms.WithdrawStatsForm;
import fr.uniamu.ibdm.gsa_server.services.impl.AdminServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminServiceTest {

  @MockBean
  ProductRepository productRepository;

  @MockBean
  SpeciesRepository speciesRepository;

  @MockBean
  AlertRepository alertRepository;

  @MockBean
  AliquotRepository aliquotRepository;

  @MockBean
  TeamRepository teamRepository;

  @MockBean
  TeamTrimestrialReportRepository teamTrimestrialReportRepository;

  @MockBean
  TransactionRepository transactionRepository;

  @InjectMocks
  AdminServiceImpl adminService;

  @Before
  public void initMocks() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void getWithdrawsStats() {

    List<Object[]> returnQuery = new ArrayList<>();
    Object[] o;

    for (int i = 0; i < 10; i++) {
      o = new Object[3];
      o[0] = i + 1;
      o[1] = 2019;
      o[2] = new BigDecimal(12);
      if ((i + 1) % 2 == 0) {
        returnQuery.add(o);
      }
    }

    Mockito.when(productRepository.getWithdrawStats(Mockito.any(), Mockito.any(), Mockito.any(),
        Mockito.any(), Mockito.any())).thenReturn(returnQuery);
    WithdrawStatsForm form = new WithdrawStatsForm("fake team", "chicken_anti_donkey", "april",
        "may", 2019, 2019);

    List<StatsWithdrawQuery> list = adminService.getWithdrawStats(form);

    Assert.assertNotNull(list);
    Assert.assertEquals(list.size(), 9);

    for (int i = 0; i < list.size(); i++) {
      System.out.println(list.get(i).getMonth() + " - " + list.get(i).getWithdraw());
      Assert.assertEquals(list.get(i).getMonth(), i + 2);
      if ((i + 1) % 2 == 0) {
        Assert.assertEquals(list.get(i).getWithdraw(), 0);
      } else {
        Assert.assertEquals(list.get(i).getWithdraw(), 12);
      }
    }

  }

  @Test
  public void getAllSpeciesNames() {
    List<String> names = Arrays.asList("donkey", "chicken", "horse");
    Mockito.when(speciesRepository.getAllSpeciesNames()).thenReturn(names);
    Assert.assertEquals(adminService.getAllSpeciesNames(), names);

    Mockito.when(speciesRepository.getAllSpeciesNames()).thenReturn(null);
    Assert.assertEquals(adminService.getAllSpeciesNames(), null);
  }

  @Test
  public void addProduct() {
    String sourceName = "donkey";
    String targetName = "chicken";
    Species source = new Species(sourceName);
    Species target = new Species(targetName);
    Collection<Aliquot> aliquots = new ArrayList<>();
    final Product newProduct = new Product(target, source, aliquots);
    ProductPK productPk = new ProductPK();
    productPk.setSource(sourceName);
    productPk.setTarget(targetName);
    Optional<Species> nullableSourceSpecies = Optional.of(source);
    Optional<Species> nullableTargetSpecies = Optional.of(target);

    Mockito.when(speciesRepository.findById(sourceName)).thenReturn(nullableSourceSpecies);
    Mockito.when(speciesRepository.findById(targetName)).thenReturn(nullableTargetSpecies);
    Mockito.when(productRepository.findById(productPk)).thenReturn(Optional.empty());
    Mockito.when(productRepository.save(newProduct)).thenReturn(newProduct);
    Assert.assertEquals(true, adminService.addProduct(sourceName, targetName));

    Mockito.when(speciesRepository.findById(sourceName)).thenReturn(Optional.empty());
    Assert.assertEquals(false, adminService.addProduct(sourceName, targetName));

    Mockito.when(speciesRepository.findById(targetName)).thenReturn(Optional.empty());
    Assert.assertEquals(false, adminService.addProduct(sourceName, targetName));

    Mockito.when(speciesRepository.findById(sourceName)).thenReturn(nullableSourceSpecies);
    Mockito.when(speciesRepository.findById(targetName)).thenReturn(nullableTargetSpecies);
    Mockito.when(productRepository.findById(productPk)).thenReturn(Optional.of(newProduct));
    Assert.assertEquals(false, adminService.addProduct(sourceName, targetName));
  }

  @Test
  public void getAllAlerts() {

    List<Alert> alerts = new ArrayList<>();
    Alert tmp;

    for (int i = 0; i < 10; i++) {
      tmp = new Alert();
      tmp.setSeuil(10);
      tmp.setAlertId(i);
      tmp.setAlertType(AlertType.VISIBLE_STOCK);
      tmp.setProduct(new Product(new Species("MONKEY"), new Species("DONKEY"), null));
      alerts.add(tmp);
    }

    Mockito.when(alertRepository.findAll()).thenReturn(alerts);

    List<AlertsData> alertsData = adminService.getAllAlerts();

    Assert.assertEquals(10, alertsData.size());

    for (int i = 0; i < alertsData.size(); i++) {
      Assert.assertEquals(i, alertsData.get(i).getAlertId());
      Assert.assertEquals(10, alertsData.get(i).getSeuil());
      Assert.assertEquals(AlertType.VISIBLE_STOCK, alertsData.get(i).getAlertType());
      Assert.assertEquals("DONKEY_ANTI_MONKEY", alertsData.get(i).getProductName());
    }

  }

  @Test
  public void removeAlert() {

    Mockito.when(alertRepository.findById(1L)).thenReturn(Optional.of(new Alert()));

    boolean success = adminService.removeAlert(0);

    Assert.assertEquals(false, success);

    success = adminService.removeAlert(1);

    Assert.assertEquals(true, success);

  }

  @Test
  public void updateAlert() {

    Alert alert = new Alert();
    alert.setSeuil(10);

    UpdateAlertForm form = new UpdateAlertForm(1, 50);
    UpdateAlertForm formFail = new UpdateAlertForm(0, 50);

    Mockito.when(alertRepository.findById(1L)).thenReturn(Optional.of(alert));

    boolean success = adminService.updateAlertSeuil(formFail);

    Assert.assertEquals(false, success);

    success = adminService.updateAlertSeuil(form);

    Assert.assertEquals(true, success);

  }

  @Test
  public void getTriggeredAlerts() {

    Object[] queryVisible = new Object[6];
    Object[] queryHidden = new Object[6];
    Object[] queryGeneral = new Object[6];

    queryVisible[0] = "SOURCE";
    queryVisible[1] = "TARGET";
    queryVisible[2] = BigDecimal.valueOf(30);
    queryVisible[3] = 40;
    queryVisible[4] = "VISIBLE_STOCK";
    queryVisible[5] = BigInteger.valueOf(0);

    queryHidden[0] = "SOURCE";
    queryHidden[1] = "TARGET";
    queryHidden[2] = BigDecimal.valueOf(30);
    queryHidden[3] = 40;
    queryHidden[4] = "HIDDEN_STOCK";
    queryHidden[5] = BigInteger.valueOf(1);

    queryGeneral[0] = "SOURCE";
    queryGeneral[1] = "TARGET";
    queryGeneral[2] = BigDecimal.valueOf(30);
    queryGeneral[3] = 40;
    queryGeneral[4] = "GENERAL";
    queryGeneral[5] = BigInteger.valueOf(2);

    List<Object[]> listQueryVisible = new ArrayList<>();
    listQueryVisible.add(queryVisible);

    List<Object[]> listQueryHidden = new ArrayList<>();
    listQueryHidden.add(queryHidden);

    List<Object[]> listQueryGeneral = new ArrayList<>();
    listQueryGeneral.add(queryGeneral);

    Mockito.when(productRepository.getTriggeredAlertsVisible()).thenReturn(listQueryVisible);
    Mockito.when(productRepository.getTriggeredAlertsHidden()).thenReturn(listQueryHidden);
    Mockito.when(productRepository.getTriggeredAlertsGeneral()).thenReturn(listQueryGeneral);

    Object[] aliquotQuery = new Object[4];

    aliquotQuery[0] = BigInteger.valueOf(5);
    aliquotQuery[1] = new Timestamp(1254891);
    aliquotQuery[2] = BigInteger.valueOf(30);
    aliquotQuery[3] = BigInteger.valueOf(30);

    List<Object[]> listAliquotQuery = new ArrayList<>();

    listAliquotQuery.add(aliquotQuery);

    Mockito.when(aliquotRepository.findAllBySourceAndTargetQuery("SOURCE", "TARGET"))
        .thenReturn(listAliquotQuery);

    List<TriggeredAlertsQuery> triggeredAlertsQueries = adminService.getTriggeredAlerts();

    Assert.assertEquals(3, triggeredAlertsQueries.size());

    for (TriggeredAlertsQuery tr : triggeredAlertsQueries) {
      Assert.assertEquals(1, tr.getAliquots().size());
      Assert.assertEquals(triggeredAlertsQueries.indexOf(tr), tr.getAlertId());
      Assert.assertEquals(30, tr.getQte());
      Assert.assertTrue(40 == tr.getSeuil());
      Assert.assertEquals("SOURCE", tr.getSource());
      Assert.assertEquals("TARGET", tr.getTarget());

    }

  }

  @Test
  public void saveTeamTrimestrialReport() {
    AddTeamTrimestrialReportForm form = new AddTeamTrimestrialReportForm();
    form.setFinalFlag(false);
    form.setLosses(100F);
    form.setQuarter("QUARTER_1");
    form.setYear(2019);
    form.setTeamId(1L);

    Team team = new Team();
    team.setTeamId(1L);
    Mockito.when(teamRepository.findById(Mockito.eq(1L))).thenReturn(Optional.of(team));

    TeamTrimestrialReportPk teamTrimestrialReportPk = new TeamTrimestrialReportPk();
    teamTrimestrialReportPk.setQuarter(Quarter.QUARTER_1);
    teamTrimestrialReportPk.setTeam(1L);
    teamTrimestrialReportPk.setYear(2019);

    Mockito.when(teamTrimestrialReportRepository.findById(Mockito.eq(teamTrimestrialReportPk)))
        .thenReturn(Optional.empty());
    // Return type is ignored
    Mockito.when(teamTrimestrialReportRepository.save(Mockito.any())).thenReturn(null);

    // Saving a new report with a correct form should be added in the database.
    Boolean success = adminService.saveTeamTrimestrialReport(form);
    Assert.assertEquals(true, success);

    // Saving an existing report with a correct form should be updated in the database.
    TeamTrimestrialReport currentReport = new TeamTrimestrialReport();
    currentReport.setFinalFlag(false);

    Mockito.when(teamTrimestrialReportRepository.findById(Mockito.eq(teamTrimestrialReportPk)))
        .thenReturn(Optional.of(currentReport));
    success = adminService.saveTeamTrimestrialReport(form);
    Assert.assertEquals(true, success);

    // Saving a non-editable report should not be updated.
    currentReport.setFinalFlag(true);

    Mockito.when(teamTrimestrialReportRepository.findById(Mockito.eq(teamTrimestrialReportPk)))
        .thenReturn(Optional.of(currentReport));
    success = adminService.saveTeamTrimestrialReport(form);
    Assert.assertEquals(false, success);

    // Saving a report should fail when the specified quarter does not match the values of the
    // Quarter enumeration.
    Mockito.when(teamRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
    form.setQuarter("anyQuarter");
    success = adminService.saveTeamTrimestrialReport(form);
    Assert.assertEquals(false, success);

  }

  @Test
  public void getTransactionsByTeamAndQuarterAndYear() {
    List<TransactionReportData> reportTransactions = adminService
        .getTransactionsByTeamAndQuarterAndYear(null, Quarter.QUARTER_1.name(), 2019);
    Assert.assertNull(reportTransactions);
    
    reportTransactions = adminService
        .getTransactionsByTeamAndQuarterAndYear("Best team", null, 2019);
    Assert.assertNull(reportTransactions);

    reportTransactions = adminService
        .getTransactionsByTeamAndQuarterAndYear("Best team", "anyQuarter", 2019);
    Assert.assertNull(reportTransactions);
    
  }

}
