package fr.uniamu.ibdm.gsa_server.models.primarykeys;

import java.io.Serializable;
import java.util.Objects;


public class ProductPK implements Serializable {

  private String target;
  private String source;

  public ProductPK() {
  }

  public ProductPK(String target, String source) {
    this.target = target;
    this.source = source;
  }

  public String getTarget() {
    return target;
  }

  public void setTarget(String target) {
    this.target = target;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProductPK productPK = (ProductPK) o;
    return Objects.equals(target, productPK.target)
        &&
        Objects.equals(source, productPK.source);
  }

  @Override
  public int hashCode() {
    return Objects.hash(target, source);
  }
}