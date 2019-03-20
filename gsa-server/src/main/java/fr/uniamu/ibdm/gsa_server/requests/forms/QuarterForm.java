package fr.uniamu.ibdm.gsa_server.requests.forms;

import java.util.Arrays;
import java.util.stream.Collectors;

import fr.uniamu.ibdm.gsa_server.models.enumerations.Quarter;

public class QuarterForm implements Form {
  private String quarter;

  public QuarterForm(String quarter) {
    this.quarter = quarter;
  }

  @Override
  public boolean validate() {
    if (quarter == null) {
      return false;
    }
    if (!Arrays.stream(Quarter.values()).map(Quarter::name).collect(Collectors.toSet())
        .contains(this.quarter)) {
      return false;
    }

    return true;
  }

  public String getQuarter() {
    return quarter;
  }

  public void setQuarter(String quarter) {
    this.quarter = quarter;
  }

}
