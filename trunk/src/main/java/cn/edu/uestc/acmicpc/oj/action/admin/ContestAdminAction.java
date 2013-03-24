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

import cn.edu.uestc.acmicpc.db.dao.iface.IContestDAO;
import cn.edu.uestc.acmicpc.db.view.impl.ContestView;
import cn.edu.uestc.acmicpc.ioc.dao.ContestDAOAware;
import cn.edu.uestc.acmicpc.oj.action.BaseAction;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Contest admin main page
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 * @version 1
 */
@SuppressWarnings("UnusedDeclaration")
@LoginPermit(value = Global.AuthenticationType.ADMIN)
public class ContestAdminAction extends BaseAction implements ContestDAOAware {

    private IContestDAO contestDAO;

    @SuppressWarnings("SameReturnValue")
    public String toContestList() {
        return SUCCESS;
    }

    private String editorFlag;

    public String getEditorFlag() {
        return editorFlag;
    }

    public void setEditorFlag(String editorFlag) {
        this.editorFlag = editorFlag;
    }

    public Integer getTargetContestId() {
        return targetContestId;
    }

    public void setTargetContestId(Integer targetContestId) {
        this.targetContestId = targetContestId;
    }

    public ContestView getTargetContest() {
        return targetContest;
    }

    public void setTargetContest(ContestView targetContest) {
        this.targetContest = targetContest;
    }

    private Integer targetContestId;
    private ContestView targetContest;

    @SuppressWarnings("SameReturnValue")
    public String toContestEditor() {
        editorFlag = "new";
        if (targetContestId != null) {
            try {
                targetContest = new ContestView(contestDAO.get(targetContestId));
                if (targetContest.getContestId() == null)
                    throw new AppException("Wrong problem ID!");
                editorFlag = "edit";
            } catch (AppException e) {
                redirect(getActionURL("/admin", "index"), e.getMessage());
            }
        }
        return SUCCESS;
    }

    @Override
    public void setContestDAO(IContestDAO contestDAO) {
        this.contestDAO = contestDAO;
    }
}
