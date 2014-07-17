package cn.edu.uestc.acmicpc.web.rank;

import cn.edu.uestc.acmicpc.db.dto.impl.TrainingUserDto;

public class TrainingRankListUser {
  public TrainingUserDto[] users;
  public Integer rank;
  public Integer solved;
  public Integer tried;
  public Long penalty;
  public RankListItem[] itemList;
  public String[] rawData;
}
