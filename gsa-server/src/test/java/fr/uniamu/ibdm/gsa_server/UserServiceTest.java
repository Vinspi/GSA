package fr.uniamu.ibdm.gsa_server;

import fr.uniamu.ibdm.gsa_server.dao.MemberRepository;
import fr.uniamu.ibdm.gsa_server.dao.ProductRepository;
import fr.uniamu.ibdm.gsa_server.dao.TeamRepository;
import fr.uniamu.ibdm.gsa_server.dao.UserRepository;
import fr.uniamu.ibdm.gsa_server.models.Aliquot;
import fr.uniamu.ibdm.gsa_server.models.Product;
import fr.uniamu.ibdm.gsa_server.models.Species;
import fr.uniamu.ibdm.gsa_server.models.Team;
import fr.uniamu.ibdm.gsa_server.models.User;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.ProductOverviewData;
import fr.uniamu.ibdm.gsa_server.services.impl.UserServiceImpl;
import fr.uniamu.ibdm.gsa_server.util.Crypto;
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

import java.lang.reflect.Array;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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
  public void getAllOverviewProduct(){

    List<Product> products = new ArrayList<>();
    Collection<Aliquot> aliquots = new ArrayList<>();

    for (int i=0;i<5;i++){
      ((ArrayList<Aliquot>) aliquots).add(new Aliquot(8,8));
    }

    products.add(new Product(new Species("MoNkEy"),new Species("Cat"),aliquots));

    Mockito.when(productRepository.findAll()).thenReturn(products);

    List<ProductOverviewData> productOverviewDataList = userService.getAllOverviewProducts();

    Assert.assertNotNull(productOverviewDataList);
    Assert.assertEquals(productOverviewDataList.size(),1);
    Assert.assertEquals(productOverviewDataList.get(0).getQuantity(),40);
    Assert.assertEquals(productOverviewDataList.get(0).getProductName(), "CAT_ANTI_MONKEY");

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
