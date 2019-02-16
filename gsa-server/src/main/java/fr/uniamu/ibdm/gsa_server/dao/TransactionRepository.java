package fr.uniamu.ibdm.gsa_server.dao;

import fr.uniamu.ibdm.gsa_server.models.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

}
