package fr.uniamu.ibdm.gsa_server.util;

import java.time.LocalDate;

import fr.uniamu.ibdm.gsa_server.models.enumerations.Quarter;

public class QuarterDateConverter {

  /**
   * Gets the first day of quarter parameter value.
   * 
   * @param quarter string value of Quarter enumeration
   * @param year year value
   * 
   * @return LocalDate if quarter is correctly defined, null otherwise.
   */
  public static LocalDate getQuarterFirstDay(String quarter, int year) {
    if (quarter.equals(Quarter.QUARTER_1.name())) {
      return LocalDate.of(year, 1, 1);
    } else if (quarter.equals(Quarter.QUARTER_2.name())) {
      return LocalDate.of(year, 4, 1);
    } else if (quarter.equals(Quarter.QUARTER_3.name())) {
      return LocalDate.of(year, 7, 1);
    } else if (quarter.equals(Quarter.QUARTER_4.name())) {
      return LocalDate.of(year, 10, 1);
    }
    return null;
  }

  /**
   * Gets the last day of quarter parameter value.
   * 
   * @param quarter string value of Quarter enumeration
   * @param year year value
   * 
   * @return LocalDate if quarter is correctly defined, null otherwise.
   */
  public static LocalDate getQuarterLastDay(String quarter, int year) {
    if (quarter.equals(Quarter.QUARTER_1.name())) {
      return LocalDate.of(year, 3, 31);
    } else if (quarter.equals(Quarter.QUARTER_2.name())) {
      return LocalDate.of(year, 6, 30);
    } else if (quarter.equals(Quarter.QUARTER_3.name())) {
      return LocalDate.of(year, 9, 30);
    } else if (quarter.equals(Quarter.QUARTER_4.name())) {
      return LocalDate.of(year, 12, 31);
    }
    return null;
  }

}