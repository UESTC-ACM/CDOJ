/*
 *
 *  * cdoj, UESTC ACMICPC Online Judge
 *  * Copyright (c) 2013 fish <@link lyhypacm@gmail.com>,
 *  * 	mzry1992 <@link muziriyun@gmail.com>
 *  *
 *  * This program is free software; you can redistribute it and/or
 *  * modify it under the terms of the GNU General Public License
 *  * as published by the Free Software Foundation; either version 2
 *  * of the License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program; if not, write to the Free Software
 *  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */

package cn.edu.uestc.acmicpc.util;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;

import javax.persistence.Id;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * All actions for database.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 */
@SuppressWarnings("UnusedDeclaration")
public class DatabaseUtil {
    /**
     * Put all criterion in the criterion list into criteria object.
     *
     * @param criteria      criteria object
     * @param criterionList criterion list
     */
    public static void putCriterionIntoCriteria(Criteria criteria,
                                                Iterable<Criterion> criterionList) {
        if (criteria == null || criterionList == null)
            return;
        for (Criterion criterion : criterionList)
            criteria.add(criterion);
    }

    /**
     * Get entity's key value.
     *
     * @param object entity object
     * @return entity's key value.
     */
    public static Object getKeyValue(Object object) {
        for (Method method : object.getClass().getMethods()) {
            if (method.getName().startsWith("get")) {
                if (method.isAnnotationPresent(Id.class)) {
                    try {
                        return method.invoke(object);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        return null;
                    }
                }
            }
        }
        return null;
    }
}
