package fr.uniamu.ibdm.gsa_server.dao;

import java.time.LocalDate;
import java.util.List;

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
	
	@Query("select a.aliquotNLot, a.aliquotExpirationDate from Aliquot a")
    List<Aliquot> getAliquots();
}
