package cn.edu.uestc.acmicpc.service;

import cn.edu.uestc.acmicpc.db.dao.iface.ICompileinfoDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IProblemDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IStatusDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IUserDAO;

/**
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 * @version 1
 */
public class JudgeThread implements Runnable {
    private IProblemDAO problemDAO;
    private IStatusDAO statusDAO;
    private final IUserDAO userDAO;
    private final ICompileinfoDAO compileinfoDAO;

    public JudgeThread(IUserDAO userDAO, IProblemDAO problemDAO, IStatusDAO statusDAO, ICompileinfoDAO compileinfoDAO) {
        this.userDAO = userDAO;
        this.problemDAO = problemDAO;
        this.statusDAO = statusDAO;
        this.compileinfoDAO = compileinfoDAO;
    }

    @Override
    public void run() {
    }
}
