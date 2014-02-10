package cn.edu.uestc.acmicpc.web.rank;

/**
 * Description
 */
public class RankListStatus {
  public Integer tried;
  public Integer result;
  public String problemTitle;
  public String userName;
  public Long time;

  public RankListStatus(Integer tried,
                        Integer result,
                        String problemTitle,
                        String userName,
                        Long time) {
    this.tried = tried;
    this.result = result;
    this.problemTitle = problemTitle;
    this.userName = userName;
    this.time = time;
  }
}
