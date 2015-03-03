package cn.edu.uestc.acmicpc.judge.entity;

import cn.edu.uestc.acmicpc.judge.core.JudgeCore;
import cn.edu.uestc.acmicpc.judge.core.JudgeResult;
import cn.edu.uestc.acmicpc.util.enums.OnlineJudgeReturnType;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;

/**
 * Problem judge component.
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Judge implements Runnable {

  private static final Logger LOGGER = LogManager.getLogger(Judge.class);

  public void setJudgeName(String judgeName) {
    this.judgeName = judgeName;
  }

  /**
   * Judge's name.
   */
  private String judgeName;

  public void setJudgeQueue(BlockingQueue<JudgeItem> judgeQueue) {
    this.judgeQueue = judgeQueue;
  }

  /**
   * Global judge queue.
   */
  private BlockingQueue<JudgeItem> judgeQueue;

  /**
   * Judge core
   */
  private JudgeCore judgeCore;

  public void setJudgeCore(JudgeCore judgeCore) {
    this.judgeCore = judgeCore;
  }

  @Override
  public void run() {
    try {
      while (true) {
        if (!judgeQueue.isEmpty()) {
          judge(judgeQueue.take());
        } else {
          Thread.sleep(200);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Judge judgeItem by judge core.
   *
   * @param judgeItem
   *          judge item to be judged
   */
  void judge(JudgeItem judgeItem) {
    LOGGER.info("[" + judgeName + "] Start judging status#"
        + judgeItem.getStatus().getStatusId());
    try {
      int numberOfTestCase = judgeItem.getStatus().getDataCount();
      boolean isAccepted = true;
      for (int currentCase = 1; isAccepted && currentCase <= numberOfTestCase; currentCase++) {
        judgeItem.getStatus().setCaseNumber(currentCase);
        JudgeResult result = judgeCore.judge(currentCase, judgeItem);
        isAccepted = updateJudgeItem(result, judgeItem);
      }
      if (isAccepted) {
        judgeItem.getStatus().setResultId(OnlineJudgeReturnType.OJ_AC.ordinal());
      }
      judgeItem.update(true);
    } catch (Exception e) {
      LOGGER.error(e);
      judgeItem.getStatus().setResultId(OnlineJudgeReturnType.OJ_SE.ordinal());
      judgeItem.update(true);
    }
  }

  private boolean updateJudgeItem(JudgeResult judgeResult, JudgeItem judgeItem) {
    boolean isAccepted = true;
    OnlineJudgeReturnType result = judgeResult.getResult();
    if (result == OnlineJudgeReturnType.OJ_AC) {
      result = OnlineJudgeReturnType.OJ_RUNNING;
    } else {
      isAccepted = false;
    }
    judgeItem.getStatus().setResultId(result.ordinal());
    Integer oldMemoryCost = judgeItem.getStatus().getMemoryCost();
    Integer currentMemoryCost = judgeResult.getMemoryCost();
    judgeItem.getStatus().setMemoryCost(Math.max(currentMemoryCost, oldMemoryCost));

    Integer oldTimeCost = judgeItem.getStatus().getTimeCost();
    Integer currentTimeCost = judgeResult.getTimeCost();
    if (oldTimeCost == null) {
      judgeItem.getStatus().setTimeCost(currentTimeCost);
    } else {
      judgeItem.getStatus().setTimeCost(Math.max(currentTimeCost, oldTimeCost));
    }

    if (judgeItem.getStatus().getResultId() == OnlineJudgeReturnType.OJ_CE.ordinal()) {
      judgeItem.setCompileInfo(judgeResult.getCompileInfo());
    } else {
      if (judgeItem.getCompileInfo() != null) {
        judgeItem.setCompileInfo(null);
      }
    }

    judgeItem.update(false);
    return isAccepted;
  }
}
