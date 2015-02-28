package cn.edu.uestc.acmicpc.judge.entity;

import cn.edu.uestc.acmicpc.util.enums.OnlineJudgeReturnType;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.helper.FileUtil;
import cn.edu.uestc.acmicpc.util.settings.Settings;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

  public void setWorkPath(String workPath) throws AppException {
    File dir = new File(workPath);
    if (!dir.exists() && !dir.mkdirs()) {
      throw new AppException("Cannot create work directory");
    }
    this.workPath = workPath;
  }

  /**
   * Judge's name.
   */
  private String judgeName;
  /**
   * Judge's work path.
   */
  private String workPath;

  /**
   * Global setting entity.
   */
  @Autowired
  private Settings settings;

  public void setTempPath(String tempPath) throws AppException {
    File dir = new File(tempPath);
    if (!dir.exists() && !dir.mkdirs()) {
      throw new AppException("Cannot create temp directory");
    }
    this.tempPath = tempPath;
  }

  /**
   * Temp files path.
   */
  private String tempPath;

  public void setJudgeQueue(BlockingQueue<JudgeItem> judgeQueue) {
    this.judgeQueue = judgeQueue;
  }

  /**
   * Global judge queue.
   */
  private BlockingQueue<JudgeItem> judgeQueue;

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
   * Build judge's core shell command line
   *
   * @param problemId
   *          problem's id
   * @param currentTestCase
   *          current test case number
   * @param judgeItem
   *          {@code judgeItem} entity
   * @return command line we need
   */
  private String buildJudgeShellCommand(int problemId, int currentTestCase,
      JudgeItem judgeItem) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(settings.JUDGE_CORE);
    stringBuilder.append(" -T "); // SPJ time
    stringBuilder.append(10000);
    stringBuilder.append(" -L "); // log file
    stringBuilder.append(workPath + "/log.log");

    stringBuilder.append(" -u ");
    stringBuilder.append(judgeItem.getStatus().getStatusId());
    stringBuilder.append(" -s ");
    stringBuilder.append(judgeItem.getSourceName());
    stringBuilder.append(" -n ");
    stringBuilder.append(problemId);
    stringBuilder.append(" -D ");
    stringBuilder.append(settings.DATA_PATH);
    stringBuilder.append("/")
        .append(judgeItem.getStatus().getProblemId())
        .append("/");
    stringBuilder.append(" -d ");
    stringBuilder.append(tempPath);

    if (judgeItem.getStatus().getLanguage().equals("Java")) {
      stringBuilder.append(" -t ");
      stringBuilder.append(judgeItem.getStatus().getJavaTimeLimit());
      stringBuilder.append(" -m ");
      stringBuilder.append(judgeItem.getStatus()
          .getJavaMemoryLimit());
    } else {
      stringBuilder.append(" -t ");
      stringBuilder.append(judgeItem.getStatus().getTimeLimit());
      stringBuilder.append(" -m ");
      stringBuilder.append(judgeItem.getStatus().getMemoryLimit());
    }

    stringBuilder.append(" -o ");
    stringBuilder.append(judgeItem.getStatus().getOutputLimit());
    if (judgeItem.getStatus().getIsSpj()) {
      stringBuilder.append(" -S");
    }
    stringBuilder.append(" -l ");
    stringBuilder.append(judgeItem.getStatus().getLanguageId());
    stringBuilder.append(" -I ");
    stringBuilder.append(settings.DATA_PATH).append("/")
        .append(judgeItem.getStatus().getProblemId()).append("/")
        .append(currentTestCase).append(".in");
    stringBuilder.append(" -O ");
    stringBuilder.append(settings.DATA_PATH).append("/")
        .append(judgeItem.getStatus().getProblemId()).append("/")
        .append(currentTestCase).append(".out");
    if (currentTestCase == 1) {
      stringBuilder.append(" -C");
    }

    return stringBuilder.toString();
  }

  /**
   * Get process' call back string with shell command.
   *
   * @param shellCommand
   *          shell command line
   * @return command's call back string
   */
  private String[] getCallBackString(String shellCommand) {
    Process p;
    String callBackString = "";
    try {
      p = Runtime.getRuntime().exec(shellCommand);
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
          p.getInputStream()));
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        callBackString += line;
      }
    } catch (Exception ignored) {
    }
    return callBackString.split(" ");
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
      FileUtil.saveToFile(judgeItem.getStatus().getCodeContent(),
          tempPath + "/"
              + judgeItem.getSourceName());
      int problemId = judgeItem.getStatus().getProblemId();
      for (int currentCase = 1; isAccepted && currentCase <= numberOfTestCase; currentCase++) {
        judgeItem.getStatus().setCaseNumber(currentCase);
        String shellCommand = buildJudgeShellCommand(problemId, currentCase,
            judgeItem);
        String[] callBackString = getCallBackString(shellCommand);
        isAccepted = updateJudgeItem(callBackString, judgeItem);
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

  private boolean updateJudgeItem(String[] callBackString, JudgeItem judgeItem) {
    boolean isAccepted = true;
    if (callBackString != null && callBackString.length == 3) {
      try {
        int result = Integer.parseInt(callBackString[0]);
        if (result == OnlineJudgeReturnType.OJ_AC.ordinal()) {
          result = OnlineJudgeReturnType.OJ_RUNNING.ordinal();
        } else {
          isAccepted = false;
        }
        judgeItem.getStatus().setResultId(result);
        Integer oldMemoryCost = judgeItem.getStatus().getMemoryCost();
        Integer currentMemoryCost = Integer.parseInt(callBackString[1]);
        judgeItem.getStatus().setMemoryCost(Math.max(currentMemoryCost, oldMemoryCost));

        Integer oldTimeCost = judgeItem.getStatus().getTimeCost();
        Integer currentTimeCost = Integer.parseInt(callBackString[2]);
        if (oldTimeCost == null) {
          judgeItem.getStatus().setTimeCost(currentTimeCost);
        } else {
          judgeItem.getStatus().setTimeCost(Math.max(currentTimeCost, oldTimeCost));
        }
      } catch (NumberFormatException e) {
        judgeItem.getStatus().setResultId(OnlineJudgeReturnType.OJ_SE.ordinal());
        isAccepted = false;
      }
    } else {
      judgeItem.getStatus().setResultId(OnlineJudgeReturnType.OJ_SE.ordinal());
      isAccepted = false;
    }

    if (judgeItem.getStatus().getResultId() == OnlineJudgeReturnType.OJ_CE.ordinal()) {
      StringBuilder stringBuilder = new StringBuilder();
      BufferedReader br = null;
      try {
        br = new BufferedReader(new FileReader(workPath
            + "/temp/stderr_compiler.txt"));
        String line;
        while ((line = br.readLine()) != null) {
          if (line.trim().startsWith("/home/")) {
            line = line.substring(line.indexOf(judgeItem.getSourceName()));
          }
          stringBuilder.append(line).append('\n');
        }
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        if (br != null) {
          try {
            br.close();
          } catch (IOException ignored) {
          }
        }
      }
      judgeItem.setCompileInfo(stringBuilder.toString());
    } else {
      if (judgeItem.getCompileInfo() != null) {
        judgeItem.setCompileInfo(null);
      }
    }

    judgeItem.update(false);
    return isAccepted;
  }
}
