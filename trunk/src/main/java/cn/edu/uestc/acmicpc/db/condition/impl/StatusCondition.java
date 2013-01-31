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

package cn.edu.uestc.acmicpc.db.condition.impl;

import cn.edu.uestc.acmicpc.db.condition.base.BaseCondition;
import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.db.entity.User;

/**
 * Status search condition.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 * @version 1
 */
@SuppressWarnings("UnusedDeclaration")
public class StatusCondition extends BaseCondition {
    /**
     * Start status id.
     */
    @Exp(MapField = "statusId", Type = ConditionType.ge)
    public Integer startId;
    /**
     * End status id.
     */
    @Exp(MapField = "statusId", Type = ConditionType.le)
    public Integer endId;

    @Exp(Type = ConditionType.eq, MapObject = User.class)
    public Integer userId;

    @Exp(Type = ConditionType.eq, MapObject = Problem.class)
    public Integer problemId;

    @Override
    public void invoke(Condition condition) {
    }
}
