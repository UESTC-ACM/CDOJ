package cn.edu.uestc.acmicpc.db.condition.base;

import cn.edu.uestc.acmicpc.db.condition.base.Condition.ConditionType;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * We can use this class to get {@link Condition} entity.
 * <p>
 * <strong>USAGE</strong>
 * <p>
 * We can write a simple class than extends this class, and add the {@link Exp}
 * annotation to each field of the class. If we do not do with, the field will
 * be handled in subclass {@code getCondition} method.
 * <p>
 * <strong>For developer</strong>:
 * <ul>
 * <li>
 * In default, the map field's name is equal to the data field's name. If you
 * want to map the class field into another data field, use the
 * {@code Exp#mapField()} parameter please.
 * <p>
 * For example:
 * <p>
 * <code>
 * {@literal @}Exp(mapField = "userId", type = ConditionType.EQUALS)<br>
 * public Integer id;
 * </code></li>
 * <li>
 * {@code ConditionType.LIKE} type will add % in two ends of the string,
 * otherwise please handle this case in {@code getCondition} by yourself.
 * <p>
 * For example:
 * <p>
 * <code>
 * public String userName;
 * <br>
 * {@literal @}Override<br>
 * public Condition getCondition() {<br>
 * &nbsp;&nbsp;Condition condition = super.getCondition();<br>
 * &nbsp;&nbsp;if (userName != null) {<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;conditions.addEntry("userName", ConditionType.STRING_EQUALS,
 * "%" + userName);<br>
 * &nbsp;&nbsp;}<br>
 * &nbsp;&nbsp;return condition;<br>
 * }
 * </code></li>
 * </ul>
 *
 * @deprecated this class is deprecated, use
 *             {@link cn.edu.uestc.acmicpc.db.criteria.BaseCriteria}
 *             instead.
 */
@Deprecated
public abstract class BaseCondition {

  private static final Logger LOGGER = LogManager.getLogger(BaseCondition.class);
  @SuppressWarnings("unused")
  private final String keyField;

  /**
   * Creates a {@link BaseCondition} sub class with key field.
   *
   * @param keyField
   *          key field's name.
   */
  protected BaseCondition(String keyField) {
    this.keyField = keyField;
  }

  /**
   * Current page id.
   */
  public Long currentPage;
  /**
   * Number of records per page.
   */
  public Long countPerPage;
  /**
   * Fields for ordering, separated with ','.
   */
  public String orderFields;
  /**
   * Whether ordered by ascending order for each fields, separated with ','.
   * Each value can be {@code true} or {@code false}.
   */
  public String orderAsc;

  /**
   * Get Condition objects from conditions
   *
   * @return condition object we need
   * @throws AppException
   */
  public Condition getCondition() throws AppException {
    Condition condition = new Condition();

    if (orderFields != null) {
      String[] fields = orderFields.split(",");
      String[] asc = orderAsc.split(",");
      AppExceptionUtil.assertTrue(fields.length == asc.length);
      for (int i = 0; i < fields.length; i++) {
        condition.addOrder(fields[i], asc[i].equals("true"));
      }
    }
    Class<?> clazz = this.getClass();
    for (Field field : clazz.getFields()) {
      if (field.isAnnotationPresent(Exp.class)) {
        Exp exp = field.getAnnotation(Exp.class);
        try {
          Object value = field.get(this);
          if (value == null) {
            continue;
          }
          if (value instanceof Boolean) {
            value = (Boolean) value ? "1" : "0";
          }
          if (exp.mapField().trim().equals("")) {
            condition.addEntry(field.getName(), exp.type(), value);
          } else {
            condition.addEntry(exp.mapField(), exp.type(), value);
          }
        } catch (IllegalArgumentException | IllegalAccessException | AppException e) {
          e.printStackTrace();
          LOGGER.error(e);
        }
      }
    }
    return condition;
  }

  /**
   * Annotation for condition expressions. See {@link BaseCondition} for more
   * details.
   */
  @Retention(RetentionPolicy.RUNTIME)
  public @interface Exp {

    /**
     * Mapping field name.
     *
     * @return mapping field name
     */
    public String mapField() default "";

    /**
     * Condition compare type, generate the condition clause by the compare
     * type.
     *
     * @return condition compare type
     */
    public ConditionType type();

  }

}
