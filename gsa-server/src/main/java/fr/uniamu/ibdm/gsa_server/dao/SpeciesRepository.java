package fr.uniamu.ibdm.gsa_server.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.uniamu.ibdm.gsa_server.models.Species;

@Repository
public interface SpeciesRepository extends CrudRepository<Species, String> {

  @Query(value = "SELECT species_name FROM species", nativeQuery = true)
  List<String> getAllSpeciesName();
}
