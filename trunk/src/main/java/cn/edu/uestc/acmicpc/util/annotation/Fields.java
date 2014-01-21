package cn.edu.uestc.acmicpc.util.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * DTO's DB fields for DB query.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Fields {

  String[] value();
}
