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

import java.io.Serializable;

/**
 * Base view entity.
 *
 * @param <Entity> Entity class for this view.
 */
public class View<Entity extends Serializable> {

  /**
   * Fetch data from entity.
   * <p/>
   * TODO(mzry1992): please write codes to transfer dto to view in each view's constructor, and
   * do not call super().
   *
   * @param entity specific entity
   * @deprecated this constructor is deprecated, please build the view manually.
   */
  @Deprecated
  protected View(Entity entity) {
//    if (entity == null)
//      return;
//    Method[] methods = getClass().getMethods();
//    for (Method method : methods) {
//      if (method.getName().startsWith("set")) {
//        String name =
//            StringUtil.getGetterOrSetter(StringUtil.MethodType.GETTER, method.getName()
//                .substring(3));
//        Ignore ignore = method.getAnnotation(Ignore.class);
//        if (ignore == null || !ignore.value()) {
//          try {
//            Method getter = entity.getClass().getMethod(name);
//            if (getter.getReturnType().equals(String.class)) {
//              // Trim useless blanks
//              method.invoke(this, StringEscapeUtils.escapeHtml4((String) getter.invoke(entity))
//                  .trim());
//            } else {
//              method.invoke(this, getter.invoke(entity));
//            }
//          } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ignored) {
//          }
//        }
//      }
//    }
  }

  public View() {
  }
}
