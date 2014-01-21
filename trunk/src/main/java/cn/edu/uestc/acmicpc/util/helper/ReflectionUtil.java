package cn.edu.uestc.acmicpc.util.helper;

import java.lang.reflect.Method;

/**
 * Global method for get method and field from objects.
 */
public class ReflectionUtil {

  @SuppressWarnings({"unchecked", "rawtypes"})
  public static Method getMethodByAnnotation(Class<?> clazz, Class annotation) {
    Method[] methods = clazz.getMethods();
    for (Method method : methods) {
      if (method.getAnnotation(annotation) != null)
        return method;
    }
    return null;
  }

  /**
   * Convert a string value to specific type.
   *
   * @param value      string value
   * @param targetType target type class
   * @return target value
   */
  public static Object valueOf(String value, Class<?> targetType) {
    try {
      Method convertMethod = targetType.getMethod("valueOf", String.class);
      return convertMethod.invoke(null, value);
    } catch (Exception e) {
      return value;
    }
  }
}
