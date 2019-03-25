package fr.uniamu.ibdm.gsa_server.services.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.uniamu.ibdm.gsa_server.dao.AliquotRepository;
import fr.uniamu.ibdm.gsa_server.dao.MemberRepository;
import fr.uniamu.ibdm.gsa_server.dao.ProductRepository;
import fr.uniamu.ibdm.gsa_server.dao.TeamRepository;
import fr.uniamu.ibdm.gsa_server.dao.TeamTrimestrialReportRepository;
import fr.uniamu.ibdm.gsa_server.dao.TransactionRepository;
import fr.uniamu.ibdm.gsa_server.dao.UserRepository;
import fr.uniamu.ibdm.gsa_server.models.Aliquot;
import fr.uniamu.ibdm.gsa_server.models.Member;
import fr.uniamu.ibdm.gsa_server.models.Product;
import fr.uniamu.ibdm.gsa_server.models.Team;
import fr.uniamu.ibdm.gsa_server.models.Transaction;
import fr.uniamu.ibdm.gsa_server.models.User;
import fr.uniamu.ibdm.gsa_server.models.enumerations.Quarter;
import fr.uniamu.ibdm.gsa_server.models.enumerations.TransactionMotif;
import fr.uniamu.ibdm.gsa_server.models.enumerations.TransactionType;
import fr.uniamu.ibdm.gsa_server.models.primarykeys.ProductPK;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.ProductOverviewData;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.ReportData;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.TeamReportData;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.TransactionData;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.WithdrawnTransactionData;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.YearQuarterData;
import fr.uniamu.ibdm.gsa_server.requests.forms.WithdrowForm;
import fr.uniamu.ibdm.gsa_server.services.UserService;
import fr.uniamu.ibdm.gsa_server.util.Crypto;
import fr.uniamu.ibdm.gsa_server.util.QuarterDateConverter;

@Service
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;
  private TeamRepository teamRepository;
  private MemberRepository memberRepository;
  private ProductRepository productRepository;
  private AliquotRepository aliquotRepository;
  private TransactionRepository transactionRepository;
  private TeamTrimestrialReportRepository teamTrimestrialReportRepository;

  /**
   * Constructor for UserService.
   *
   * @param userRepository Dao for user entities.
   * @param teamRepository Dao for team entities.
   * @param memberRepository Dao for member entities.
   */
  @Autowired
  public UserServiceImpl(UserRepository userRepository, TeamRepository teamRepository,
      MemberRepository memberRepository, ProductRepository productRepository,
      AliquotRepository aliquotRepository, TransactionRepository transactionRepository,
      TeamTrimestrialReportRepository teamTrimestrialReportRepository) {
    this.userRepository = userRepository;
    this.teamRepository = teamRepository;
    this.memberRepository = memberRepository;
    this.productRepository = productRepository;
    this.aliquotRepository = aliquotRepository;
    this.transactionRepository = transactionRepository;
    this.teamTrimestrialReportRepository = teamTrimestrialReportRepository;
  }

  @Override
  public User registerAccount(String name, String email, String password, String teamName,
      boolean isAdmin) {

    Team team = teamRepository.findByTeamName(teamName);
    User u = userRepository.findByUserEmail(email);

    if (team == null || u != null) {
      /* the team does not exists or the email is already used */
      return null;
    }

    byte[] seed = new byte[128];
    new Random().nextBytes(seed);
    byte[] passwordHash = Crypto.hashPassword(seed, password.getBytes());

    User user = new User();
    user.setUserPassword(passwordHash);
    user.setSalt(seed);
    user.setUserName(name);
    user.setUserEmail(email);
    user.setAdmin(isAdmin);

    user = userRepository.save(user);

    Member member = new Member();
    member.setBegin(LocalDate.now());
    member.setTeam(team);
    member.setUser(user);
    memberRepository.save(member);

    return user;
  }

  @Override
  public User login(String email, String password) {

    User user = userRepository.findByUserEmail(email);

    if (user == null) {
      return null;
    }

    byte[] passHash = Crypto.hashPassword(user.getSalt(), password.getBytes());

    if (Arrays.equals(passHash, user.getUserPassword())) {
      return user;
    } else {
      return null;
    }
  }

  @Override
  public List<ProductOverviewData> getAllOverviewProducts() {

    List<ProductOverviewData> data = new ArrayList<>();
    List<Product> products = (List) productRepository.findAll();
    Collection<Aliquot> aliquots;
    int sum;

    for (Product p : products) {
      aliquots = p.getAliquots();
      sum = 0;
      for (Aliquot a : aliquots) {
        sum += a.getAliquotQuantityVisibleStock();
      }
      data.add(new ProductOverviewData(p.getProductName(), sum));
    }

    return data;
  }

  @Override
  public String getProductNameFromNlot(Long nlot) {

    Optional<Aliquot> aliquotOpt = aliquotRepository.findById(nlot);

    if (aliquotOpt.isPresent()) {
      return aliquotOpt.get().getProduct().getProductName();
    } else {
      return null;
    }
  }

  @Override
  public boolean withdrawCart(List<WithdrowForm> cart, User user) {

    Optional<Aliquot> aliquotOpt;
    Aliquot aliquot;
    boolean returnValue = true;
    int withdrowQuantity;
    Transaction transaction;
    Member member;

    for (WithdrowForm wf : cart) {
      aliquotOpt = aliquotRepository.findById(wf.getNlot());
      /* we verify that the aliquot exists */
      if (aliquotOpt.isPresent()) {
        aliquot = aliquotOpt.get();
        withdrowQuantity = aliquot.withdrawFromVisibleStock(wf.getQuantity());
        /* we choose a member in which name perform the withdraw */
        member = null;
        for (Member m : user.getMembers()) {
          if (m.getTeam().getTeamName().equals(wf.getTeamName())) {
            member = m;
          }
        }

        if (member != null) {
          transaction = new Transaction(TransactionMotif.TEAM_WITHDRAW, TransactionType.WITHDRAW,
              LocalDate.now(), withdrowQuantity, aliquot, member);
          transactionRepository.save(transaction);
          aliquotRepository.save(aliquot);
        } else {
          returnValue = false;
        }
      } else {
        returnValue = false;
      }
    }

    return returnValue;
  }

  @Override
  public List<String> getAllProductName() {

    List<String> productNames = new ArrayList<>();

    productRepository.findAll().forEach(element -> {
      productNames.add(element.getProductName());
    });

    return productNames;
  }

  @Override
  public List<String> getAllTeamName() {

    List<String> teamNames = new ArrayList<>();

    teamRepository.findAll().forEach(element -> {
      teamNames.add(element.getTeamName());
    });

    return teamNames;
  }

  @Override
  public List<TransactionData> getUserWithdrawalsHistoryBetween(long userId, LocalDate begin,
      LocalDate end) {
    List<TransactionData> history = new ArrayList<>();

    User user = userRepository.findById(userId).get();
    Member member = memberRepository.findByUser(user);

    transactionRepository
        .findAllByMemberAndTransactionDateGreaterThanEqualAndTransactionDateLessThanEqualAndTransactionMotifLike(
            member, begin, end, TransactionMotif.TEAM_WITHDRAW)
        .forEach(elem -> history.add(new TransactionData(elem)));
    return history;
  }

  @Override
  public List<TransactionData> getUserWithdrawalsHistorySince(long userId, LocalDate begin) {
    List<TransactionData> history = new ArrayList<>();

    User user = userRepository.findById(userId).get();
    Member member = memberRepository.findByUser(user);

    transactionRepository
        .findAllByMemberAndTransactionDateGreaterThanEqualAndTransactionMotifLike(member, begin,
            TransactionMotif.TEAM_WITHDRAW)
        .forEach(elem -> history.add(new TransactionData(elem)));
    return history;
  }

  @Override
  public List<TransactionData> getUserWithdrawalsHistoryUpTo(long userId, LocalDate end) {
    List<TransactionData> history = new ArrayList<>();

    User user = userRepository.findById(userId).get();
    Member member = memberRepository.findByUser(user);

    transactionRepository
        .findAllByMemberAndTransactionDateLessThanEqualAndTransactionMotifLike(member, end,
            TransactionMotif.TEAM_WITHDRAW)
        .forEach(elem -> history.add(new TransactionData(elem)));
    return history;
  }

  @Override
  public List<TransactionData> getUserWithdrawalsHistory(long userId) {
    List<TransactionData> history = new ArrayList<>();

    User user = userRepository.findById(userId).get();
    Member member = memberRepository.findByUser(user);

    transactionRepository.findAllByMember(member)
        .forEach(elem -> history.add(new TransactionData(elem)));

    return history;
  }

  @Override
  public List<TeamReportData> getUserTeamReports(long userId) {
    User user;
    Optional<User> nullableUser = userRepository.findById(userId);

    if (nullableUser.isPresent()) {
      user = nullableUser.get();
    } else {
      return null;
    }

    List<TeamReportData> userTeamReports = new ArrayList<>();

    if (user.getMembers().size() == 0) {
      return userTeamReports;
    }

    // Fetching all user's past and current team reports

    for (Member member : user.getMembers()) {
      TeamReportData teamReport = new TeamReportData();
      teamReport.setTeamName(member.getTeam().getTeamName());

      List<YearQuarterData> userTeamValidatedQuarters = new ArrayList<>();
      List<ReportData> reports = new ArrayList<>();

      // Fetching team validated quarters
      List<Object[]> resultQuery = teamTrimestrialReportRepository
          .findQuarterAndYearOfTeamNonEditableReports(member.getTeam().getTeamId());
      for (Object[] row : resultQuery) {
        String quarter = (String) row[0];
        Integer year = (Integer) row[1];

        LocalDate firstQuarterDay = QuarterDateConverter
            .getQuarterFirstDay(Quarter.valueOf(quarter), year);
        LocalDate lastQuarterDay = QuarterDateConverter.getQuarterLastDay(Quarter.valueOf(quarter),
            year);
        LocalDate firstMembershipDay = member.getBegin();
        LocalDate lastMembershipDay = member.getEnd();

        if (firstMembershipDay.isAfter(lastQuarterDay)) {
          continue;
        }

        if (lastMembershipDay != null) {
          if (firstMembershipDay.isAfter(lastMembershipDay)
              || firstQuarterDay.isAfter(lastMembershipDay)) {
            continue;
          }
        }

        ReportData report = new ReportData();
        BigDecimal teamWithdrawalCost = BigDecimal.ZERO;

        // Fetching quarterly team transactions
        resultQuery = transactionRepository.getWithdrawnTransactionsByTeamNameAndQuarter(
            member.getTeam().getTeamName(), firstQuarterDay.toString(), lastQuarterDay.toString());

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

        report.setWithdrawnTransactions(transactions);
        report.setQuarter(quarter);
        report.setYear(year);
        report.setTeamLoss(null);
        report.setTeamWithdrawalCost(teamWithdrawalCost);
        reports.add(report);
      }
      userTeamReports.add(new TeamReportData(reports, member.getTeam().getTeamName()));
    }

    return userTeamReports;

  }

}
