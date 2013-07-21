package cn.edu.uestc.acmicpc.training.parser;

import cn.edu.uestc.acmicpc.training.entity.TrainingProblemSummaryInfo;
import cn.edu.uestc.acmicpc.util.exception.ParserException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
public class TrainingRankListFormatParser {

  private static String VJ_1Y = "^(\\d{1,2})\\s*:\\s*(\\d{2})\\s*:\\s*(\\d{2})$";
  private static String VJ_NORMAL =
      "^(\\d{1,2})\\s*:\\s*(\\d{2})\\s*:\\s*(\\d{2})\\s*\\(\\s*-\\s*(\\d+)\\s*\\)$";
  private static String VJ_FAIL = "^\\(\\s*-\\s*(\\d+)\\s*\\)$";
  private static String PC_NORMAL = "^(\\d+)\\s*/\\s*(\\d+)$";
  private static String PC_FAIL = "^(\\d+)\\s*/\\s*-\\s*-$";

  public static TrainingProblemSummaryInfo getProblemScore(String grid) {
    TrainingProblemSummaryInfo trainingProblemSummaryInfo = new TrainingProblemSummaryInfo();
    grid = grid.trim();
    // ""
    if (grid.equals(""))
      return trainingProblemSummaryInfo;
    trainingProblemSummaryInfo.setPenalty((int) Math.floor(Double.parseDouble(grid)));
    if (trainingProblemSummaryInfo.getPenalty() > 0) {
      trainingProblemSummaryInfo.setSolved(true);
      trainingProblemSummaryInfo.setTried(1);
      trainingProblemSummaryInfo.setSolutionTime(trainingProblemSummaryInfo.getPenalty());
    }
    return trainingProblemSummaryInfo;
  }

  public static TrainingProblemSummaryInfo getProblemSummaryInfo(String grid)
      throws ParserException {
    TrainingProblemSummaryInfo trainingProblemSummaryInfo = new TrainingProblemSummaryInfo();
    grid = grid.trim();
    // ""
    if (grid.equals(""))
      return trainingProblemSummaryInfo;
    Pattern pattern;
    Matcher matcher;

    // hh:mm:ss
    pattern = Pattern.compile(VJ_1Y);
    matcher = pattern.matcher(grid);
    if (matcher.find()) {
      Integer hh = Integer.parseInt(matcher.group(1));
      Integer mm = Integer.parseInt(matcher.group(2));
      trainingProblemSummaryInfo.setSolved(true);
      trainingProblemSummaryInfo.setTried(1);
      trainingProblemSummaryInfo.setSolutionTime(hh * 60 + mm);
      trainingProblemSummaryInfo.setPenalty(hh * 60 + mm);
      return trainingProblemSummaryInfo;
    }
    // hh:mm:ss(-tried)
    pattern = Pattern.compile(VJ_NORMAL);
    matcher = pattern.matcher(grid);
    if (matcher.find()) {
      Integer hh = Integer.parseInt(matcher.group(1));
      Integer mm = Integer.parseInt(matcher.group(2));
      Integer tried = Integer.parseInt(matcher.group(4));
      trainingProblemSummaryInfo.setSolved(true);
      trainingProblemSummaryInfo.setTried(1 + tried);
      trainingProblemSummaryInfo.setSolutionTime(hh * 60 + mm);
      trainingProblemSummaryInfo.setPenalty(hh * 60 + mm + tried * 20);
      return trainingProblemSummaryInfo;
    }
    // (-tried)
    pattern = Pattern.compile(VJ_FAIL);
    matcher = pattern.matcher(grid);
    if (matcher.find()) {
      Integer tried = Integer.parseInt(matcher.group(1));
      trainingProblemSummaryInfo.setTried(tried);
      return trainingProblemSummaryInfo;
    }
    // tried/time
    pattern = Pattern.compile(PC_NORMAL);
    matcher = pattern.matcher(grid);
    if (matcher.find()) {
      Integer tried = Integer.parseInt(matcher.group(1));
      Integer time = Integer.parseInt(matcher.group(2));
      trainingProblemSummaryInfo.setSolved(true);
      trainingProblemSummaryInfo.setTried(tried);
      trainingProblemSummaryInfo.setSolutionTime(time);
      trainingProblemSummaryInfo.setPenalty(time + (tried - 1) * 20);
      return trainingProblemSummaryInfo;
    }
    // tried/--
    pattern = Pattern.compile(PC_FAIL);
    matcher = pattern.matcher(grid);
    if (matcher.find()) {
      Integer tried = Integer.parseInt(matcher.group(1));
      trainingProblemSummaryInfo.setTried(tried);
      return trainingProblemSummaryInfo;
    }

    throw new ParserException("Error while parse |" + grid + "|, is it right?");
  }
}
