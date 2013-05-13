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
    private Integer startId;
    /**
     * End user id.
     */
    private Integer endId;

    /**
     * Title.
     */
    private String title;
    private String source;

    /**
     * Keyword for {@code description}, {@code input}, {@code output},
     * {@code sampleInput}, {@code sampleOutput} and {@code hint}.
     */
    private String keyword;

    private Boolean isSpj;

    private Boolean isVisible;

    private Integer startDifficulty;

    private Boolean isTitleEmpty;

    public Boolean getIsTitleEmpty() {
        return isTitleEmpty;
    }

    public void setIsTitleEmpty(Boolean isTitleEmpty) {
        this.isTitleEmpty = isTitleEmpty;
    }

    @Exp(MapField = "difficulty", Type = ConditionType.le)
    public Integer getEndDifficulty() {
        return endDifficulty;
    }

    public void setEndDifficulty(Integer endDifficulty) {
        this.endDifficulty = endDifficulty;
    }

    @Exp(MapField = "difficulty", Type = ConditionType.ge)
    public Integer getStartDifficulty() {
        return startDifficulty;
    }

    public void setStartDifficulty(Integer startDifficulty) {
        this.startDifficulty = startDifficulty;
    }

    @Exp(Type = ConditionType.eq)
    public Boolean getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(Boolean visible) {
        isVisible = visible;
    }

    @Exp(Type = ConditionType.eq)
    public Boolean getIsSpj() {
        return isSpj;
    }

    public void setIsSpj(Boolean spj) {
        isSpj = spj;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Exp(Type = ConditionType.like)
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Exp(Type = ConditionType.like)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Exp(MapField = "problemId", Type = ConditionType.le)
    public Integer getEndId() {
        return endId;
    }

    public void setEndId(Integer endId) {
        this.endId = endId;
    }

    @Exp(MapField = "problemId", Type = ConditionType.ge)
    public Integer getStartId() {
        return startId;
    }

    public void setStartId(Integer startId) {
        this.startId = startId;
    }

    private Integer endDifficulty;

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
        if (isTitleEmpty != null) {
            if (isTitleEmpty) {
                condition.addCriterion(Restrictions.isEmpty("title"));
            } else {
                condition.addCriterion(Restrictions.isNotEmpty("title"));
            }
        }
    }
}
