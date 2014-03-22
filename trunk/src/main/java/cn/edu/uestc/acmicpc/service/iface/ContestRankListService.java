package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.rank.RankList;

/**
 * Description
 */
public interface ContestRankListService {

  public RankList getRankList(Integer contestId) throws AppException;
}
