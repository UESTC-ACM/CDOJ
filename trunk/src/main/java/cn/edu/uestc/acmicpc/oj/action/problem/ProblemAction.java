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

package cn.edu.uestc.acmicpc.oj.action.problem;

import cn.edu.uestc.acmicpc.db.dao.iface.IProblemDAO;
import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.db.view.impl.ProblemView;
import cn.edu.uestc.acmicpc.ioc.dao.ProblemDAOAware;
import cn.edu.uestc.acmicpc.oj.action.BaseAction;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * action for show problem.
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
@LoginPermit(NeedLogin = false)
public class ProblemAction extends BaseAction implements ProblemDAOAware {

    /**
     * ProblemDAO for problem search.
     */
    @Autowired
    private IProblemDAO problemDAO;

    /**
     * Setter of ProblemDAO for Ioc.
     *
     * @param problemDAO newly problemDAO
     */
    public void setProblemDAO(IProblemDAO problemDAO) {
        this.problemDAO = problemDAO;
    }

    /**
     * save target problem id
     */
    private Integer targetProblemId;

    /**
     * save problem to edit
     */
    private ProblemView targetProblem;

    @SuppressWarnings("UnusedDeclaration")
    public Integer getTargetProblemId() {
        return targetProblemId;
    }

    public void setTargetProblemId(Integer targetProblemId) {
        this.targetProblemId = targetProblemId;
    }

    @SuppressWarnings("UnusedDeclaration")
    public ProblemView getTargetProblem() {
        return targetProblem;
    }

    /**
     * Go to problem editor view!
     *
     * @return <strong>SUCCESS</strong> signal
     */
    @SuppressWarnings("SameReturnValue")
    public String toProblem() {
        if (targetProblemId != null) {
            try {
                Problem problem = problemDAO.get(targetProblemId);
                if (!problem.getIsVisible())
                    throw new AppException("Problem doesn't exist");
                targetProblem = new ProblemView(problem);
                if (targetProblem.getProblemId() == null)
                    throw new AppException("Wrong problem ID!");
            } catch (AppException e) {
                return redirect(getActionURL("/problem", "index"), e.getMessage());
            }
        }
        return SUCCESS;
    }

}
