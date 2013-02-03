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

import cn.edu.uestc.acmicpc.db.dao.iface.IDAO;
import cn.edu.uestc.acmicpc.util.BeanUtil;
import cn.edu.uestc.acmicpc.util.StringUtil;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;

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
 * If the field is other class's key field, please use the {@code MapObject} parameter.
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
 * rewrite the invoke method to deal with it.
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
 * @version 5
 */
@Transactional
public abstract class BaseCondition implements ApplicationContextAware {

    /**
     * Spring application context.
     */
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * Method for user to invoke special columns
     *
     * @param condition conditions that to be considered
     */
    public abstract void invoke(Condition condition);

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void clear() {
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
    @SuppressWarnings({"ConstantConditions", "unchecked"})
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
                    String name = exp.MapObject().getName()
                            .substring(exp.MapObject().getName().lastIndexOf('.') + 1);
                    String DAOName = name.substring(0, 1).toLowerCase()
                            + name.substring(1) + "DAO";
                    IDAO DAO = (IDAO) applicationContext.getBean(DAOName);
                    value = DAO.get((Serializable) value);
                }
                if (exp.Type().name().equals("like")) {
                    value = String.format("%%%s%%", value);
                }
                Criterion c = (Criterion) restrictionsClass.getMethod(exp.Type().name(),
                        String.class, Object.class).invoke(null, mapField,
                        value);
                condition.addCriterion(c);
            } catch (Exception e) {
                e.printStackTrace();
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
