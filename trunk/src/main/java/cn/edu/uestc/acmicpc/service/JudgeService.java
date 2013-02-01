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

import cn.edu.uestc.acmicpc.db.dao.iface.*;
import cn.edu.uestc.acmicpc.ioc.*;
import cn.edu.uestc.acmicpc.service.entity.Judge;
import cn.edu.uestc.acmicpc.service.entity.JudgeItem;
import cn.edu.uestc.acmicpc.service.entity.Scheduler;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Judge main service, use multi-thread architecture to process judge
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 * @version 2
 */
public class JudgeService implements ProblemDAOAware, StatusDAOAware, UserDAOAware, CompileinfoDAOAware, CodeDAOAware {
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

    /**
     * CodeDAO for fetch user's source code.
     */
    private ICodeDAO codeDAO;

    /**
     * Judging main thread.
     */
    private Scheduler scheduler;
    private Thread schedulerThread;
    private static Thread[] judgeThreads;
    private static Judge[] judges;
    /**
     * Judging Queue.
     */
    private static final BlockingQueue<JudgeItem> judgeQueue = new LinkedBlockingQueue<>();
    private List<Thread> threads = new LinkedList<>();

    public void init() {
        scheduler = new Scheduler(statusDAO, judgeQueue);
        schedulerThread = new Thread(scheduler);
        judgeThreads = new Thread[0];
    }

    public void destroy() {
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

    @Override
    public void setCodeDAO(ICodeDAO codeDAO) {
        this.codeDAO = codeDAO;
    }
}
