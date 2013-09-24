package cn.edu.uestc.acmicpc.service.entity;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cn.edu.uestc.acmicpc.db.condition.impl.StatusCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IStatusDAO;
import cn.edu.uestc.acmicpc.db.entity.Status;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Judge queue scheduler.
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class Scheduler implements Runnable {

  /**
   * StatusDAO for database operation.
   */
  @Autowired
  private IStatusDAO statusDAO;

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
  @Autowired
  private StatusCondition statusCondition;

  /**
   * Spring application context
   */
  @Autowired
  private ApplicationContext applicationContext;

  /**
   * Searching interval.
   */
  private final long INTERVAL = 3L;

  @Override
  public void run() {
    Timer timer = new Timer(true);
    timer.schedule(new TimerTask() {

      @Override
      public void run() {
        searchForJudge();
      }
    }, 0, INTERVAL * 1000L);
  }

  /**
   * Search status in queuing.
   */
  @SuppressWarnings("unchecked")
  private void searchForJudge() {
    try {
      StatusCondition statusCondition = new StatusCondition();
      // FIXME set status condition.
//      statusCondition.getResult().add(Global.OnlineJudgeReturnType.OJ_WAIT);
//      statusCondition.getResult().add(Global.OnlineJudgeReturnType.OJ_REJUDGING);
      List<Status> statusList = (List<Status>) statusDAO.findAll(statusCondition.getCondition());
      for (Status status : statusList) {
        status.setResult(Global.OnlineJudgeReturnType.OJ_JUDGING.ordinal());
        status.setCaseNumber(0);
        JudgeItem judgeItem = applicationContext.getBean("judgeItem", JudgeItem.class);
        judgeItem.status = status;
        statusDAO.update(status);
        judgeQueue.put(judgeItem);
      }
    } catch (AppException e) {
      e.printStackTrace();
    } catch (InterruptedException ignored) {
    }
  }
}
