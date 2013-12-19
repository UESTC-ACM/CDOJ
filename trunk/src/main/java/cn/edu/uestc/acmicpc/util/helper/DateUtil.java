package cn.edu.uestc.acmicpc.util.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Global method for date operations.
 */
public class DateUtil {

  /**
   * Parse date string.
   *
   * @param string date string to parse
   * @return Date object for the string
   * @throws ParseException
   */
  public static Date valueOf(String string) throws ParseException {
    return new SimpleDateFormat("yyyy/MM/dd").parse(string);
  }

  /**
   * Date add operation.
   *
   * @param date date we want to add
   * @param field add field name
   * @param offset add offset
   * @return new date after add operation
   */
  public static Date add(Date date, int field, int offset) {
    Calendar cd = Calendar.getInstance();
    cd.setTime(date);
    cd.add(field, offset);
    return cd.getTime();
  }
}
