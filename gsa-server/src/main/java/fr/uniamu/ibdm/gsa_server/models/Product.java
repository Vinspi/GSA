package fr.uniamu.ibdm.gsa_server.models;

import fr.uniamu.ibdm.gsa_server.models.primarykeys.ProductPK;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Collection;

@Entity
@IdClass(ProductPK.class)
public class Product implements Serializable {


  @Id
  @ManyToOne
  @JoinColumn(name = "target_pk", columnDefinition = "varchar(30)")
  private Species target;


  @Id
  @ManyToOne
  @JoinColumn(name = "source_pk", columnDefinition = "varchar(30)")
  private Species source;

  @OneToMany(mappedBy = "product")
  private Collection<Aliquot> aliquots;

  public Product() {
  }

  public Product(Species target, Species source, Collection<Aliquot> aliquots) {
    this.target = target;
    this.source = source;
    this.aliquots = aliquots;
  }

  @Transient
  public String getProductName(){
    return this.getSource().getSpeciesName().toUpperCase()+"_ANTI_"+this.getTarget().getSpeciesName().toUpperCase();
  }

  public Species getTarget() {
    return target;
  }

  public void setTarget(Species target) {
    this.target = target;
  }

  public Collection<Aliquot> getAliquots() {
    return aliquots;
  }

  public void setAliquots(Collection<Aliquot> aliquots) {
    this.aliquots = aliquots;
  }

  public Species getSource() {
    return source;
  }

  public void setSource(Species source) {
    this.source = source;
  }

}
