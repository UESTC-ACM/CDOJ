package cn.edu.uestc.acmicpc.util.helper;

import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Object global methods.
 */
public class ObjectUtil {

  private static final Logger LOGGER = LogManager.getLogger(ObjectUtil.class);

  /**
   * Output object's fields and methods.
   *
   * @param obj object to be printed
   * @return information about the object
   */
  @SuppressWarnings("rawtypes")
  public static String toString(Object obj) {
    if (obj == null)
      return "null";
    ArrayList<String> list = new ArrayList<>();
    Class<?> cls = obj.getClass();
    for (Field f : cls.getFields()) {
      try {
        list.add(String.format("%s : %s", f.getName(), f.get(obj).toString()));
      } catch (Exception ignored) {
      }
    }
    for (Method m : cls.getMethods()) {
      try {
        String name = m.getName();
        if (!name.startsWith("get"))
          continue;
        name = name.substring(3);
        list.add(String.format("%s : %s", name, m.invoke(obj).toString()));
      } catch (Exception ignored) {
      }
    }
    if (obj instanceof Iterable) {
      for (Object object : (Iterable) obj) {
        list.add(toString(object));
      }
    }
    return String.format("{\n%s\n}", ArrayUtil.join(list.toArray(), ",\n"));
  }

  public static boolean equals(Object first, Object second) {
    if (first == second) {
      return true;
    }
    if (first == null || second == null) {
      return false;
    }
    return first.equals(second);
  }

  public static <T extends Serializable> boolean entityEquals(BaseDTO<T> dto, T object) {
    for (Method method : dto.getClass().getMethods()) {
      if (method.getName().startsWith("get") && !method.getName().equals("getClass")) {
        try {
          if (!method.invoke(dto)
              .equals(object.getClass().getMethod(method.getName()).invoke(object))) {
            return false;
          }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
            | NoSuchMethodException | SecurityException ignored) {
        }
      }
    }
    return true;
  }
}
