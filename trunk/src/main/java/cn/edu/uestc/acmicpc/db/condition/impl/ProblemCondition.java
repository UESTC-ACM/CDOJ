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
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;

/**
 * Problem search condition.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 */
@SuppressWarnings("UnusedDeclaration")
public class ProblemCondition extends BaseCondition {
    /**
     * Start user id.
     */
    @Exp(MapField = "problemId", Type = ConditionType.ge)
    public Integer startId;
    /**
     * End user id.
     */
    @Exp(MapField = "problemId", Type = ConditionType.le)
    public Integer endId;

    /**
     * Title.
     */
    @Exp(Type = ConditionType.like)
    public String title;
    @Exp(Type = ConditionType.like)
    public String source;

    /**
     * Keyword for {@code description}, {@code input}, {@code output},
     * {@code sampleInput}, {@code sampleOutput} and {@code hint}.
     */
    public String keyword;

    @Exp(Type = ConditionType.eq)
    public Boolean isSpj;

    @Exp(Type = ConditionType.eq)
    public Boolean isVisible;

    @Exp(MapField = "difficulty", Type = ConditionType.ge)
    public Integer startDifficulty;

    @Exp(MapField = "difficulty", Type = ConditionType.le)
    public Integer endDifficulty;

    @Override
    public void invoke(Condition condition) {
        if (keyword != null) {
            String[] fields = new String[]{"description", "input", "output",
                    "sampleInput", "sampleOutput", "hint"};
            Junction junction = Restrictions.disjunction();
            for (String field : fields) {
                junction.add(Restrictions.like(field, String.format("%%%s%%", keyword)));
            }
            condition.addCriterion(junction);
        }
    }
}
