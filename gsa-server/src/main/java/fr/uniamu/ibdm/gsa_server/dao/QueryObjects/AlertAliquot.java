package fr.uniamu.ibdm.gsa_server.dao.QueryObjects;

import java.time.LocalDate;

public class AlertAliquot {

  private long nlot;
  private LocalDate expirationDate;
  private int qte;

  public AlertAliquot() {
  }

  /**
   * Constructor for AlertAliquot object.
   *
   * @param nlot Nlot of current aliquot.
   * @param expirationDate Expiration date of current aliquot.
   * @param qte Quantity left for this aliquot.
   */
  public AlertAliquot(long nlot, LocalDate expirationDate, int qte) {
    this.nlot = nlot;
    this.expirationDate = expirationDate;
    this.qte = qte;
  }

  public int getQte() {
    return qte;
  }

  public void setQte(int qte) {
    this.qte = qte;
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
