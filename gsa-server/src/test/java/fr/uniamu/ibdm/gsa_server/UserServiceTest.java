package fr.uniamu.ibdm.gsa_server;

import fr.uniamu.ibdm.gsa_server.dao.AliquotRepository;
import fr.uniamu.ibdm.gsa_server.dao.MemberRepository;
import fr.uniamu.ibdm.gsa_server.dao.ProductRepository;
import fr.uniamu.ibdm.gsa_server.dao.TeamRepository;
import fr.uniamu.ibdm.gsa_server.dao.TransactionRepository;
import fr.uniamu.ibdm.gsa_server.dao.UserRepository;
import fr.uniamu.ibdm.gsa_server.models.Aliquot;
import fr.uniamu.ibdm.gsa_server.models.Member;
import fr.uniamu.ibdm.gsa_server.models.Product;
import fr.uniamu.ibdm.gsa_server.models.Species;
import fr.uniamu.ibdm.gsa_server.models.Team;
import fr.uniamu.ibdm.gsa_server.models.Transaction;
import fr.uniamu.ibdm.gsa_server.models.User;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.ProductOverviewData;
import fr.uniamu.ibdm.gsa_server.requests.forms.WithdrowForm;
import fr.uniamu.ibdm.gsa_server.services.impl.UserServiceImpl;
import fr.uniamu.ibdm.gsa_server.util.Crypto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Array;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

  @MockBean
  UserRepository userRepository;

  @MockBean
  TeamRepository teamRepository;

  @MockBean
  ProductRepository productRepository;

  @MockBean
  MemberRepository memberRepository;

  @MockBean
  AliquotRepository aliquotRepository;

  @MockBean
  TransactionRepository transactionRepository;

  @InjectMocks
  UserServiceImpl userService;

  @MockBean
  Team team;

  @MockBean
  User user;

  @Before
  public void initMocks() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void registerAccount() {
    Mockito.when(teamRepository.findByTeamName("team that exists")).thenReturn(team);
    Mockito.when(teamRepository.findByTeamName("team that does not exists")).thenReturn(null);
    Mockito.when(userRepository.findByUserEmail("already.exists@email.com")).thenReturn(user);
    Mockito.when(userRepository.save(Mockito.any(User.class))).thenAnswer(i -> {
      return i.getArgument(0);
    });

    User user = userService.registerAccount("Bruce", "bruce.wayne@batcave.com", "i'mbatman", "team that exists", false);

    Assert.assertNotNull(user);
    Assert.assertSame(user.getUserName(), "Bruce");
    Assert.assertSame(user.getUserEmail(), "bruce.wayne@batcave.com");

    byte[] passHashed = Crypto.hashPassword(user.getSalt(), "i'mbatman".getBytes());

    Assert.assertTrue(Arrays.equals(passHashed, user.getUserPassword()));

    user = userService.registerAccount("Bruce", "bruce.wayne@batcave.com", "i'mbatman", "team that does not exists", false);

    Assert.assertNull(user);

    user = userService.registerAccount("Bruce", "already.exists@email.com", "i'mbatman", "team that does not exists", false);

    Assert.assertNull(user);

  }

  @Test
  public void login() {
    Mockito.when(userRepository.findByUserEmail("bruce.wayne@batcave.com")).thenReturn(fakeUser());
    Mockito.when(userRepository.findByUserEmail("nonexistant@void.com")).thenReturn(null);

    User user = userService.login("bruce.wayne@batcave.com", "i'mbatman");

    Assert.assertNull(user);

    user = userService.login("bruce.wayne@batcave.com", "pantoufle");

    Assert.assertNotNull(user);

    user = userService.login("nonexistant@void.com", "pantoufle");

    Assert.assertNull(user);


  }

  @Test
  public void getAllOverviewProduct() {

    List<Product> products = new ArrayList<>();
    Collection<Aliquot> aliquots = new ArrayList<>();

    for (int i = 0; i < 5; i++) {
      ((ArrayList<Aliquot>) aliquots).add(new Aliquot(8, 8));
    }

    products.add(new Product(new Species("MoNkEy"), new Species("Cat"), aliquots));

    Mockito.when(productRepository.findAll()).thenReturn(products);

    List<ProductOverviewData> productOverviewDataList = userService.getAllOverviewProducts();

    Assert.assertNotNull(productOverviewDataList);
    Assert.assertEquals(productOverviewDataList.size(), 1);
    Assert.assertEquals(productOverviewDataList.get(0).getQuantity(), 40);
    Assert.assertEquals(productOverviewDataList.get(0).getProductName(), "CAT_ANTI_MONKEY");

  }

  @Test
  public void getProductNameFromAliquotNlot() {

    Aliquot aliquot = new Aliquot();
    Product product = new Product();
    product.setSource(new Species("MONKEY"));
    product.setTarget(new Species("GOAT"));
    aliquot.setProduct(product);

    Mockito.when(aliquotRepository.findById(1592L)).thenReturn(Optional.of(aliquot));

    String productName = userService.getProductNameFromNlot(1592L);

    Assert.assertNotNull(productName);
    Assert.assertEquals("MONKEY_ANTI_GOAT", productName);

    productName = userService.getProductNameFromNlot(1515L);

    Assert.assertNull(productName);

  }

  @Test
  public void withdrawCart() {

    Aliquot aliquot = new Aliquot();

    Mockito.when(aliquotRepository.findById(0L)).thenReturn(Optional.of(aliquot));
    Mockito.when(aliquotRepository.findById(1L)).thenReturn(Optional.of(aliquot));
    Mockito.when(aliquotRepository.findById(2L)).thenReturn(Optional.of(aliquot));

    ArgumentCaptor<Transaction> captor = ArgumentCaptor.forClass(Transaction.class);

    Collection<Member> collectionMember = new ArrayList<>();

    User user = fakeUser();
    Member member = new Member();
    Team team = new Team();
    team.setTeamName("fake team");
    member.setTeam(team);
    member.setUser(user);
    collectionMember.add(member);
    user.setMembers(collectionMember);

    List<WithdrowForm> cart = new ArrayList<>();

    for (long i = 0; i < 5; i++) {
      cart.add(new WithdrowForm(i, 2, "fake team"));
    }

    boolean response = userService.withdrawCart(cart, user);

    Mockito.verify(transactionRepository, Mockito.times(3)).save(captor.capture());
    Assert.assertFalse(response);
    Transaction transactionCaptured1 = captor.getValue();

    Assert.assertEquals(transactionCaptured1.getMember().getTeam(), team);
    Assert.assertEquals(transactionCaptured1.getMember().getUser(), user);
    Assert.assertEquals(transactionCaptured1.getMember(), member);
    Assert.assertEquals(transactionCaptured1.getAliquot(), aliquot);

    Mockito.reset(transactionRepository);
    cart = new ArrayList<>();

    for (long i = 0; i < 3; i++) {
      cart.add(new WithdrowForm(i, 2, "fake team"));
    }

    response = userService.withdrawCart(cart, user);

    Mockito.verify(transactionRepository, Mockito.times(3)).save(captor.capture());
    Assert.assertTrue(response);

    Transaction transactionCaptured2 = captor.getValue();

    Assert.assertEquals(transactionCaptured2.getMember().getTeam(), team);
    Assert.assertEquals(transactionCaptured2.getMember().getUser(), user);
    Assert.assertEquals(transactionCaptured2.getMember(), member);
    Assert.assertEquals(transactionCaptured2.getAliquot(), aliquot);

    Mockito.reset(transactionRepository);
    cart = new ArrayList<>();

    response = userService.withdrawCart(cart, user);

    Mockito.verify(transactionRepository, Mockito.never()).save(Mockito.any());
    Assert.assertTrue(response);

  }

  @Test
  public void getAllProductName() {

    List<Product> products = new ArrayList<>();

    for (int i=0;i<10;i++){
      products.add(new Product(new Species("target"+i), new Species("source"+i), null));
    }

    Mockito.when(productRepository.findAll()).thenReturn(products);

    List<String> names = userService.getAllProductName();

    for (int i=0;i<10;i++){
      Assert.assertTrue(names.contains("SOURCE"+i+"_ANTI_"+"TARGET"+i));
    }

  }

  @Test
  public void getAllTeamName() {


    List<Team> teams = new ArrayList<>();

    for (int i=0;i<10;i++){
      teams.add(new Team("fake team"+i, null, null));
    }

    Mockito.when(teamRepository.findAll()).thenReturn(teams);

    List<String> names = userService.getAllTeamName();

    for (int i=0;i<10;i++){
      Assert.assertTrue(names.contains("fake team"+i));
    }

  }

  private User fakeUser() {
    User user = new User();

    byte[] seed = SecureRandom.getSeed(128);

    byte[] passHash = Crypto.hashPassword(seed, "pantoufle".getBytes());

    user.setSalt(seed);
    user.setUserPassword(passHash);
    user.setUserEmail("bruce.wayne@batcave.com");
    user.setUserName("Bruce");
    return user;
  }

}
