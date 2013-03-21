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
import cn.edu.uestc.acmicpc.ioc.dao.ContestDAOAware;
import cn.edu.uestc.acmicpc.oj.action.BaseAction;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;

/**
 * Contest admin main page
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 * @version 1
 */
@LoginPermit(value = Global.AuthenticationType.ADMIN)
public class ContestAdminAction extends BaseAction implements ContestDAOAware {

    private IContestDAO contestDAO;

    public String toContestList() {
        return SUCCESS;
    }

    private String editorFlag;
    private Integer targetContestId;

    public String toContestEditor() {

        return SUCCESS;
    }

    @Override
    public void setContestDAO(IContestDAO contestDAO) {
        this.contestDAO = contestDAO;
    }
}
