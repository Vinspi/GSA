package fr.uniamu.ibdm.gsa_server.dao;

import java.time.LocalDate;
import java.util.List;

import fr.uniamu.ibdm.gsa_server.requests.JsonData.AliquotExpired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fr.uniamu.ibdm.gsa_server.models.Aliquot;

@Repository
public interface AliquotRepository extends JpaRepository<Aliquot, Long> {
	/*
	 *  private long aliquotNLot;


  private LocalDate aliquotExpirationDate;
  private long aliquotQuantityVisibleStock;
  private long aliquotQuantityHiddenStock;
  private float aliquotPrice;
  private String provider;*/
	
	@Query("select new fr.uniamu.ibdm.gsa_server.requests.JsonData.AliquotExpired(a.aliquotNLot, a.aliquotExpirationDate) from Aliquot a")
  List<AliquotExpired> getAliquots();
}
