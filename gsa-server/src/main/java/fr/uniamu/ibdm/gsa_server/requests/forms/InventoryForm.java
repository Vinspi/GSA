package fr.uniamu.ibdm.gsa_server.requests.forms;

public class InventoryForm implements Form {

  private long aliquotNLot;
  private int quantity;

  public InventoryForm(long aliquotNLot, int quantity) {
    this.aliquotNLot = aliquotNLot;
    this.quantity = quantity;
  }

  public InventoryForm() {

  }

  @Override
  public boolean validate() {

    if (aliquotNLot < 0 || quantity < 0) {  
      return false;
    }


    return true;
  }

  public long getAliquotNLot() {
    return aliquotNLot;
  }

  public void setAliquotNLot(long aliquotNLot) {
    this.aliquotNLot = aliquotNLot;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }


}
