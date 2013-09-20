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

package cn.edu.uestc.acmicpc.checker.base;

import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Checker for file uploader or unzip tools.
 * <p/>
 * We can use {@code Checker} Entity when upload files or unzip a {@code zip} file.
 * <p/>
 * Override {@code check} method for checker working.
 *
 * @param <Entity> Entity type
 */
public interface Checker<Entity> {

  /**
   * Check certain entity, if the entity is invalid, throws an {@code AppException} object.
   *
   * @param entity entity to be checked
   * @throws AppException if the entity is invalid.
   */
  public void check(Entity entity) throws AppException;
}
