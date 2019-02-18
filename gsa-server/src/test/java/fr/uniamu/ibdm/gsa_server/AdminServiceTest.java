package fr.uniamu.ibdm.gsa_server;

import java.math.BigDecimal;
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

import fr.uniamu.ibdm.gsa_server.dao.ProductRepository;
import fr.uniamu.ibdm.gsa_server.dao.SpeciesRepository;
import fr.uniamu.ibdm.gsa_server.dao.QueryObjects.StatsWithdrawQuery;
import fr.uniamu.ibdm.gsa_server.models.Aliquot;
import fr.uniamu.ibdm.gsa_server.models.Product;
import fr.uniamu.ibdm.gsa_server.models.Species;
import fr.uniamu.ibdm.gsa_server.models.primarykeys.ProductPK;
import fr.uniamu.ibdm.gsa_server.requests.forms.WithdrawStatsForm;
import fr.uniamu.ibdm.gsa_server.services.impl.AdminServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminServiceTest {

  @MockBean
  ProductRepository productRepository;

  @MockBean
  SpeciesRepository speciesRepository;

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

    Mockito.when(
        productRepository.getWithdrawStats(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
        .thenReturn(returnQuery);
    WithdrawStatsForm form = new WithdrawStatsForm("fake team", "chicken_anti_donkey", "april", "may", 2019, 2019);

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
  public void getAllSpeciesName() {
    List<String> names = Arrays.asList("donkey", "chicken", "horse");
    Mockito.when(speciesRepository.getAllSpeciesName()).thenReturn(names);
    Assert.assertEquals(adminService.getAllSpeciesName(), names);

    Mockito.when(speciesRepository.getAllSpeciesName()).thenReturn(null);
    Assert.assertEquals(adminService.getAllSpeciesName(), null);
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

}
