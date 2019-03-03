package fr.uniamu.ibdm.gsa_server.util;

import java.time.LocalDate;

import fr.uniamu.ibdm.gsa_server.models.enumerations.Quarter;

public class QuarterDateConverter {
  private LocalDate firstDay;
  private LocalDate lastDay;

  public QuarterDateConverter() {
  }

  public QuarterDateConverter(LocalDate firstDay, LocalDate lastDay) {
    this.firstDay = firstDay;
    this.lastDay = lastDay;
  }

  public LocalDate getFirstDay() {
    return firstDay;
  }

  public void setFirstDay(LocalDate firstDay) {
    this.firstDay = firstDay;
  }

  public LocalDate getLastDay() {
    return lastDay;
  }

  public void setLastDay(LocalDate lastDay) {
    this.lastDay = lastDay;
  }

  /**
   * Sets firstDay and lastDay attributes according to quarter parameter value.
   * 
   * @param quarter string value of Quarter enumeration
   * @param year year value
   * 
   * @return true if quarter is correctly defined, false otherwise.
   */
  public boolean setQuarterDates(String quarter, int year) {

    if (quarter.equals(Quarter.QUARTER_1.name())) {
      this.firstDay = LocalDate.of(year, 1, 1);
      this.lastDay = LocalDate.of(year, 3, 31);
    } else if (quarter.equals(Quarter.QUARTER_2.name())) {
      this.firstDay = LocalDate.of(year, 4, 1);
      this.lastDay = LocalDate.of(year, 6, 30);
    } else if (quarter.equals(Quarter.QUARTER_3.name())) {
      this.firstDay = LocalDate.of(year, 7, 1);
      this.lastDay = LocalDate.of(year, 9, 30);
    } else if (quarter.equals(Quarter.QUARTER_4.name())) {
      this.firstDay = LocalDate.of(year, 10, 1);
      this.lastDay = LocalDate.of(year, 12, 31);
    } else {
      return false;
    }

    return true;
  }

}
