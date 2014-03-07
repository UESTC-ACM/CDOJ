package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.dto.impl.contestTeamInfo.ContestTeamInfoDTO;
import cn.edu.uestc.acmicpc.db.entity.ContestTeamInfo;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * ContestTeamInfo service interface.
 */
public interface ContestTeamInfoService extends DatabaseService<ContestTeamInfo, Integer> {

  /**
   * Get {@link cn.edu.uestc.acmicpc.db.dto.impl.contestTeamInfo.ContestTeamInfoDTO} entity by team's id.
   *
   * @param teamId team's Id.
   * @return {@link cn.edu.uestc.acmicpc.db.dto.impl.contestTeamInfo.ContestTeamInfoDTO} entity.
   * @throws AppException
   */

  public ContestTeamInfoDTO getContestTeamInfoDTOByTeamId(Integer teamId) throws AppException;
}
