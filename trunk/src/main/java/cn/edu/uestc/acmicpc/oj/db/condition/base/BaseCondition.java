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

package cn.edu.uestc.acmicpc.oj.db.condition.base;

import cn.edu.uestc.acmicpc.oj.annotation.IdSetter;
import cn.edu.uestc.acmicpc.oj.util.ReflectionUtil;
import cn.edu.uestc.acmicpc.oj.util.StringUtil;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * We can use this class to transform conditions to database's Criterion list
 * <p/>
 * <strong>USAGE</strong>
 * <p/>
 * We can write a simple class than extends this class, and add the Exp
 * annotation to each field of the class. If we do not do with, the field
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
 * public Integer id;
 * </code>
 * </li>
 * <li>
 * If the field is other class's key field, please mark the setter of the
 * field with {@code IdSetter} annotation and user the {@code MapObject} parameter.
 * <p/>
 * For example:
 * <p/>
 * <code>
 * {@literal @}Exp(Type = ConditionType.eq, MapObject = Department.class)<br/>
 * public Integer departmentId;
 * </code>
 * </li>
 * <li>
 * {@code ConditionType.like} type will add % in two ends of the string, otherwise
 * rewrite the invoke method to deal with ti.
 * <p/>
 * For example:
 * <p/>
 * <code>
 * public String userName;
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
 * @version 3
 */
@SuppressWarnings("UnusedDeclaration")
public abstract class BaseCondition {

    /**
     * Method for user to invoke special columns
     *
     * @param condition conditions that to be considered
     */
    public abstract void invoke(Condition condition);

    /**
     * Basic condition type of database handler
     */
    public enum ConditionType {
        eq, gt, lt, ge, le, like
    }

    /**
     * Get Criterion objects from conditions
     *
     * @return criterion list we need
     */
    public Condition getCondition() {
        return getCondition(false);
    }

    /**
     * Get Criterion objects from conditions
     *
     * @param upperCaseFirst whether columns' name begin uppercase letter first
     * @return criterion list we need
     */
    @SuppressWarnings("ConstantConditions")
    public Condition getCondition(boolean upperCaseFirst) {
        Condition condition = new Condition();
        Class<?> clazz = this.getClass();
        Class<?> restrictionsClass = Restrictions.class;
        Class<?> objectClass = Object.class;
        for (Field f : clazz.getFields()) {
            Object value;
            Exp exp = f.getAnnotation(Exp.class);
            try {
                value = f.get(this);
            } catch (Exception e) {
                continue;
            }
            if (value == null || exp == null)
                continue;
            String mapField = exp.MapField();
            mapField = StringUtil.isNullOrWhiteSpace(mapField) ? f.getName()
                    : mapField;
            mapField = upperCaseFirst ? mapField.substring(0, 1).toUpperCase()
                    + mapField.substring(1) : mapField;
            try {
                if (!exp.MapObject().equals(objectClass)) {
                    Object obj = exp.MapObject().newInstance();
                    Method method = ReflectionUtil.getMethodByAnnotation(exp.MapObject(), IdSetter.class);
                    method.invoke(obj, value);
                    value = obj;
                }
                value = exp.Type().name().equals("like") ? String.format(
                        "%%%s%%", value) : value;
                Criterion c = (Criterion) restrictionsClass.getMethod(exp.Type().name(),
                        String.class, Object.class).invoke(null, mapField,
                        value);
                condition.addCriterion(c);
            } catch (Exception ignored) {
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
        public String MapField() default "";

        public Class<?> MapObject() default Object.class;

        public ConditionType Type();

    }

}
