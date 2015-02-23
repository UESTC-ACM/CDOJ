package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.rank.RankList;

/**
 * Contest rank list service interface.
 */
public interface ContestRankListService {

  /**
   * Get rank list by contest's id.
   *
   * @param contestId   contest's id.
   * @param contestType
   * @param frozen
   * @param frozenTime
   * @return {@link RankList} entity.
   * @throws AppException
   */
  public RankList getRankList(Integer contestId,
      Integer contestType,
      Boolean frozen, Integer frozenTime) throws AppException;

}
