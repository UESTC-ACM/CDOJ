/*
 * cdoj, UESTC ACMICPC Online Judge
 * Copyright (c) 2012  fish <@link lyhypacm@gmail.com>,
 * 	mzry1992 <@link muziriyun@gmail.com>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package cn.edu.uestc.acmicpc.db.condition.base;

import cn.edu.uestc.acmicpc.db.condition.impl.UserCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IDAO;
import cn.edu.uestc.acmicpc.util.StringUtil;
import cn.edu.uestc.acmicpc.util.annotation.Ignore;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * We can use this class to transform conditions to database's Criterion list
 * <p/>
 * <strong>USAGE</strong>
 * <p/>
 * We can write a simple class than extends this class, and add the Exp
 * annotation to each getter of the class. If we do not do with, the field
 * will be passed into invoke() method.
 * <p/>
 * <strong>For developer</strong>:
 * <ul>
 * <li>
 * In default, the class field's name is equal to the data field's name.
 * If you want to map the class field into another data field, use the
 * {@code MapField} parameter please.
 * <p/>
 * For example:
 * <p/>
 * <code>
 * {@literal @}Exp(MapField = "userId", Type = ConditionType.eq)<br/>
 * public Integer getId();
 * </code>
 * </li>
 * <li>
 * If the field is other class's key field, please use the {@code MapObject} parameter.
 * <p/>
 * For example:
 * <p/>
 * <code>
 * {@literal @}Exp(Type = ConditionType.eq, MapObject = Department.class)<br/>
 * public Integer getDepartmentId;
 * </code>
 * </li>
 * <li>
 * {@code ConditionType.like} type will add % in two ends of the string, otherwise
 * rewrite the invoke method to deal with it.
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
 * </code>
 * </li>
 * </ul>
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 */
@SuppressWarnings("UnusedDeclaration")
public abstract class BaseCondition implements ApplicationContextAware {

    /**
     * Spring application context.
     */
    @Autowired
    protected ApplicationContext applicationContext;

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
     * We can iterator all the fields which will be considered, and
     * handle the condition object according to the fields' values.
     * <p/>
     * <strong>WARN</strong>:
     * <p/>
     * When you deal with {@code joined} columns, please put then into
     * {@code condition} entity with {@code JoinedProperty} entity.
     *
     * @param condition conditions that to be considered
     * @see Condition
     * @see JoinedProperty
     */
    protected void invoke(Condition condition) {
        if (orderFields != null) {
            String[] fields = orderFields.split(",");
            String[] asc = orderAsc.split(",");
            if (fields.length == asc.length) {
                for (int i = 0; i < fields.length; i++)
                    condition.addOrder(fields[i], asc[i].equals("true"));
            }
        }
    }

    @Override
    @Ignore
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * Clear all field, and set then to {@code null}.
     */
    @SuppressWarnings("NullArgumentToVariableArgMethod")
    public void clear() {
        if (getClass() == UserCondition.class) return;
        Method[] methods = getClass().getMethods();
        for (Method method : methods) {
            if (method.getName().startsWith("set")) {
                try {
                    if (method.isAnnotationPresent(Ignore.class))
                        continue;
                    method.invoke(this, (Object) null);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
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
     * Basic condition type of database handler
     */
    public enum ConditionType {
        eq("="), gt(">"), lt("<"), ge(">="), le("<="), like(" like ");
        private String signal;

        public String getSignal() {
            return signal;
        }

        public void setSignal(String signal) {
            this.signal = signal;
        }

        private ConditionType(String signal) {
            this.signal = signal;

        }
    }

    /**
     * Get {@code Condition} objects from conditions
     *
     * @return condition object we need
     */
    public Condition getCondition() {
        return getCondition(false);
    }

    /**
     * Get Condition objects from conditions
     *
     * @param upperCaseFirst whether columns' name begin uppercase letter first
     * @return condition object we need
     */
    @SuppressWarnings({"ConstantConditions", "unchecked", "SameParameterValue"})
    Condition getCondition(boolean upperCaseFirst) {
        Condition condition = new Condition();
        Class<?> clazz = this.getClass();
        Class<?> restrictionsClass = Restrictions.class;
        Class<?> objectClass = Object.class;
        for (Method method : clazz.getMethods()) {
            if (method.getName().startsWith("get")) {
                try {
                    String fieldName = StringUtil.getFieldNameFromGetterOrSetter(method.getName());
                    Object value, keyValue;
                    Exp exp = method.getAnnotation(Exp.class);
                    if (exp == null)
                        continue;
                    try {
                        value = method.invoke(this);
                    } catch (Exception e) {
                        continue;
                    }
                    if (value == null)
                        continue;
                    keyValue = value;
                    String mapField = exp.MapField();
                    mapField = StringUtil.isNullOrWhiteSpace(mapField) ? fieldName : mapField;
                    mapField = upperCaseFirst ? mapField.substring(0, 1).toUpperCase()
                            + mapField.substring(1) : mapField;
                    boolean isJoinedProperty = false;
                    if (!exp.MapObject().equals(objectClass)) {
                        String name = exp.MapObject().getName()
                                .substring(exp.MapObject().getName().lastIndexOf('.') + 1);
                        String DAOName = name.substring(0, 1).toLowerCase()
                                + name.substring(1) + "DAO";
                        IDAO DAO = (IDAO) applicationContext.getBean(DAOName);
                        value = DAO.get((Serializable) value);
                        isJoinedProperty = true;
                    }
                    if (exp.Type().name().equals("like")) {
                        value = String.format("%%%s%%", value);
                    }
                    Criterion c = (Criterion) restrictionsClass.getMethod(exp.Type().name(),
                            String.class, Object.class).invoke(null, mapField,
                            value);
                    if (isJoinedProperty) {
                        condition.addJoinedProperty(mapField, new JoinedProperty(c, keyValue, exp.Type()));
                    } else {
                        condition.addCriterion(c);
                    }
                } catch (Exception e) {
//                    e.printStackTrace();
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
        public String MapField() default "";

        /**
         * Mapping object's class, we will get the persistence entity by
         * specific DAO object.
         *
         * @return mapping object class
         */
        public Class<?> MapObject() default Object.class;

        /**
         * Condition compare type, generate the condition clause by the
         * compare type.
         *
         * @return condition compare type
         */
        public ConditionType Type();

    }

}
