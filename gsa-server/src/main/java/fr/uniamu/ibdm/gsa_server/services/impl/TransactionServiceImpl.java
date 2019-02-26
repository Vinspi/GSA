package fr.uniamu.ibdm.gsa_server.services.impl;

import fr.uniamu.ibdm.gsa_server.dao.TeamRepository;
import fr.uniamu.ibdm.gsa_server.dao.TransactionRepository;
import fr.uniamu.ibdm.gsa_server.dao.UserRepository;
import fr.uniamu.ibdm.gsa_server.models.Transaction;
import fr.uniamu.ibdm.gsa_server.models.enumerations.TransactionType;
import fr.uniamu.ibdm.gsa_server.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<Transaction> getWithdrawalHistory() {

        List<Transaction> withdrawalHistory = new ArrayList<>();

        transactionRepository.findAll().forEach(element -> {
            if (element.getTransactionType() == TransactionType.WITHDRAW) {
                withdrawalHistory.add(element);
            }
        });

        return withdrawalHistory;
    }
}
