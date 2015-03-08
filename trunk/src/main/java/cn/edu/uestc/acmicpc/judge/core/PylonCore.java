package cn.edu.uestc.acmicpc.judge.core;

import cn.edu.uestc.acmicpc.judge.entity.JudgeItem;
import cn.edu.uestc.acmicpc.util.enums.OnlineJudgeReturnType;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.helper.FileUtil;
import cn.edu.uestc.acmicpc.util.settings.Settings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * PylonCore adapter
 */
public class PylonCore implements JudgeCore {

  /**
   * Judge's work path.
   */
  private final String workPath;

  /**
   * Temp files path.
   */
  private final String tempPath;

  /**
   * Global setting entity.
   */
  private final Settings settings;

  public PylonCore(String workPath, String tempPath, Settings settings) throws AppException {
    this.settings = settings;
    this.workPath = preparePath(workPath, "work");
    this.tempPath = preparePath(tempPath, "temp");
  }

  private String preparePath(String path, String type) throws AppException {
    File dir = new File(path);
    if (!dir.exists() && !dir.mkdirs()) {
      throw new AppException("Cannot create " + type + " directory");
    }
    return path;
  }

  /**
   * Build judge's core shell command line
   *
   * @param currentTestCase
   *          current test case number
   * @param judgeItem
   *          {@code judgeItem} entity
   * @return command line we need
   */
  private String buildJudgeShellCommand(int currentTestCase, JudgeItem judgeItem) {
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
    stringBuilder.append(judgeItem.getStatus().getProblemId());
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

  @Override
  public JudgeResult judge(int currentCase, JudgeItem judgeItem) {
    if (currentCase == 1) {
      // Save source code.
      FileUtil.saveToFile(judgeItem.getStatus().getCodeContent(),
          tempPath + "/" + judgeItem.getSourceName());
    }

    String shellCommand = buildJudgeShellCommand(currentCase, judgeItem);
    String[] callBackString = getCallBackString(shellCommand);

    JudgeResult result = new JudgeResult();
    result.setResult(OnlineJudgeReturnType.getReturnType(Integer.parseInt(callBackString[0])));
    result.setMemoryCost(Integer.parseInt(callBackString[1]));
    result.setTimeCost(Integer.parseInt(callBackString[2]));

    if (result.getResult()  == OnlineJudgeReturnType.OJ_CE) {
      StringBuilder stringBuilder = new StringBuilder();
      BufferedReader br = null;
      try {
        br = new BufferedReader(new FileReader(workPath + "/temp/stderr_compiler.txt"));
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
      result.setCompileInfo(stringBuilder.toString());
    }

    return result;
  }
}
