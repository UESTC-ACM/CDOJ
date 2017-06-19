package cn.edu.uestc.acmicpc.judge.core;

import cn.edu.uestc.acmicpc.judge.entity.JudgeItem;
import cn.edu.uestc.acmicpc.util.enums.OnlineJudgeReturnType;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.settings.Settings;
import org.apache.log4j.Logger;

/**
 * judge core adapter using lrun as judge core.
 */
public class LrunCore extends AbstractJudgeCore {

  private static final Logger logger = Logger.getLogger(LrunCore.class);

  private static final String COMMAND_LINE_PATTERN =
    "py-lrun-core.py -l %s -w %s -d %s -s %s -t %d -m %d -f %s";

  public LrunCore(String workPath, String tempPath, Settings settings) {
    super(workPath, tempPath, settings);
  }

  @Override
  protected String buildJudgeShellCommand(int currentTestCase, JudgeItem judgeItem) {
    String language = normalizeLanguage(judgeItem.getStatus().getLanguage());
    String dataDir = settings.DATA_PATH + "/" + judgeItem.getStatus().getProblemId() + "/";
    int timeLimit = judgeItem.getStatus().getLanguage().equals("Java")
        ? judgeItem.getStatus().getJavaTimeLimit()
        : judgeItem.getStatus().getTimeLimit();
    int memory = judgeItem.getStatus().getLanguage().equals("Java")
        ? judgeItem.getStatus().getJavaMemoryLimit()
        : judgeItem.getStatus().getMemoryLimit();
    String command = String.format(COMMAND_LINE_PATTERN,
        language, tempPath, dataDir,
        judgeItem.getSourceNameWithoutExtension(),
        timeLimit, memory,
        Integer.toString(currentTestCase) /* data file */);
    if (currentTestCase == 1) {
      command = command + " -c";
    }
    return command;
  }

  private String normalizeLanguage(String language) {
    if ("Java".equals(language)) {
      return "java";
    } else if ("C".equals(language)) {
      return "gnu-gcc";
    } else if ("C++".equals(language)) {
      return "gnu-g++11";
    } else {
      throw new AppException("unreognize language: " + language);
    }
  }

  @Override
  protected JudgeResult execute(JudgeItem judgeItem, String output) {
    JudgeResult result = new JudgeResult();
    result.setResult(OnlineJudgeReturnType.OJ_WA);
    result.setMemoryCost(128);
    result.setTimeCost(648);
    logger.error("[#] from LrunCore: " + output);
    return result;
  }
}
