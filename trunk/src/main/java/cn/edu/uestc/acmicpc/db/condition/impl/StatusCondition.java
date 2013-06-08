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
import cn.edu.uestc.acmicpc.db.dao.impl.UserDAO;
import cn.edu.uestc.acmicpc.db.entity.Language;
import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.ioc.condition.UserConditionAware;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;
import java.util.List;

/**
 * Status search condition.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 */
@SuppressWarnings("UnusedDeclaration")
public class StatusCondition extends BaseCondition implements UserConditionAware {
    /**
     * Start status id.
     */
    private Integer startId;
    /**
     * End status id.
     */
    private Integer endId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * User's id.
     */

    private Integer userId;

    /**
     * User's name
     */
    private String userName;

    /**
     * Problem's id.
     */
    private Integer problemId;

    /**
     * Language's id.
     */
    private Integer languageId;

    /**
     * Contest's id.
     */
    private Integer contestId;

    /**
     * Judging result list(<strong>PRIMARY</strong>).
     */
    private List<Global.OnlineJudgeReturnType> result = new LinkedList<>();

    @Exp(MapField = "statusId", Type = ConditionType.ge)
    public Integer getStartId() {
        return startId;
    }

    public void setStartId(Integer startId) {
        this.startId = startId;
    }

    @Exp(MapField = "statusId", Type = ConditionType.le)
    public Integer getEndId() {
        return endId;
    }

    public void setEndId(Integer endId) {
        this.endId = endId;
    }

    @Exp(MapField = "userByUserId", Type = ConditionType.eq, MapObject = User.class)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Exp(MapField = "problemByProblemId", Type = ConditionType.eq, MapObject = Problem.class)
    public Integer getProblemId() {
        return problemId;
    }

    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
    }

    @Exp(MapField = "languageByLanguageId", Type = ConditionType.eq, MapObject = Language.class)
    public Integer getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }

    public Integer getContestId() {
        return contestId;
    }

    public void setContestId(Integer contestId) {
        this.contestId = contestId;
    }

    public List<Global.OnlineJudgeReturnType> getResult() {
        return result;
    }

    public void setResult(List<Global.OnlineJudgeReturnType> result) {
        this.result = result;
    }

    /**
     * Judging result int format.
     */
    private Integer resultId;

    public Integer getResultId() {
        return resultId;
    }

    public void setResultId(Integer resultId) {
        this.resultId = resultId;
    }

    @Autowired
    UserCondition userCondition;

    /**
     * <strong>WARN</strong>: if we set {@code contestId} with {@code -1},
     * that means we will query all records with contestId is {@code null}.
     *
     * @param condition conditions that to be considered
     */
    @SuppressWarnings("unchecked")
    @Override
    public void invoke(Condition condition) {
        super.invoke(condition);

        if (contestId != null) {
            if (contestId == -1)
                condition.addCriterion(Restrictions.isNull("contestByContestId"));
            else
                condition.addCriterion(Restrictions.eq("contestByContestId", contestId));
        }

        if (userName != null) {
            UserDAO userDAO = applicationContext.getBean("userDAO", UserDAO.class);
            userCondition.clear();
            userCondition.setUserName(userName);
            try {
                List<User> users = (List<User>) userDAO.findAll(userCondition.getCondition());
                if (users != null && !users.isEmpty())
                    condition.addCriterion(Restrictions.eq("userByUserId", users.get(0)));
            } catch (AppException ignored) {
            }
        }

        if (result != null && !result.isEmpty() || resultId != null) {
            if (result != null && !result.isEmpty()) {
                Junction junction = Restrictions.disjunction();
                for (Global.OnlineJudgeReturnType onlineJudgeReturnType : result)
                    junction.add(Restrictions.eq("result", onlineJudgeReturnType.ordinal()));
                condition.addCriterion(junction);
            } else {
                condition.addCriterion(Restrictions.eq("result", resultId));
            }
        }
    }

    @Override
    public void setUserCondition(UserCondition userCondition) {
        this.userCondition = userCondition;
    }

    @Override
    public UserCondition getUserCondition() {
        return userCondition;
    }
}
