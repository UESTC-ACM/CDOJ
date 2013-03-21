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
import cn.edu.uestc.acmicpc.util.DateUtil;
import org.hibernate.criterion.Restrictions;

import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Contest database condition entity.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 */
@SuppressWarnings("UnusedDeclaration")
public class ContestCondition extends BaseCondition {
    @Exp(MapField = "contestId", Type = ConditionType.ge)
    public Integer startId;

    @Exp(MapField = "contestId", Type = ConditionType.le)
    public Integer endId;

    @Exp(Type = ConditionType.like)
    private String title;

    @Exp(Type = ConditionType.like)
    private String description;

    @Exp(Type = ConditionType.eq)
    private byte type;

    // TODO use Timestamp or Date or Integer?
    @Exp(MapField = "time", Type = ConditionType.ge)
    private Timestamp startTime;

    private Timestamp endTime;

    @Exp(Type = ConditionType.eq)
    private Boolean isVisible;

    @Override
    public void invoke(Condition condition) {
        if (endTime != null) {
            condition.addCriterion(Restrictions.lt("time", DateUtil.add(endTime, Calendar.DATE, 1)));
        }
    }
}
