package fr.uniamu.ibdm.gsa_server.services;

import fr.uniamu.ibdm.gsa_server.dao.QueryObjects.StatsWithdrawQuery;
import fr.uniamu.ibdm.gsa_server.models.Transaction;
import fr.uniamu.ibdm.gsa_server.requests.forms.WithdrawStatsForm;

import java.time.LocalDate;
import java.util.List;

public interface AdminService {

    /**
     * This method retrieve stats for building admin chart.
     *
     * @param form container for building options of the chart.
     * @return a list of months, years, and withdrawals
     */
    List<StatsWithdrawQuery> getWithdrawStats(WithdrawStatsForm form);

    /**
     * This method retrieves the name of all species found in the database.
     *
     * @return a list of names or null if an error occurred.
     */
    List<String> getAllSpeciesNames();


    /**
     * This method adds a new product named after the source and target species name.
     *
     * @param sourceName string
     * @param targetName string
     * @return true if adding the product is successful, false otherwise.
     */
    boolean addProduct(String sourceName, String targetName);

    /**
     * This method fetch all withdrawals in the period given in argument
     * in the database and return a list of all transactions.
     *
     * @param begin The begin of the period to take into account.
     * @param end The end of the period to take into account.
     * @return a list of transactions.
     */
    List<Transaction> getWithdrawalsHistoryBetween(LocalDate begin, LocalDate end);

    /*
     * This method fetch all withdrawals since the date given in argument
     * in the database and return a list of those.
     *
     * @param begin The begin of the period to take into account.
     * @return a list of transactions.
     */
    //List<Transaction> getWithdrawalHistorySince(LocalDate begin);

    /*
     * This method fetch all withdrawals up to the date given in argument
     * in the database and return a list of those.
     *
     * @param end The end of the period to take into account.
     * @return a list of transactions.
     */
    //List<Transaction> getWithdrawalHistoryUpTo(LocalDate end);

    /*
     * This method fetch all withdrawals in the database and return
     * a list of those.
     *
     * @return a list of transactions.
     */
    List<Transaction> getWithdrawalsHistory();
}
