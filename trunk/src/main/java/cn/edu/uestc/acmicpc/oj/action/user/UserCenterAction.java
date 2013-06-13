/*
 * cdoj, UESTC ACMICPC Online Judge
 *
 * Copyright (c) 2013 fish <@link lyhypacm@gmail.com>,
 * mzry1992 <@link muziriyun@gmail.com>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package cn.edu.uestc.acmicpc.oj.action.user;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.impl.ProblemCondition;
import cn.edu.uestc.acmicpc.db.condition.impl.StatusCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IProblemDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IStatusDAO;
import cn.edu.uestc.acmicpc.db.dao.impl.ProblemDAO;
import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.db.view.impl.UserView;
import cn.edu.uestc.acmicpc.ioc.condition.ProblemConditionAware;
import cn.edu.uestc.acmicpc.ioc.condition.StatusConditionAware;
import cn.edu.uestc.acmicpc.ioc.dao.ProblemDAOAware;
import cn.edu.uestc.acmicpc.ioc.dao.StatusDAOAware;
import cn.edu.uestc.acmicpc.oj.action.BaseAction;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Action for user center
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
@SuppressWarnings("UnusedDeclaration")
@LoginPermit(NeedLogin = false)
public class UserCenterAction extends BaseAction
        implements StatusDAOAware,
        StatusConditionAware, ProblemDAOAware {

    private String targetUserName;

    private UserView targetUser;

    public UserView getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(UserView targetUser) {
        this.targetUser = targetUser;
    }

    public String getTargetUserName() {
        return targetUserName;
    }

    public void setTargetUserName(String targetUserName) {
        this.targetUserName = targetUserName;
    }

    /**
     * To enter user center.
     *
     * @return {@code SUCCESS} flag
     */
    public String toUserCenter() {
        try {
            if (targetUserName == null)
                throw new AppException("User name is empty!");
            User user = userDAO.getEntityByUniqueField("userName", targetUserName);
            if (user == null)
                throw new AppException("No such user!");
            targetUser = new UserView(user);
        } catch (AppException e) {
            return redirect(getActionURL("/", "index"), e.getMessage());
        } catch (Exception e) {
            return redirect(getActionURL("/", "index"), "Unknown exception occurred.");
        }
        return SUCCESS;
    }

    /**
     * Get user's problem status list.
     * <p/>
     * Find all problem that target user passed or failed.
     * <p/>
     * <strong>JSON output</strong>:
     * <ul>
     * <li>
     * For success: {"result":"ok", "problemStatus":<strong>ProblemStatus object</strong>,
     * "problemCount":<strong>Tot problems</strong>}
     * </li>
     * <li>
     * For error: {"result":"error", "error_msg":<strong>error message</strong>}
     * </li>
     * </ul>
     *
     * @return <strong>JSON</strong> signal
     */
    @SuppressWarnings("unchecked")
    public String toUserProblemState() {
        try {
            if (targetUserName == null)
                throw new AppException("User name is empty!");
            User currentUser = userDAO.getEntityByUniqueField("userName", targetUserName);
            if (currentUser == null)
                throw new AppException("No such user!");

            statusCondition.clear();
            statusCondition.setUserId(currentUser.getUserId());
            statusCondition.setResultId(null);
            Condition condition = statusCondition.getCondition();
            condition.addProjection(Projections.groupProperty("problemByProblemId.problemId"));
            List<Integer> results = (List<Integer>) statusDAO.findAll(condition);
            for (Integer result : results)
                problemStatus.put(result, Global.AuthorStatusType.FAIL);

            statusCondition.clear();
            statusCondition.setUserId(currentUser.getUserId());
            statusCondition.setResultId(Global.OnlineJudgeReturnType.OJ_AC.ordinal());
            condition = statusCondition.getCondition();
            condition.addProjection(Projections.groupProperty("problemByProblemId.problemId"));
            results = (List<Integer>) statusDAO.findAll(condition);
            for (Integer result : results)
                problemStatus.put(result, Global.AuthorStatusType.PASS);

            json.put("result", "ok");
            json.put("problemStatus", problemStatus);
            json.put("problemCount", problemDAO.count());
        } catch (AppException e) {
            json.put("result", "error");
            json.put("error_msg", e.getMessage());
        } catch (Exception e) {
            json.put("result", "error");
            e.printStackTrace();
            json.put("error_msg", "Unknown exception occurred.");
        }
        return JSON;

    }

    @Autowired
    private StatusCondition statusCondition;
    @Autowired
    private IStatusDAO statusDAO;

    @Override
    public void setStatusCondition(StatusCondition statusCondition) {
        this.statusCondition = statusCondition;
    }

    @Override
    public StatusCondition getStatusCondition() {
        return statusCondition;
    }

    @Override
    public void setStatusDAO(IStatusDAO statusDAO) {
        this.statusDAO = statusDAO;
    }

    @Autowired
    private IProblemDAO problemDAO;

    @Override
    public void setProblemDAO(IProblemDAO problemDAO) {
        this.problemDAO = problemDAO;
    }
}
