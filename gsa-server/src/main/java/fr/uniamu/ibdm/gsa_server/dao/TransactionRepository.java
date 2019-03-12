package fr.uniamu.ibdm.gsa_server.dao;

import fr.uniamu.ibdm.gsa_server.models.Transaction;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
  @Query(value = "SELECT *\n"
      + "FROM transaction\n"
      + "WHERE (aliquot_id =:aliquot_id\n"
      + "AND transaction_type LIKE 'OUTDATED')", nativeQuery = true)
    List<Transaction> findOutdatedTransactionByAliquot(@Param("aliquot_id") Long aliquot_id);
}
