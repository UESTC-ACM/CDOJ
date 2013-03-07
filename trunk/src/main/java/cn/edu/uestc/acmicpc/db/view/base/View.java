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

package cn.edu.uestc.acmicpc.db.view.base;

import cn.edu.uestc.acmicpc.util.annotation.Ignore;
import cn.edu.uestc.acmicpc.util.StringUtil;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Base view entity.
 * <p/>
 * <strong>USAGE</strong>:
 * Create subclass and set {@code ignore} tag for files' getter which we want
 * to initialize ourselves.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 * @version 6
 */
public class View<Entity extends Serializable> {

    /**
     * Fetch data from entity.
     *
     * @param entity specific entity
     */
    public View(Entity entity) {
        if (entity == null)
            return;
        Method[] methods = getClass().getMethods();
        for (Method method : methods) {
            if (method.getName().startsWith("set")) {
                String name = StringUtil.getGetterOrSetter(StringUtil.MethodType.GETTER,
                        method.getName().substring(3));
                Ignore ignore = method.getAnnotation(Ignore.class);
                if (ignore == null || !ignore.value()) {
                    try {
                        Method getter = entity.getClass().getMethod(name);
                        if (getter.getReturnType().equals(String.class)) {
                            //Trim useless blanks
                            method.invoke(this, StringEscapeUtils.escapeHtml4((String) getter.invoke(entity)).trim());
                        } else {
                            method.invoke(this, getter.invoke(entity));
                        }
                    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ignored) {
                    }
                }
            }
        }
    }
}
