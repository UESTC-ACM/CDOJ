package cn.edu.uestc.acmicpc.web.rank;

import java.sql.Timestamp;

/**
 * Description
 */
public class RankList {
  public RankListProblem[] problemList;
  public RankListUser[] rankList;
  public Timestamp lastFetched;
}
