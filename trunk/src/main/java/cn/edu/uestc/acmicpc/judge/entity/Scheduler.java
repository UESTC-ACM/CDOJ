package cn.edu.uestc.acmicpc.judge.entity;

import cn.edu.uestc.acmicpc.db.dto.impl.StatusDto;
import cn.edu.uestc.acmicpc.service.iface.StatusService;
import cn.edu.uestc.acmicpc.util.enums.OnlineJudgeReturnType;
import cn.edu.uestc.acmicpc.util.exception.AppException;
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

/**
 * Judge queue scheduler.
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class Scheduler implements Runnable, ApplicationContextAware {

  private static final Logger LOGGER = LogManager.getLogger(Scheduler.class);

  public void setJudgeQueue(BlockingQueue<JudgeItem> judgeQueue) {
    this.judgeQueue = judgeQueue;
  }

  /**
   * Status service
   */
  private StatusService statusService;

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
    searchForJudge(true);
    Timer timer = new Timer(true);
    timer.schedule(new TimerTask() {

      @Override
      public void run() {
        searchForJudge(false);
      }
    }, 0, INTERVAL * 1000L);
  }

  /**
   * Search status in queuing.
   *
   * @param isFirstTime
   *          whether is first time to search
   */
  private void searchForJudge(boolean isFirstTime) {
    try {
      List<StatusDto> statusList = statusService.getQueuingStatus(isFirstTime);
      for (StatusDto status : statusList) {
        status.setResultId(OnlineJudgeReturnType.OJ_JUDGING.ordinal());
        status.setCaseNumber(0);
        JudgeItem judgeItem = applicationContext.getBean(JudgeItem.class);
        judgeItem.setStatus(status);
        statusService.updateStatus(status);
        judgeQueue.put(judgeItem);
        LOGGER.info("[Scheduler] Put status#" + status.getStatusId() + " into judge queue.");
      }
    } catch (AppException e) {
      LOGGER.error(e);
    } catch (InterruptedException ignored) {
    }
  }

  @Autowired
  public void setStatusService(StatusService statusService) {
    this.statusService = statusService;
  }

  @Override
  @Autowired
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }
}
