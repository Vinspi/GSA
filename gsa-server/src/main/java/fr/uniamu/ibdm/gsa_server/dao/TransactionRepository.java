package fr.uniamu.ibdm.gsa_server.dao;



import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.uniamu.ibdm.gsa_server.models.Transaction;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
	


	 
	 /*@Query(value = "SELECT transactionMotif, transactionType, transactionDate, transactionQuantity, aliquot, member"
	 		+ " FROM Transaction WHERE (aliquot_id =:aliquot_id)", nativeQuery = true)
		Transaction findByAliquot(@Param("aliquot_id") long aliquot_id);*/
	
	@Query(value ="select new fr.uniamu.ibdm.gsa_server.models.Transaction"
			+ "(t.transactionMotif) from Transaction t \n"
			  + "JOIN aliquot ON (aliquot_id = aliquot.aliquotnlot)\n"
			+ "WHERE aliquot_id =:aliquot_id", nativeQuery = true)
	List<Transaction> getTransactionsByAliquot(@Param("aliquot_id") long aliquot_id);
	

	 @Query(value = "SELECT *\n"
		      + "FROM transaction\n"
		      + "WHERE (aliquot_id =:aliquot_id\n"
		      + "AND transaction_type LIKE 'WITHDRAW')", nativeQuery = true)
	 List<Transaction> findOutdatedTransactionByAliquot(@Param("aliquot_id") Long aliquot_id);
	
	//+ "AND transaction_type LIKE 'OUTDATED')"
}
