package cn.edu.uestc.acmicpc.util.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Map a field to column in CSV file
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface CSVMap {

  /**
   * Mapping column name.
   *
   * @return mapping column name.
   */
  public String value() default "";
}
