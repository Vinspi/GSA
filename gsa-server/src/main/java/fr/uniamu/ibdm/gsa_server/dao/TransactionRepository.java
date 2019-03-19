package fr.uniamu.ibdm.gsa_server.dao;

import fr.uniamu.ibdm.gsa_server.models.Member;
import fr.uniamu.ibdm.gsa_server.models.Transaction;
import fr.uniamu.ibdm.gsa_server.models.enumerations.TransactionType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    /* FUNCTIONS FOR ADMIN */
    List<Transaction> findAllByTransactionDateGreaterThanEqualAndTransactionDateLessThanEqualAndTransactionTypeLike(LocalDate begin, LocalDate end, TransactionType transactionType);

    List<Transaction> findAllByTransactionDateGreaterThanEqualAndTransactionTypeLike(LocalDate begin, TransactionType transactionType);

    List<Transaction> findAllByTransactionDateLessThanEqualAndTransactionTypeLike(LocalDate end, TransactionType transactionType);

    /* FUNCTIONS FOR USERS */
    List<Transaction> findAllByMemberAndTransactionDateGreaterThanEqualAndTransactionDateLessThanEqualAndTransactionTypeLike(Member member, LocalDate begin, LocalDate end, TransactionType transactionType);

    List<Transaction> findAllByMemberAndTransactionDateGreaterThanEqualAndTransactionTypeLike(Member member, LocalDate begin, TransactionType transactionType);

    List<Transaction> findAllByMemberAndTransactionDateLessThanEqualAndTransactionTypeLike(Member member, LocalDate end, TransactionType transactionType);

    List<Transaction> findAllByMember(Member member);

}
