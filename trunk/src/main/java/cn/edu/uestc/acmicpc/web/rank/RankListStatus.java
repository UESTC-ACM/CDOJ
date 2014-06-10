package cn.edu.uestc.acmicpc.web.rank;

/**
 * Description
 */
public class RankListStatus {
  public Integer tried;
  public Integer result;
  public String problemTitle;
  public String userName;
  public String nickName;
  public String reallyName;
  public String email;
  public Long time;

  public RankListStatus(Integer tried,
                        Integer result,
                        String problemTitle,
                        String userName,
                        String nickName,
                        String email,
                        String reallyName,
                        Long time) {
    this.tried = tried;
    this.result = result;
    this.problemTitle = problemTitle;
    this.userName = userName;
    this.nickName = nickName;
    this.email = email;
    this.reallyName = reallyName;
    this.time = time;
  }
}
