package fr.uniamu.ibdm.gsa_server.services;

import fr.uniamu.ibdm.gsa_server.models.Transaction;

import java.util.List;

public interface TransactionService {

    /**
     * This method fetch all transactions in the database and return a list
     * of all transactions.
     * @return a list of transactions.
     */
    List<Transaction> getWithdrawalHistory();

}
