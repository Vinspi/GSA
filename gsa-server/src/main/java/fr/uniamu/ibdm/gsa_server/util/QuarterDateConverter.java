package fr.uniamu.ibdm.gsa_server.util;

import java.time.LocalDate;
import java.time.Month;

import fr.uniamu.ibdm.gsa_server.models.enumerations.Quarter;

public class QuarterDateConverter {

  /**
   * Gets the first day of quarter parameter value.
   * 
   * @param quarter value of Quarter enumeration
   * @param year year value
   * 
   * @return LocalDate
   */
  public static LocalDate getQuarterFirstDay(Quarter quarter, int year) {
    if (quarter.equals(Quarter.QUARTER_1)) {
      return LocalDate.of(year, 1, 1);
    } else if (quarter.equals(Quarter.QUARTER_2)) {
      return LocalDate.of(year, 4, 1);
    } else if (quarter.equals(Quarter.QUARTER_3)) {
      return LocalDate.of(year, 7, 1);
    } else {
      return LocalDate.of(year, 10, 1);
    }
  }

  /**
   * Gets the last day of quarter parameter value.
   * 
   * @param quarter value of Quarter enumeration
   * @param year year value
   * 
   * @return LocalDate
   */
  public static LocalDate getQuarterLastDay(Quarter quarter, int year) {
    if (quarter.equals(Quarter.QUARTER_1)) {
      return LocalDate.of(year, 3, 31);
    } else if (quarter.equals(Quarter.QUARTER_2)) {
      return LocalDate.of(year, 6, 30);
    } else if (quarter.equals(Quarter.QUARTER_3)) {
      return LocalDate.of(year, 9, 30);
    } else {
      return LocalDate.of(year, 12, 31);
    }
  }

  /**
   * Gets the quarter of parameter date.
   * 
   * @param date LocalDate
   * @return a value of Quarter enum.
   */
  public static Quarter getQuarterOfDate(LocalDate date) {
    Quarter quarter;
    int year = date.getYear();
    if (LocalDate.of(year, Month.MARCH, 31).isAfter(date)) {
      quarter = Quarter.QUARTER_1;
    } else if (LocalDate.of(year, Month.JUNE, 30).isAfter(date)) {
      quarter = Quarter.QUARTER_2;
    } else if (LocalDate.of(year, Month.SEPTEMBER, 31).isAfter(date)) {
      quarter = Quarter.QUARTER_3;
    } else {
      quarter = Quarter.QUARTER_4;
    }
    return quarter;
  }

}