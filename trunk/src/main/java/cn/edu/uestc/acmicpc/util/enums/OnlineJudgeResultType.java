package cn.edu.uestc.acmicpc.util.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * Online judge result type.
 */
public enum OnlineJudgeResultType {
  OJ_ALL("All"),
  OJ_AC("Accepted", 1),
  OJ_PE("Presentation Error", 2),
  OJ_TLE("Time Limit Exceeded", 3),
  OJ_MLE("Memory Limit Exceeded", 4),
  OJ_WA("Wrong Answer", 5),
  OJ_OLE("Output Limit Exceeded", 6),
  OJ_CE("Compilation Error", 7),
  OJ_RE("Runtime Error", 8, 9, 10, 11, 12, 15),
  OJ_RF("Restricted Function", 13),
  OJ_SE("System Error", 14),
  OJ_JUDGING("Judging", 16, 17),
  OJ_WAIT("Queuing", 0, 18),
  OJ_NOT_AC("Not accepted", 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17);

  private final static int TOTAL_RESULT_SIZE = 19;

  private final String description;

  private final List<Integer> results;

  /**
   * Get enumerate value's description.
   *
   * @return description string for specific online judge return type
   */
  public String getDescription() {
    return description;
  }

  public List<Integer> getResults() {
    return results;
  }

  private OnlineJudgeResultType(String description, int... results) {
    this.description = description;
    this.results = new ArrayList<>();
    if (results.length == 0) {
      for (int i = 0; i < TOTAL_RESULT_SIZE; i++) {
        this.results.add(i);
      }
    } else {
      for (int result : results) {
        this.results.add(result);
      }
    }
  }
}