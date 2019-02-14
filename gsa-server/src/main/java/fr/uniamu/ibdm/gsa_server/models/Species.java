package fr.uniamu.ibdm.gsa_server.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Species {

  @Id
  @Column(length = 30)
  private String speciesName;

  public Species() {
  }

  public Species(String speciesName) {
    this.speciesName = speciesName;
  }

  public String getSpeciesName() {
    return speciesName;
  }

  public void setSpeciesName(String speciesName) {
    this.speciesName = speciesName;
  }




}
