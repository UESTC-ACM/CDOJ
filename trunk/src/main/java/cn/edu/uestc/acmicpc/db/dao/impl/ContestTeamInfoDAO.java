package cn.edu.uestc.acmicpc.db.dao.impl;

import org.springframework.stereotype.Repository;

import cn.edu.uestc.acmicpc.db.dao.base.DAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IContestTeamInfoDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.ContestTeamInfoDTO;
import cn.edu.uestc.acmicpc.db.entity.ContestTeamInfo;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * DAO for contestTeamInfo entity.
 */
@Repository
public class ContestTeamInfoDAO extends DAO<ContestTeamInfo, Integer, ContestTeamInfoDTO>
    implements IContestTeamInfoDAO {

  @Override
  protected Class<Integer> getPKClass() {
    return Integer.class;
  }

  @Override
  protected Class<ContestTeamInfo> getReferenceClass() {
    return ContestTeamInfo.class;
  }

  @Override
  public void delete(Integer key) throws AppException {
    throw new UnsupportedOperationException();
  }

  @Override
  public ContestTeamInfoDTO persist(ContestTeamInfoDTO dto) throws AppException {
    throw new UnsupportedOperationException();
  }
}
