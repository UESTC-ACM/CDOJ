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

package cn.edu.uestc.acmicpc.db.condition;

import cn.edu.uestc.acmicpc.util.StringUtil;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * We can use this class to transform conditions to database's Criterion list
 * <p/>
 * <strong>USAGE</strong>
 * <p/>
 * We can write a simple class than extends this class, and add the Exp
 * annotation to each field of the class. If we do not do with, the field
 * will be passed into invoke() method.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 * @version 1
 */
@SuppressWarnings("UnusedDeclaration")
public abstract class BaseCondition {

    /**
     * method for user to invoke special columns
     *
     * @param conditions conditions that to be considered
     */
    public abstract void invoke(ArrayList<Criterion> conditions);

    /**
     * basic condition type of database handler
     */
    public enum ConditionType {
        eq, gt, lt, ge, le, like
    }

    /**
     * get Criterion objects from conditions
     *
     * @return criterion list we need
     */
    public List<Criterion> getCriterionList() {
        return getCriterionList(true);
    }

    /**
     * get Criterion objects from conditions
     *
     * @param upperCaseFirst whether columns' name begin uppercase letter first
     * @return criterion list we need
     */
    @SuppressWarnings("ConstantConditions")
    public List<Criterion> getCriterionList(boolean upperCaseFirst) {
        ArrayList<Criterion> list = new ArrayList<Criterion>();
        Class<?> cls = this.getClass();
        Class<?> rcls = Restrictions.class;
        Class<?> ocls = Object.class;
        for (Field f : cls.getFields()) {
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
                if (!exp.MapObject().equals(ocls)) {
                    Object obj = exp.MapObject().newInstance();
                    Method m = exp.MapObject()
                            .getMethod("setId", Integer.class);
                    m.invoke(obj, value);
                    value = obj;
                }
                value = exp.Type().name().equals("like") ? String.format(
                        "%%%s%%", value) : value;
                Criterion c = (Criterion) rcls.getMethod(exp.Type().name(),
                        String.class, Object.class).invoke(null, mapField,
                        value);
                list.add(c);
            } catch (Exception ignored) {
            }
        }
        invoke(list);
        return list;
    }

    /**
     * annotation for condition expressions
     */
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Exp {
        public String MapField() default "";

        public Class<?> MapObject() default Object.class;

        public ConditionType Type();

    }

}
