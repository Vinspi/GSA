package fr.uniamu.ibdm.gsa_server.dao.QueryObjects;

import fr.uniamu.ibdm.gsa_server.util.LocalDateAttributeConverter;

import javax.persistence.Convert;
import java.time.LocalDate;

public class AlertAliquot {

  private long nlot;
  @Convert(converter = LocalDateAttributeConverter.class)
  private LocalDate expirationDate;

  public AlertAliquot() {
  }

  public AlertAliquot(long nlot, LocalDate expirationDate) {
    this.nlot = nlot;
    this.expirationDate = expirationDate;
  }

  public long getNlot() {
    return nlot;
  }

  public void setNlot(long nlot) {
    this.nlot = nlot;
  }

  public LocalDate getExpirationDate() {
    return expirationDate;
  }

  public void setExpirationDate(LocalDate expirationDate) {
    this.expirationDate = expirationDate;
  }
}
