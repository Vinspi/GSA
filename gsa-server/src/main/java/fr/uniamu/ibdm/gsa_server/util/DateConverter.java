package fr.uniamu.ibdm.gsa_server.util;

import java.util.HashMap;

public class DateConverter {

  private static HashMap<String, String> monthToNumber;
  private static HashMap<String, String> numberToMonth;


  /**
   * This method convert month name to month number.
   *
   * @param month Name of the month.
   * @return String formatted number corresponding to the specified month.
   */
  public static String monthToNumberConvertor(String month) {
    monthToNumber = new HashMap<>();
    monthToNumber.put("january", "1");
    monthToNumber.put("february", "2");
    monthToNumber.put("march", "3");
    monthToNumber.put("april", "4");
    monthToNumber.put("may", "5");
    monthToNumber.put("june", "6");
    monthToNumber.put("july", "7");
    monthToNumber.put("august", "8");
    monthToNumber.put("september", "9");
    monthToNumber.put("october", "10");
    monthToNumber.put("november", "11");
    monthToNumber.put("december", "12");

    return monthToNumber.get(month);
  }

  /**
   * This method convert month number to month name.
   *
   * @param number The number of the month.
   * @return The name of the month.
   */
  public static String numberToMonthConvertor(String number) {
    numberToMonth = new HashMap<>();
    numberToMonth.put("1", "january");
    numberToMonth.put("2", "february");
    numberToMonth.put("3", "march");
    numberToMonth.put("4", "april");
    numberToMonth.put("5", "may");
    numberToMonth.put("6", "june");
    numberToMonth.put("7", "july");
    numberToMonth.put("8", "august");
    numberToMonth.put("9", "september");
    numberToMonth.put("10", "october");
    numberToMonth.put("11", "november");
    numberToMonth.put("12", "december");

    return numberToMonth.get(number);
  }

}
