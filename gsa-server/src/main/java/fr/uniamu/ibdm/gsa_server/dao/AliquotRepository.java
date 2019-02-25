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

	@Query("select new fr.uniamu.ibdm.gsa_server.models.Aliquot(a.aliquotNLot, a.aliquotExpirationDate,"
			+ " a.aliquotQuantityVisibleStock, a.aliquotQuantityHiddenStock) from Aliquot a")
	List<Aliquot> getAliquots();

//	@Modifying(clearAutomatically = true)// save au lieu d update ( mettre la val 0 )
//    @Query("UPDATE fr.uniamu.ibdm.gsa_server.models.Aliquot a SET a.aliquotQuantityVisibleStock = :aliquotQuantityVisibleStock WHERE a.aliquotNLot = :aliquotNLot")
//	void makeQuantityZeroNative(@Param("aliquotNLot") Long aliquotNLot, @Param("aliquotQuantityVisibleStock") Long aliquotQuantityVisibleStock);
	// envoyer le nom du produit getProductName();
	// rajouter le filrage

	// utiliser save de repo au lieu de query, findbyid( nlot) -> apres set qt
	// visible et hidden apres save aliquots
	// inserer des transactions pour la gestion des pertes ( ennum OUDATED pour les
	// qts visibles et hidden )

}
