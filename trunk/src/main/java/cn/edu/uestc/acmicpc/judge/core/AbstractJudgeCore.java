package cn.edu.uestc.acmicpc.judge.core;

import cn.edu.uestc.acmicpc.judge.entity.JudgeItem;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.helper.FileUtil;
import cn.edu.uestc.acmicpc.util.settings.Settings;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.log4j.Logger;

/**
 * Abstract judge core adapter, including common methods.
 */
public abstract class AbstractJudgeCore implements JudgeCore {

  private static final Logger logger = Logger.getLogger(AbstractJudgeCore.class);

  /**
   * Judge's work path.
   */
  protected final String workPath;
  /**
   * Temp files path.
   */
  protected final String tempPath;
  /**
   * Global setting entity.
   */
  protected final Settings settings;

  AbstractJudgeCore(String workPath, String tempPath, Settings settings) {
    this.settings = settings;
    this.workPath = preparePath(workPath, "work");
    this.tempPath = preparePath(tempPath, "temp");
  }

  private String preparePath(String path, String type) throws AppException {
    File dir = new File(path);
    if (!dir.exists() && !dir.mkdirs()) {
      throw new AppException("Cannot create " + type + " directory");
    }
    return dir.getAbsolutePath() + "/";
  }

  /**
   * Build judge's core shell command line
   *
   * @param currentTestCase current test case number
   * @param judgeItem       {@code judgeItem} entity
   * @return command line we need
   */
  protected abstract String buildJudgeShellCommand(int currentTestCase, JudgeItem judgeItem);

  /**
   * Executes shell command and return the judge result
   */
  protected abstract JudgeResult execute(JudgeItem judgeItem, String shellCommand);

  @Override
  public JudgeResult judge(int currentCase, JudgeItem judgeItem) {
    if (currentCase == 1) {
      // Save source code.
      FileUtil.saveToFile(judgeItem.getStatus().getCodeContent(),
          tempPath + "/" + judgeItem.getSourceName());
    }
    String shellCommand = buildJudgeShellCommand(currentCase, judgeItem);
    logger.info("the shellCommand for judge core: " + shellCommand);
    Process p;
    StringBuilder sb = new StringBuilder();
    BufferedReader bufferedReader = null;
    try {
      p = Runtime.getRuntime().exec(shellCommand);
      bufferedReader = new BufferedReader(new InputStreamReader(
          p.getInputStream()));
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        sb.append(line).append("\n");
      }
    } catch (Exception ignored) {
    } finally {
      if (bufferedReader != null) {
        try {
          bufferedReader.close();
        } catch (IOException ignored) {
        }
      }
    }
    JudgeResult t = execute(judgeItem, sb.toString());
    t.settempPath(tempPath);
    return t;
  }
}