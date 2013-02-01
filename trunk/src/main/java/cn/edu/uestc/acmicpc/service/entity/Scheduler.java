package cn.edu.uestc.acmicpc.service.entity;

import cn.edu.uestc.acmicpc.db.condition.impl.StatusCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.ICompileinfoDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IProblemDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IStatusDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IUserDAO;
import cn.edu.uestc.acmicpc.db.entity.Status;
import cn.edu.uestc.acmicpc.service.entity.JudgeItem;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;

/**
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 * @version 1
 */
public class Scheduler implements Runnable {

    /**
     * StatusDAO for database operation.
     */
    private IStatusDAO statusDAO;

    /**
     * Judging queue.
     */
    private final BlockingQueue<JudgeItem> judgeQueue;

    /**
     * Searching interval.
     */
    private long INTERVAL = 3L;

    public Scheduler(IStatusDAO statusDAO, BlockingQueue<JudgeItem> queue) {
        this.statusDAO = statusDAO;
        this.judgeQueue = queue;
    }

    @Override
    public void run() {
        Timer timer = new Timer(true);
        timer.schedule(new TimerTask() {
            public void run() {
                searchForJudge();
            }
        }, 0, INTERVAL * 1000L);
    }

    /**
     * Search status in queuing.
     */
    private void searchForJudge() {
        try {
            StatusCondition statusCondition = new StatusCondition();
            statusCondition.result.add(Global.OnlineJudgeReturnType.OJ_WAIT);
            statusCondition.result.add(Global.OnlineJudgeReturnType.OJ_REJUDGING);
            List<Status> statusList = statusDAO.findAll(statusCondition.getCondition());
            for (Status status : statusList) {
                status.setResult(Global.OnlineJudgeReturnType.OJ_JUDGING.ordinal());
                status.setCaseNumber(0);
                JudgeItem judgeItem = new JudgeItem(status);
                statusDAO.update(status);
                judgeQueue.put(judgeItem);
            }
        } catch (AppException | InterruptedException ignored) {
        }
    }
}
