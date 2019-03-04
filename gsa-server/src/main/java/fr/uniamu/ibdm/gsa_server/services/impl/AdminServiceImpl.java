package fr.uniamu.ibdm.gsa_server.services.impl;

import fr.uniamu.ibdm.gsa_server.dao.AliquotRepository;
import fr.uniamu.ibdm.gsa_server.dao.ProductRepository;
import fr.uniamu.ibdm.gsa_server.dao.QueryObjects.StatsWithdrawQuery;
import fr.uniamu.ibdm.gsa_server.models.Aliquot;
import fr.uniamu.ibdm.gsa_server.requests.forms.WithdrawStatsForm;
import fr.uniamu.ibdm.gsa_server.services.AdminService;
import fr.uniamu.ibdm.gsa_server.util.DateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

	private ProductRepository productRepository;

	@Autowired
	AliquotRepository aliquotRepository;

	@Override
	public List<Aliquot> getAllAliquots() {
		return aliquotRepository.getAliquots();
	}

	@Override
	public void makeQuantityZero(long id) {//à modifier
		this.aliquotRepository.makeQuantityZeroNative(id, (long) 0);
	}
	

	@Autowired
	public AdminServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public List<StatsWithdrawQuery> getWithdrawStats(WithdrawStatsForm form) {

		String[] shards = form.getProductName().split("_");

		if (shards.length < 3) {
			return new ArrayList<>();
		}

		String lowerBound;
		String upperBound;

		lowerBound = form.getYearLowerBound() + "-" + DateConverter.monthToNumberConvertor(form.getMonthLowerBound())
				+ "-01 00:00:00";

		upperBound = form.getYearUpperBound() + "-" + DateConverter.monthToNumberConvertor(form.getMonthUpperBound())
				+ "-31 00:00:00";

		System.out.println("lower bound : " + lowerBound);

		List<Object[]> result = productRepository.getWithdrawStats(form.getTeamName(), lowerBound, upperBound,
				shards[0], shards[2]);
		List<StatsWithdrawQuery> returnValue = new ArrayList<>();

		for (int i = 0; i < result.size(); i++) {

			returnValue.add(new StatsWithdrawQuery((int) result.get(i)[0], (int) result.get(i)[1],
					(BigDecimal) result.get(i)[2]));

		}

		/* remove 'holes' in the data */
		StatsWithdrawQuery tmp;
		for (int i = 0; i < returnValue.size() - 1; i++) {

			tmp = returnValue.get(i);

			if (tmp.getMonth() == 12 && returnValue.get(i + 1).getMonth() != 1) {
				returnValue.add(i + 1, new StatsWithdrawQuery(1, tmp.getYear(), 0));
			} else if (returnValue.get(i + 1).getMonth() != returnValue.get(i).getMonth() + 1 && tmp.getMonth() != 12) {
				returnValue.add(i + 1, new StatsWithdrawQuery(tmp.getMonth() + 1, tmp.getYear(), 0));
			}
		}

		return returnValue;
	}
}