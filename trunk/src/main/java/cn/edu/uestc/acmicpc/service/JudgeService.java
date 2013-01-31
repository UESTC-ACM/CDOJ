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

package cn.edu.uestc.acmicpc.service;

import cn.edu.uestc.acmicpc.db.dao.iface.ICompileinfoDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IProblemDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IStatusDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IUserDAO;
import cn.edu.uestc.acmicpc.ioc.CompileinfoDAOAware;
import cn.edu.uestc.acmicpc.ioc.ProblemDAOAware;
import cn.edu.uestc.acmicpc.ioc.StatusDAOAware;
import cn.edu.uestc.acmicpc.ioc.UserDAOAware;

import java.util.LinkedList;
import java.util.List;

/**
 * Judge main service, use multi-thread architecture to process judge
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 * @version 1
 */
public class JudgeService implements ProblemDAOAware, StatusDAOAware, UserDAOAware, CompileinfoDAOAware {
    /**
     * ProblemDAO for initializing problem information.
     */
    private IProblemDAO problemDAO;

    /**
     * StatusDAO for reading status information.
     */
    private IStatusDAO statusDAO;

    /**
     * UserDAO for updating user entity.
     */
    private IUserDAO userDAO;

    /**
     * CompileinfoDAO for add compile information.
     */
    private ICompileinfoDAO compileinfoDAO;

    List<Thread> threads = new LinkedList<Thread>();

    public void init() {
        for (int i = 0; i < 3; ++i) {
            Thread thread = new Thread(new JudgeThread(userDAO, problemDAO, statusDAO, compileinfoDAO));
            thread.start();
            threads.add(thread);
        }
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
    public void setCompileinfoDAO(ICompileinfoDAO compileinfoDAO) {
        this.compileinfoDAO = compileinfoDAO;
    }

    @Override
    public void setUserDAO(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
