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

package cn.edu.uestc.acmicpc.oj.db.condition.impl;

import cn.edu.uestc.acmicpc.oj.db.condition.base.BaseCondition;
import cn.edu.uestc.acmicpc.oj.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.oj.db.entity.Department;

/**
 * User search condition.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 * @version 1
 */
public class UserCondition extends BaseCondition {
    /**
     * Start user id.
     */
    @Exp(MapField = "userId", Type = ConditionType.ge)
    public Integer startId;
    /**
     * End user id.
     */
    @Exp(MapField = "userId", Type = ConditionType.le)
    public Integer endId;

    /**
     * User name (partly matches).
     */
    @Exp(Type = ConditionType.like)
    public String userName;

    /**
     * User's type.
     *
     * @see cn.edu.uestc.acmicpc.util.Global.AuthenticationType
     */
    @Exp(Type = ConditionType.eq)
    public Integer type;

    /**
     * User's department's id.
     *
     * @see Department
     */
    @Exp(MapField = "departmentByDepartmentId", Type = ConditionType.eq, MapObject = Department.class)
    public Integer departmentId;

    /**
     * User's school(partly matches).
     */
    @Exp(Type = ConditionType.like)
    public String school;

    @Override
    public void invoke(Condition condition) {
    }
}
