package fr.uniamu.ibdm.gsa_server.dao;



import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.uniamu.ibdm.gsa_server.models.Transaction;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {


	 
	 @Query(value = "SELECT transactionMotif, transactionType, transactionDate, transactionQuantity, aliquot, member"
	 		+ " FROM Transaction WHERE (aliquot_id =:aliquot_id)", nativeQuery = true)
		Transaction findByAliquot(@Param("aliquot_id") long aliquot_id);
	
	
}
