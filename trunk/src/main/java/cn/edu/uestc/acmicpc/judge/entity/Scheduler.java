package cn.edu.uestc.acmicpc.judge.entity;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cn.edu.uestc.acmicpc.db.condition.impl.StatusCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IStatusDAO;
import cn.edu.uestc.acmicpc.db.entity.Status;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.Global.OnlineJudgeReturnType;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Judge queue scheduler.
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class Scheduler implements Runnable, ApplicationContextAware {

  private static final Logger LOGGER = LogManager.getLogger(Scheduler.class);

  private IStatusDAO statusDAO;

  public void setJudgeQueue(BlockingQueue<JudgeItem> judgeQueue) {
    this.judgeQueue = judgeQueue;
  }

  /**
   * Judging queue.
   */
  private BlockingQueue<JudgeItem> judgeQueue;

  /**
   * Spring application context
   */
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
  @SuppressWarnings({ "unchecked", "deprecation" })
  private void searchForJudge() {
    try {
      StatusCondition statusCondition = new StatusCondition();
      statusCondition.result.add(OnlineJudgeReturnType.OJ_WAIT);
      statusCondition.result.add(OnlineJudgeReturnType.OJ_REJUDGING);
      List<Status> statusList = (List<Status>) statusDAO.findAll(statusCondition.getCondition());
      for (Status status : statusList) {
        status.setResult(Global.OnlineJudgeReturnType.OJ_JUDGING.ordinal());
        status.setCaseNumber(0);
        JudgeItem judgeItem = applicationContext.getBean(JudgeItem.class);
        judgeItem.status = status;
        statusDAO.update(status);
        judgeQueue.put(judgeItem);
      }
    } catch (AppException e) {
      LOGGER.error(e);
    } catch (InterruptedException ignored) {
    }
  }

  @Autowired
  public void setStatusDAO(IStatusDAO statusDAO) {
    this.statusDAO = statusDAO;
  }

  @Override
  @Autowired
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }
}
