package cn.edu.uestc.acmicpc.web.rank;

import cn.edu.uestc.acmicpc.db.dto.impl.teamUser.TeamUserListDTO;

import java.util.List;

/**
 * Description
 */
public class RankListUser implements Comparable<RankListUser> {
  public String userName;
  public String nickName;
  public String email;
  public List<TeamUserListDTO> teamUsers;
  public Integer rank;
  public Integer solved;
  public Integer tried;
  public Long penalty;
  public RankListItem[] itemList;

  @Override
  public int compareTo(RankListUser b) {
    if (b == null) {
      return -1;
    }
    if (this.solved > b.solved) {
      return -1;
    } else if (this.solved < b.solved) {
      return 1;
    } else {
      return this.penalty.compareTo(b.penalty);
    }
  }
}
