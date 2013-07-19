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

import java.lang.reflect.Method;

/**
 * Global method for get method and field from objects.
 * 
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 */
public class ReflectionUtil {

  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static Method getMethodByAnnotation(Class<?> clazz, Class annotation) {
    Method[] methods = clazz.getMethods();
    for (Method method : methods) {
      if (method.getAnnotation(annotation) != null)
        return method;
    }
    return null;
  }

  /**
   * Convert a string value to specific type.
   * 
   * @param value string value
   * @param targetType target type class
   * @return target value
   */
  public static Object valueOf(String value, Class<?> targetType) {
    try {
      Method convertMethod = targetType.getMethod("valueOf", String.class);
      return convertMethod.invoke(null, value);
    } catch (Exception e) {
      return value;
    }
  }
}
