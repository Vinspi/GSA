package fr.uniamu.ibdm.gsa_server.requests.forms;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class PriceForm implements Form {
  private BigDecimal price;

  public PriceForm() {
  }
  
  public PriceForm(BigDecimal price) {
    this.price = price;
  }
  
  public BigDecimal getprice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  @Override
  public boolean validate() {
    return Pattern.matches("^[0-9]{1,6}[.]?[0-9]{0,2}$", this.price.toString());
  }

}
