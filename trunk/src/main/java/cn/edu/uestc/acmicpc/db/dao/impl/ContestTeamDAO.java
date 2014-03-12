package cn.edu.uestc.acmicpc.db.dao.impl;

import cn.edu.uestc.acmicpc.db.dao.base.DAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IContestTeamDAO;
import cn.edu.uestc.acmicpc.db.entity.ContestTeam;

import org.springframework.stereotype.Repository;

/**
 * DAO for contestteam entity.
 */
@Repository
public class ContestTeamDAO extends DAO<ContestTeam, Integer> implements IContestTeamDAO {

  @Override
  protected Class<Integer> getPKClass() {
    return Integer.class;
  }

  @Override
  protected Class<ContestTeam> getReferenceClass() {
    return ContestTeam.class;
  }
}
