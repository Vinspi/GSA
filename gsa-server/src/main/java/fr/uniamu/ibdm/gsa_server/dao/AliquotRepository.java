package fr.uniamu.ibdm.gsa_server.dao;

import fr.uniamu.ibdm.gsa_server.models.Aliquot;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.ProductsStatsData;
import fr.uniamu.ibdm.gsa_server.requests.JsonData.ProvidersStatsData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AliquotRepository extends CrudRepository<Aliquot, Long> {

  @Query(value = "SELECT aliquotnlot, aliquot_expiration_date, aliquot_quantity_visible_stock, aliquot_quantity_hidden_stock FROM aliquot WHERE (source LIKE :source AND target LIKE :target)", nativeQuery = true)
  List<Object[]> findAllBySourceAndTargetQuery(@Param("source") String source, @Param("target") String target);

  @Query("SELECT new fr.uniamu.ibdm.gsa_server.requests.JsonData.ProvidersStatsData(a.provider, count(a.aliquotNLot)) FROM Aliquot a GROUP BY a.provider")
  List<ProvidersStatsData> generateProviderStats();

  @Query("SELECT new fr.uniamu.ibdm.gsa_server.requests.JsonData.ProductsStatsData(p.source, p.target, AVG(a.aliquotPrice)) FROM Aliquot a JOIN a.product p GROUP BY p.source, p.target")
  List<ProductsStatsData> generateProductsStats();


}
