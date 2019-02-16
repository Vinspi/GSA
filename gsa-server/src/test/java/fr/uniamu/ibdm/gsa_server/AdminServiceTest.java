package fr.uniamu.ibdm.gsa_server;

import fr.uniamu.ibdm.gsa_server.dao.ProductRepository;
import fr.uniamu.ibdm.gsa_server.dao.QueryObjects.StatsWithdrawQuery;
import fr.uniamu.ibdm.gsa_server.requests.forms.WithdrawStatsForm;
import fr.uniamu.ibdm.gsa_server.services.impl.AdminServiceImpl;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminServiceTest {

  @MockBean
  ProductRepository productRepository;

  @InjectMocks
  AdminServiceImpl adminService;

  @Before
  public void initMocks(){
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void getWithdrawsStats(){

    List<Object[]> returnQuery = new ArrayList<>();
    Object[] o;


    for (int i=0;i<10;i++) {
      o = new Object[3];
      o[0] = i+1;
      o[1] = 2019;
      o[2] = new BigDecimal(12);
      if((i+1)%2 == 0)
        returnQuery.add(o);
    }


    Mockito.when(productRepository.getWithdrawStats(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(returnQuery);
    WithdrawStatsForm form = new WithdrawStatsForm("fake team", "chicken_anti_donkey","april","may",2019,2019);

    List<StatsWithdrawQuery> list = adminService.getWithdrawStats(form);

    Assert.assertNotNull(list);
    Assert.assertEquals(list.size(), 9);


    for (int i=0;i<list.size();i++){
      System.out.println(list.get(i).getMonth()+" - "+list.get(i).getWithdraw());
      Assert.assertEquals(list.get(i).getMonth(),i+2);
      if ((i+1)%2 == 0)
        Assert.assertEquals(list.get(i).getWithdraw(),0);
      else
        Assert.assertEquals(list.get(i).getWithdraw(), 12);
    }

  }

}
