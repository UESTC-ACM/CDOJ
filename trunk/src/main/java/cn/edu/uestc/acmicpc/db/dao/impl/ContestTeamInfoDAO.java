package cn.edu.uestc.acmicpc.db.dao.impl;

import cn.edu.uestc.acmicpc.db.dao.base.DAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IContestTeamInfoDAO;
import cn.edu.uestc.acmicpc.db.entity.ContestTeamInfo;

import org.springframework.stereotype.Repository;

/**
 * DAO for contestTeamInfo entity.
 */
@Repository
public class ContestTeamInfoDAO extends DAO<ContestTeamInfo, Integer>
    implements IContestTeamInfoDAO {

  @Override
  protected Class<Integer> getPKClass() {
    return Integer.class;
  }

  @Override
  protected Class<ContestTeamInfo> getReferenceClass() {
    return ContestTeamInfo.class;
  }
}
