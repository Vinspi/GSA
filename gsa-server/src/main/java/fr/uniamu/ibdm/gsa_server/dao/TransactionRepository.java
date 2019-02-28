package fr.uniamu.ibdm.gsa_server.dao;

import fr.uniamu.ibdm.gsa_server.models.Transaction;
import fr.uniamu.ibdm.gsa_server.models.enumerations.TransactionType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    List<Transaction> findAllByTransactionDateGreaterThanEqualAndTransactionDateLessThanEqualAndTransactionTypeLikeOrderByTransactionDateAsc(LocalDate begin, LocalDate end, TransactionType transactionType);

    List<Transaction> findAllByTransactionDateGreaterThanEqualOrderByTransactionDateAsc(LocalDate begin);

    List<Transaction> findAllByTransactionDateLessThanEqualOrderByTransactionDateAsc(LocalDate end);

}
