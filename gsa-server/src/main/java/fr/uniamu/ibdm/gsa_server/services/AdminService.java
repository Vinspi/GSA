package fr.uniamu.ibdm.gsa_server.services;

import fr.uniamu.ibdm.gsa_server.dao.QueryObjects.StatsWithdrawQuery;
import fr.uniamu.ibdm.gsa_server.requests.forms.WithdrawStatsForm;

import java.util.List;

public interface AdminService {

  List<StatsWithdrawQuery> getWithdrawStats(WithdrawStatsForm form);

}
