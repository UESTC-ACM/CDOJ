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

package cn.edu.uestc.acmicpc.oj.action.admin;

import cn.edu.uestc.acmicpc.db.dao.iface.IProblemDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.ProblemDTO;
import cn.edu.uestc.acmicpc.db.view.impl.ProblemDataView;
import cn.edu.uestc.acmicpc.ioc.dao.ProblemDAOAware;
import cn.edu.uestc.acmicpc.oj.action.file.FileUploadAction;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * description
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 * @version 2
 */
@LoginPermit(Global.AuthenticationType.ADMIN)
public class ProblemDataAdminAction extends FileUploadAction implements ProblemDAOAware {
    private IProblemDAO problemDAO;
    private Integer targetProblemId;

    private ProblemDTO problemDTO;

    private ProblemDataView problemDataView;

    @SuppressWarnings("UnusedDeclaration")
    public ProblemDataView getProblemDataView() {
        return problemDataView;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setProblemDataView(ProblemDataView problemDataView) {
        this.problemDataView = problemDataView;
    }

    @SuppressWarnings("UnusedDeclaration")
    public ProblemDTO getProblemDTO() {
        return problemDTO;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setProblemDTO(ProblemDTO problemDTO) {
        this.problemDTO = problemDTO;
    }

    @SuppressWarnings("UnusedDeclaration")
    public Integer getTargetProblemId() {
        return targetProblemId;
    }

    public void setTargetProblemId(Integer targetProblemId) {
        this.targetProblemId = targetProblemId;
    }

    /**
     * Go to problem data editor view!
     *
     * @return <strong>SUCCESS</strong> signal
     */
    public String toProblemDataEditor() {
        try {
            problemDataView = new ProblemDataView(problemDAO.get(targetProblemId));
        } catch (AppException e) {
            return setError("Specific problem doesn't exist.");
        }
        return SUCCESS;
    }

    /**
     * Update problem's limits and data.
     *
     * @return <strong>SUCCESS</strong> signal
     */
    public String updateProblemData() {
        return SUCCESS;
    }

    @Override
    public void setProblemDAO(IProblemDAO problemDAO) {
        this.problemDAO = problemDAO;
    }
}
