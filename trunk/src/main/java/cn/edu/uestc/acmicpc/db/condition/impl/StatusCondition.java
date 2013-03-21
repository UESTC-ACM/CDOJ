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
import cn.edu.uestc.acmicpc.db.entity.Language;
import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.util.Global;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;

import java.util.LinkedList;
import java.util.List;

/**
 * Status search condition.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
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

    /**
     * User's id.
     */
    @Exp(MapField = "userByUserId", Type = ConditionType.eq, MapObject = User.class)
    public Integer userId;

    /**
     * Problem's id.
     */
    @Exp(MapField = "problemByProblemId", Type = ConditionType.eq, MapObject = Problem.class)
    public Integer problemId;

    /**
     * Language's id.
     */
    @Exp(MapField = "languageByLanguageId", Type = ConditionType.eq, MapObject = Language.class)
    public Integer languageId;

    /**
     * Contest's id.
     */
    public Integer contestId;

    /**
     * Judging result list(<strong>PRIMARY</strong>).
     */
    public List<Global.OnlineJudgeReturnType> result = new LinkedList<>();

    /**
     * Judging result int format.
     */
    public Integer iResult;

    @Override
    public void invoke(Condition condition) {
        if (contestId == null)
            contestId = -1;

        if (contestId == -1)
            condition.addCriterion(Restrictions.isNull("contestByContestId"));
        else
            condition.addCriterion(Restrictions.eq("contestByContestId", contestId));

        if (result != null && !result.isEmpty() || iResult != null) {
            if (result != null && !result.isEmpty()) {
                Junction junction = Restrictions.disjunction();
                for (Global.OnlineJudgeReturnType onlineJudgeReturnType : result)
                    junction.add(Restrictions.eq("result", onlineJudgeReturnType.ordinal()));
                condition.addCriterion(junction);
            } else {
                condition.addCriterion(Restrictions.eq("result", iResult));
            }
        }
    }
}
