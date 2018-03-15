package cn.edu.uestc.acmicpc.judge.core;

import cn.edu.uestc.acmicpc.judge.entity.JudgeItem;
import cn.edu.uestc.acmicpc.util.enums.OnlineJudgeReturnType;
import cn.edu.uestc.acmicpc.util.settings.Settings;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * PylonCore adapter
 */
@Deprecated
public class PylonCore extends AbstractJudgeCore {

  public PylonCore(String workPath, String tempPath, Settings settings) {
    super(workPath, tempPath, settings);
  }

  /**
   * Build judge's core shell command line
   *
   * @param currentTestCase current test case number
   * @param judgeItem       {@code judgeItem} entity
   * @return command line we need
   */
  @Override
  protected String buildJudgeShellCommand(int currentTestCase, JudgeItem judgeItem) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(settings.JUDGE_CORE);
    stringBuilder.append(" -T "); // SPJ time
    stringBuilder.append(10000);
    stringBuilder.append(" -L "); // log file
    stringBuilder.append(workPath).append("/log.log");

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

    stringBuilder.append(" -t ");
    stringBuilder.append(judgeItem.getStatus().getTimeLimit());
    stringBuilder.append(" -m ");
    stringBuilder.append(judgeItem.getStatus().getMemoryLimit());

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
   * Executes shell command and return the judge result
   */
  @Override
  protected JudgeResult execute(JudgeItem judgeItem, String output) {
    String[] callBackString = output.split(" ");

    JudgeResult result = new JudgeResult();
    result.setResult(OnlineJudgeReturnType.getReturnType(Integer.parseInt(callBackString[0])));
    result.setMemoryCost(Integer.parseInt(callBackString[1]));
    result.setTimeCost(Integer.parseInt(callBackString[2]));

    if (result.getResult() == OnlineJudgeReturnType.OJ_CE) {
      StringBuilder stringBuilder = new StringBuilder();
      BufferedReader br = null;
      try {
        br = new BufferedReader(new FileReader(tempPath + "/stderr_compiler.txt"));
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
