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
import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.entity.Department;

/**
 * User search condition.
 * 
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 */
public class UserCondition extends BaseCondition {

  /**
   * Start user id.
   */
  private Integer startId;
  /**
   * End user id.
   */
  private Integer endId;

  /**
   * User name (partly matches).
   */
  private String userName;

  /**
   * User's type.
   * 
   * @see cn.edu.uestc.acmicpc.util.Global.AuthenticationType
   */
  private Integer type;

  /**
   * User's department's id.
   * 
   * @see Department
   */
  private Integer departmentId;

  public String getSchool() {
    return school;
  }

  public void setSchool(String school) {
    this.school = school;
  }

  @Exp(MapField = "departmentByDepartmentId", Type = ConditionType.eq, MapObject = Department.class)
  public
      Integer getDepartmentId() {
    return departmentId;
  }

  public void setDepartmentId(Integer departmentId) {
    this.departmentId = departmentId;
  }

  @Exp(Type = ConditionType.eq)
  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  @Exp(Type = ConditionType.like)
  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  @Exp(MapField = "userId", Type = ConditionType.le)
  public Integer getEndId() {
    return endId;
  }

  public void setEndId(Integer endId) {
    this.endId = endId;
  }

  @Exp(MapField = "userId", Type = ConditionType.ge)
  public Integer getStartId() {
    return startId;
  }

  public void setStartId(Integer startId) {
    this.startId = startId;
  }

  /**
   * User's school(partly matches).
   */
  @Exp(Type = ConditionType.like)
  private String school;

  @Override
  public void invoke(Condition condition) {
    super.invoke(condition);
  }
}
