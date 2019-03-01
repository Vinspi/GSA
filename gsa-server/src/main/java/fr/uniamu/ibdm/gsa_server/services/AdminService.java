package fr.uniamu.ibdm.gsa_server.services;

import fr.uniamu.ibdm.gsa_server.dao.QueryObjects.StatsWithdrawQuery;
import fr.uniamu.ibdm.gsa_server.models.Aliquot;
import fr.uniamu.ibdm.gsa_server.requests.forms.WithdrawStatsForm;

import java.util.List;

public interface AdminService {

	/**
	 * This method retrieve stats for building admin chart.
	 *
	 * @param form container for building options of the chart.
	 * @return a list of months, years, and withdrawals
	 */
	List<StatsWithdrawQuery> getWithdrawStats(WithdrawStatsForm form);

	List<Aliquot> getAllAliquots();

	void makeQuantityZero(long id);
		
}
