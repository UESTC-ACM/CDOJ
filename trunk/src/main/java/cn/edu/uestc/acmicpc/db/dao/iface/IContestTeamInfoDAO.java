package cn.edu.uestc.acmicpc.db.dao.iface;

import cn.edu.uestc.acmicpc.db.dto.impl.ContestTeamInfoDTO;
import cn.edu.uestc.acmicpc.db.entity.ContestTeamInfo;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * ContestTeamInfoDAO AOP interface.
 */
public interface IContestTeamInfoDAO extends IDAO<ContestTeamInfo, Integer, ContestTeamInfoDTO> {

  @Override
  public ContestTeamInfoDTO persist(ContestTeamInfoDTO dto) throws AppException;
}
