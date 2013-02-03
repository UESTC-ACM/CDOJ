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

package cn.edu.uestc.acmicpc.service.entity;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.impl.StatusCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.*;
import cn.edu.uestc.acmicpc.db.entity.CompileInfo;
import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.db.entity.Status;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.ioc.condition.StatusConditionAware;
import cn.edu.uestc.acmicpc.ioc.dao.CompileinfoDAOAware;
import cn.edu.uestc.acmicpc.ioc.dao.ProblemDAOAware;
import cn.edu.uestc.acmicpc.ioc.dao.StatusDAOAware;
import cn.edu.uestc.acmicpc.ioc.dao.UserDAOAware;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.hibernate.criterion.Projections;

/**
 * Judge item for single problem.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 * @version 3
 */
public class JudgeItem implements CompileinfoDAOAware, StatusDAOAware, UserDAOAware, ProblemDAOAware, StatusConditionAware {
    public Status status;
    public CompileInfo compileInfo;
    /**
     * Status database condition.
     */
    StatusCondition statusCondition;

    /**
     * Compileinfo DAO for database query.
     */
    private ICompileinfoDAO compileinfoDAO;
    /**
     * Status DAO for database query.
     */
    private IStatusDAO statusDAO;
    /**
     * User DAO for database query.
     */
    private IUserDAO userDAO;
    /**
     * Problem DAO for database query.
     */
    private IProblemDAO problemDAO;

    public int parseLanguage() {
        String extension = status.getLanguageByLanguageId().getExtension();
        switch (extension) {
            case "cc":
                return 0;
            case "c":
                return 1;
            case "java":
                return 2;
            default:
                return 3;
        }
    }

    @SuppressWarnings("UnusedDeclaration")
    public String getSourceName() {
        if ("java".equals(status.getLanguageByLanguageId().getExtension()))
            return "Main.java";
        else
            return Integer.toString(status.getStatusId()) + status.getLanguageByLanguageId().getExtension();

    }

    /**
     * Update database for item.
     */
    public void update() {
        if (compileInfo != null) {
            if (compileInfo.getContent().length() > 65535)
                compileInfo.setContent(compileInfo.getContent().substring(0, 65534));
            try {
                compileinfoDAO.add(compileInfo);
                status.setCompileInfoByCompileInfoId(compileInfo);
            } catch (AppException ignored) {
            }
        }
        try {
            statusDAO.update(status);
        } catch (AppException ignored) {
        }
        if (status.getResult() == Global.OnlineJudgeReturnType.OJ_AC.ordinal()) {
            try {
                User user = status.getUserByUserId();
                statusCondition.userId = user.getUserId();
                Condition condition = statusCondition.getCondition();
                condition.addProjection(Projections.countDistinct("problemId"));
                Long count = statusDAO.customCount(condition);
                user.setSolved((int) count.longValue());
                userDAO.update(user);
                statusCondition = new StatusCondition();
                Problem problem = status.getProblemByProblemId();
                statusCondition.problemId = problem.getProblemId();
                condition = statusCondition.getCondition();
                condition.addProjection(Projections.countDistinct("userId"));
                count = statusDAO.customCount(condition);
                problem.setSolved((int) count.longValue());
                problemDAO.update(problem);
            } catch (Exception e) {
                System.out.println(e.toString());
            }

        }
    }

    @Override
    public void setStatusCondition(StatusCondition statusCondition) {
        this.statusCondition = statusCondition;
    }

    @Override
    public void setCompileinfoDAO(ICompileinfoDAO compileinfoDAO) {
        this.compileinfoDAO = compileinfoDAO;
    }

    @Override
    public void setProblemDAO(IProblemDAO problemDAO) {
        this.problemDAO = problemDAO;
    }

    @Override
    public void setStatusDAO(IStatusDAO statusDAO) {
        this.statusDAO = statusDAO;
    }

    @Override
    public void setUserDAO(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
