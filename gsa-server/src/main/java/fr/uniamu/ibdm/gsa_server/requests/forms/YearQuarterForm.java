package fr.uniamu.ibdm.gsa_server.requests.forms;

import java.util.Arrays;
import java.util.stream.Collectors;

import fr.uniamu.ibdm.gsa_server.models.enumerations.Quarter;

public class YearQuarterForm implements Form {
  private String quarter;
  private Integer year;

  public YearQuarterForm(String quarter, Integer year) {
    this.quarter = quarter;
    this.year = year;
  }

  @Override
  public boolean validate() {
    if (quarter == null || year == null) {
      return false;
    }
    if (!Arrays.stream(Quarter.values()).map(Quarter::name).collect(Collectors.toSet())
        .contains(this.quarter)) {
      return false;
    }

    if (year > Integer.MAX_VALUE || year < Integer.MIN_VALUE) {
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

  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }
}
