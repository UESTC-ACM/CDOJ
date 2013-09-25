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

package cn.edu.uestc.acmicpc.db.condition.impl;

import cn.edu.uestc.acmicpc.db.condition.base.BaseCondition;
import cn.edu.uestc.acmicpc.db.condition.base.Condition.ConditionType;
import cn.edu.uestc.acmicpc.db.entity.Department;
import cn.edu.uestc.acmicpc.util.Global.AuthenticationType;

/**
 * User search condition.
 */
public class UserCondition extends BaseCondition {

  public UserCondition() {
    super("userId");
  }

  /**
   * Start user id.
   */
  @Exp(mapField = "userId", type = ConditionType.GREATER_OR_EQUALS)
  public Integer startId;

  /**
   * End user id.
   */
  @Exp(mapField = "userId", type = ConditionType.LESS_OR_EQUALS)
  public Integer endId;

  /**
   * User name (partly matches).
   */
  @Exp(type = ConditionType.LIKE)
  public String userName;

  /**
   * User's type.
   *
   * @see AuthenticationType
   */
  @Exp(type = ConditionType.EQUALS)
  public Integer type;

  /**
   * User's department's id.
   *
   * @see Department
   */
  @Exp(type = ConditionType.EQUALS)
  public Integer departmentId;

  /**
   * User's school(partly matches).
   */
  @Exp(type = ConditionType.LIKE)
  public String school;
}
