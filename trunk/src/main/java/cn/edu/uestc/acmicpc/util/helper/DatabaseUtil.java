package cn.edu.uestc.acmicpc.util.helper;

import cn.edu.uestc.acmicpc.util.annotation.KeyField;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;

/**
 * All actions for database.
 */
public class DatabaseUtil {

  /**
   * Put all criterion in the criterion list into criteria object.
   *
   * @param criteria
   *          criteria object
   * @param criterionList
   *          criterion list
   */
  public static void putCriterionIntoCriteria(Criteria criteria, Iterable<Criterion> criterionList) {
    if (criteria == null || criterionList == null) {
      return;
    }
    for (Criterion criterion : criterionList) {
      criteria.add(criterion);
    }
  }

  /**
   * Get entity's key value.
   *
   * @param object
   *          entity object
   * @return entity's key value, if object is not a entity, return {@code null}.
   */
  public static Object getKeyValue(Object object) {
    KeyField keyField = object.getClass().getAnnotation(KeyField.class);
    if (keyField == null) {
      return null;
    }
    String methodName =
        StringUtil.getGetterOrSetter(StringUtil.MethodType.GETTER, keyField.value());
    try {
      Method method = object.getClass().getMethod(methodName);
      return method.invoke(object);
    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
      return null;
    }
  }
}
