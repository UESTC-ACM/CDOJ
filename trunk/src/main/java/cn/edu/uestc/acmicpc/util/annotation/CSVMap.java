package cn.edu.uestc.acmicpc.util.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Map a field to column in CSV file
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface CSVMap {

  /**
   * Mapping column name.
   *
   * @return mapping column name.
   */
  public String value() default "";
}
