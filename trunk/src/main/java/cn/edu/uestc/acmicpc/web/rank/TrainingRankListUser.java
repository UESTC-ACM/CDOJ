package cn.edu.uestc.acmicpc.web.rank;

import cn.edu.uestc.acmicpc.db.dto.impl.TrainingUserDto;

public class TrainingRankListUser {
  public TrainingUserDto[] users;
  public Integer rank;
  public Integer solved;
  public Integer score;
  public Integer successfulHack;
  public Integer unsuccessfulHack;
  public Long penalty;
  public TrainingRankListItem[] itemList;
  public String[] rawData;
  public String userName;
  public String type;
}
