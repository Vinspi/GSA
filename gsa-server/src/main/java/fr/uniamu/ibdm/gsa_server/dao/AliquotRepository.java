package fr.uniamu.ibdm.gsa_server.dao;

import fr.uniamu.ibdm.gsa_server.dao.QueryObjects.AlertAliquot;
import fr.uniamu.ibdm.gsa_server.models.Aliquot;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AliquotRepository extends CrudRepository<Aliquot, Long> {

  @Query(value = "SELECT aliquotnlot, aliquot_expiration_date, aliquot_quantity_visible_stock, aliquot_quantity_hidden_stock FROM aliquot WHERE (source LIKE :source AND target LIKE :target)", nativeQuery = true)
  List<Object[]> findAllBySourceAndTargetQuery(@Param("source") String source, @Param("target") String target);


}
