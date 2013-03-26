package cn.edu.uestc.acmicpc.service.entity;

import cn.edu.uestc.acmicpc.db.condition.impl.StatusCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IStatusDAO;
import cn.edu.uestc.acmicpc.db.entity.Status;
import cn.edu.uestc.acmicpc.ioc.condition.StatusConditionAware;
import cn.edu.uestc.acmicpc.ioc.dao.StatusDAOAware;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;

/**
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 */
public class Scheduler implements Runnable, StatusConditionAware, StatusDAOAware {

    /**
     * StatusDAO for database operation.
     */
    private IStatusDAO statusDAO;

    @SuppressWarnings("SameParameterValue")
    public void setJudgeQueue(BlockingQueue<JudgeItem> judgeQueue) {
        this.judgeQueue = judgeQueue;
    }

    /**
     * Judging queue.
     */
    private BlockingQueue<JudgeItem> judgeQueue;

    /**
     * Status database condition.
     */
    private StatusCondition statusCondition;

    /**
     * Spring application context
     */
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * Searching interval.
     */
    @SuppressWarnings("FieldCanBeLocal")
    private final long INTERVAL = 3L;

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
            statusCondition.getResult().add(Global.OnlineJudgeReturnType.OJ_WAIT);
            statusCondition.getResult().add(Global.OnlineJudgeReturnType.OJ_REJUDGING);
            List<Status> statusList = statusDAO.findAll(statusCondition.getCondition());
            for (Status status : statusList) {
                status.setResult(Global.OnlineJudgeReturnType.OJ_JUDGING.ordinal());
                status.setCaseNumber(0);
                JudgeItem judgeItem = applicationContext.getBean("judgeItem", JudgeItem.class);
                judgeItem.status = status;
                statusDAO.update(status);
                judgeQueue.put(judgeItem);
            }
        } catch (AppException | InterruptedException ignored) {
        }
    }

    @Override
    public void setStatusCondition(StatusCondition statusCondition) {
        this.statusCondition = statusCondition;
    }

    @Override
    public void setStatusDAO(IStatusDAO statusDAO) {
        this.statusDAO = statusDAO;
    }
}
