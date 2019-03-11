package fr.uniamu.ibdm.gsa_server.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.uniamu.ibdm.gsa_server.models.Aliquot;

@Repository
public interface AliquotRepository extends CrudRepository<Aliquot, Long> {

	@Query(value = "SELECT aliquotnlot, aliquot_expiration_date, aliquot_quantity_visible_stock, aliquot_quantity_hidden_stock FROM aliquot WHERE (source LIKE :source AND target LIKE :target)", nativeQuery = true)
	List<Object[]> findAllBySourceAndTargetQuery(@Param("source") String source, @Param("target") String target);

	@Query("select new fr.uniamu.ibdm.gsa_server.models.Aliquot(a.aliquotNLot, a.aliquotExpirationDate,"
			+ " a.aliquotQuantityVisibleStock, a.aliquotQuantityHiddenStock) from Aliquot a")
	List<Aliquot> getAliquots();

	// envoyer le nom du produit getProductName();


		
	
}
