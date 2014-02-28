package cn.edu.uestc.acmicpc.judge;

import cn.edu.uestc.acmicpc.judge.entity.Judge;
import cn.edu.uestc.acmicpc.judge.entity.JudgeItem;
import cn.edu.uestc.acmicpc.judge.entity.Scheduler;
import cn.edu.uestc.acmicpc.util.settings.Settings;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Judge main service, use multi-thread architecture to process judge
 */
public class JudgeService {

  private static final Logger LOGGER = LogManager.getLogger(JudgeService.class);

  private Thread schedulerThread;
  private static Thread[] judgeThreads;
  /**
   * Judging Queue.
   */
  private static final BlockingQueue<JudgeItem> judgeQueue = new LinkedBlockingQueue<>();

  @Autowired
  private ApplicationContext applicationContext;

  @Autowired
  private Settings settings;

  /**
   * Initialize the judge threads.
   */
  @PostConstruct
  public void init() {
    Scheduler scheduler = applicationContext.getBean("scheduler", Scheduler.class);
    scheduler.setJudgeQueue(judgeQueue);
    schedulerThread = new Thread(scheduler);
    judgeThreads = new Thread[settings.JUDGE_LIST.size()];
    Judge[] judges = new Judge[settings.JUDGE_LIST.size()];
    for (int i = 0; i < judgeThreads.length; ++i) {
      judges[i] = applicationContext.getBean("judge", Judge.class);
      judges[i].setJudgeQueue(judgeQueue);
      judges[i].setWorkPath(settings.JUDGE_TEMP_PATH + "/" + settings.JUDGE_LIST.get(i).get("name")
          + "/");
      judges[i].setTempPath(settings.JUDGE_TEMP_PATH + "/" + settings.JUDGE_LIST.get(i).get("name")
          + "/temp/");
      judges[i].setJudgeName(settings.JUDGE_LIST.get(i).get("name"));
      judgeThreads[i] = new Thread(judges[i]);
      judgeThreads[i].start();
    }
    schedulerThread.start();
    LOGGER.info("judge service initialize completed.");
  }

  /**
   * Destroy the judge threads.
   */
  @PreDestroy
  public void destroy() {
    try {
      if (schedulerThread.isAlive()) {
        schedulerThread.interrupt();
      }
      for (Thread judgeThread : judgeThreads) {
        if (judgeThread.isAlive()) {
          judgeThread.interrupt();
        }
      }
    } catch (SecurityException ignored) {
    }
    LOGGER.info("judge service destroy completed.");
  }
}
