/*
 *
 *  cdoj, UESTC ACMICPC Online Judge
 *  Copyright (c) 2013 fish <@link lyhypacm@gmail.com>,
 *  	mzry1992 <@link muziriyun@gmail.com>
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU General Public License
 *  as published by the Free Software Foundation; either version 2
 *  of the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */

package cn.edu.uestc.acmicpc.db.condition.base;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import cn.edu.uestc.acmicpc.db.condition.base.Condition.ConditionType;
import cn.edu.uestc.acmicpc.db.condition.base.Condition.Entry;
import cn.edu.uestc.acmicpc.util.annotation.Ignore;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * We can use this class to transform conditions to database's Criterion list
 * <p/>
 * <strong>USAGE</strong>
 * <p/>
 * We can write a simple class than extends this class, and add the Exp annotation to each getter of
 * the class. If we do not do with, the field will be passed into invoke() method.
 * <p/>
 * <strong>For developer</strong>:
 * <ul>
 * <li>
 * In default, the class field's name is equal to the data field's name. If you want to map the
 * class field into another data field, use the {@code MapField} parameter please.
 * <p/>
 * For example:
 * <p/>
 * <code>
 * {@literal @}Exp(MapField = "userId", Type = ConditionType.eq)<br/>
 * public Integer getId();
 * </code></li>
 * <li>
 * If the field is other class's key field, please use the {@code MapObject} parameter.
 * <p/>
 * For example:
 * <p/>
 * <code>
 * {@literal @}Exp(Type = ConditionType.eq, MapObject = Department.class)<br/>
 * public Integer getDepartmentId;
 * </code></li>
 * <li>
 * {@code ConditionType.like} type will add % in two ends of the string, otherwise rewrite the
 * invoke method to deal with it.
 * <p/>
 * For example:
 * <p/>
 * <code>
 * public String getUserName;
 * <br/>
 * {@literal @}Override<br/>
 * public void invoke(ArrayList<Criterion> conditions) {<br/>
 * &nbsp;&nbsp;if (userName != null)<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;conditions.add(Restrictions.like("userName", "%" + userName"));<br/>
 * }
 * </code></li>
 * </ul>
 */
public abstract class BaseCondition {

  private static final Logger LOGGER = LogManager.getLogger(BaseCondition.class);

  private Long currentPage;

  @Ignore
  public Long getCurrentPage() {
    return currentPage;
  }

  public void setCurrentPage(Long currentPage) {
    this.currentPage = currentPage;
  }

  private String orderFields;
  private String orderAsc;

  @Ignore
  public String getOrderFields() {
    return orderFields;
  }

  public void setOrderFields(String orderFields) {
    this.orderFields = orderFields;
  }

  @Ignore
  public String getOrderAsc() {
    return orderAsc;
  }

  public void setOrderAsc(String orderAsc) {
    this.orderAsc = orderAsc;
  }

  /**
   * Method for user to invoke special columns
   * <p/>
   * <strong>USAGE</strong>:
   * <p/>
   * We can iterator all the fields which will be considered, and handle the condition object
   * according to the fields' values.
   *
   * @param condition conditions that to be considered
   * @throws AppException
   * @see Condition
   * @see Entry
   * @deprecated if you should do this, deal with the condition is sub class' getCondition method.
   */
  @Deprecated
  protected void invoke(Condition condition) throws AppException {
    if (orderFields != null) {
      String[] fields = orderFields.split(",");
      String[] asc = orderAsc.split(",");
      if (fields.length == asc.length) {
        for (int i = 0; i < fields.length; i++) {
          condition.addOrder(fields[i], asc[i].equals("true"));
        }
      }
    }
  }

  /**
   * Clear all field, and set then to {@code null}.
   *
   * @deprecated this method is not supported in new API, please create condition directly.
   */
  @Deprecated
  public void clear() {
    Method[] methods = getClass().getMethods();
    for (Method method : methods) {
      if (method.getName().startsWith("set")) {
        try {
          if (method.isAnnotationPresent(Ignore.class)) {
            continue;
          }
          method.invoke(this, (Object) null);
        } catch (IllegalAccessException | InvocationTargetException e) {
          LOGGER.error(e);
        }
      }
    }

    Field[] fields = getClass().getFields();
    for (Field field : fields) {
      try {
        field.set(this, null);
      } catch (IllegalAccessException ignored) {
      }
    }
  }

  /**
   * Get Condition objects from conditions
   * <p/>
   * <strong>For developers:</strong>
   * This method is not supported order now, but it's will be supported later.
   *
   * @return condition object we need
   * @throws AppException
   */
  public Condition getCondition() throws AppException {
    Condition condition = new Condition();
    if (orderFields != null) {
      String[] fields = orderFields.split(",");
      String[] asc = orderAsc.split(",");
      if (fields.length == asc.length) {
        for (int i = 0; i < fields.length; i++) {
          condition.addOrder(fields[i], asc[i].equals("true"));
        }
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
    invoke(condition);
    return condition;
  }

  /**
   * Annotation for condition expressions
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
     * Condition compare type, generate the condition clause by the compare type.
     *
     * @return condition compare type
     */
    public ConditionType type();

  }

}
