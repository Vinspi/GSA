package fr.uniamu.ibdm.gsa_server.requests.forms;

import fr.uniamu.ibdm.gsa_server.models.enumerations.StorageType;

import java.util.regex.Pattern;

public class AddAlertForm implements Form {

  private StorageType storageType;
  private String productName;
  private int quantity;

  /**
   * Constructor for AddAlertForm.
   *
   * @param storageType Storage type for the alert.
   * @param productName Product name for the alert.
   * @param quantity    Min quantity for triggering alert.
   */
  public AddAlertForm(StorageType storageType, String productName, int quantity) {
    this.storageType = storageType;
    this.productName = productName;
    this.quantity = quantity;
  }

  public AddAlertForm() {
  }

  @Override
  public boolean validate() {
    Pattern patternProductName = Pattern.compile("^[A-Z]*_ANTI_[A-Z]*");

    if (!patternProductName.matcher(productName).matches() || quantity <= 0) {
      return false;
    }

    return true;
  }

  public StorageType getStorageType() {
    return storageType;
  }

  public void setStorageType(StorageType storageType) {
    this.storageType = storageType;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
}
