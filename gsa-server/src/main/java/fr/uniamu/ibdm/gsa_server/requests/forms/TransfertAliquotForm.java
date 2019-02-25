package fr.uniamu.ibdm.gsa_server.requests.forms;

import fr.uniamu.ibdm.gsa_server.models.enumerations.StorageType;

public class TransfertAliquotForm implements Form {

  private StorageType from;
  private StorageType to;
  private long numLot;
  private int quantity;

  /**
   * Constructor for TransfertAliquotForm.
   *
   * @param from from.
   * @param to destination.
   * @param numLot lot number of the aliquot.
   * @param quantity quantity to be transferred.
   */
  public TransfertAliquotForm(StorageType from, StorageType to, long numLot, int quantity) {
    this.from = from;
    this.to = to;
    this.numLot = numLot;
    this.quantity = quantity;
  }

  public TransfertAliquotForm() {
  }

  @Override
  public boolean validate() {
    if (numLot < 0 || quantity < 0 || from == to) {
      return false;
    }

    return true;
  }

  public StorageType getFrom() {
    return from;
  }

  public void setFrom(StorageType from) {
    this.from = from;
  }

  public StorageType getTo() {
    return to;
  }

  public void setTo(StorageType to) {
    this.to = to;
  }

  public long getNumLot() {
    return numLot;
  }

  public void setNumLot(long numLot) {
    this.numLot = numLot;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }


}
