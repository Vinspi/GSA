package fr.uniamu.ibdm.gsa_server.util;

import fr.uniamu.ibdm.gsa_server.models.enumerations.AlertType;
import fr.uniamu.ibdm.gsa_server.models.enumerations.StorageType;

public class EnumConvertor {

  public static AlertType storageTypeToAlertType(StorageType storageType){
    switch (storageType) {
      case STOCK:
        return AlertType.VISIBLE_STOCK;
      case RESERVE:
        return AlertType.HIDDEN_STOCK;
      case GENERAL:
        return AlertType.GENERAL;
      default:
        return AlertType.GENERAL;
    }
  }
}
