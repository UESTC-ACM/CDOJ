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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Object global methods.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 */
public class ObjectUtil {

  /**
   * Output object's fields and methods.
   *
   * @param obj object to be printed
   * @return information about the object
   */
  @SuppressWarnings("rawtypes")
  public static String toString(Object obj) {
    if (obj == null)
      return "null";
    ArrayList<String> list = new ArrayList<>();
    Class<?> cls = obj.getClass();
    for (Field f : cls.getFields()) {
      try {
        list.add(String.format("%s : %s", f.getName(), f.get(obj).toString()));
      } catch (Exception ignored) {
      }
    }
    for (Method m : cls.getMethods()) {
      try {
        String name = m.getName();
        if (!name.startsWith("get"))
          continue;
        name = name.substring(3);
        list.add(String.format("%s : %s", name, m.invoke(obj).toString()));
      } catch (Exception ignored) {
      }
    }
    if (obj instanceof Iterable)
      for (Object object : (Iterable) obj)
        list.add(toString(object));
    return String.format("{ %s }", ArrayUtil.join(list.toArray(), " , "));
  }

  public static boolean equals(Object first, Object second) {
    if (first == second) {
      return true;
    }
    if (first == null || second == null) {
      return false;
    }
    return first.equals(second);
  }
}
