package cn.edu.uestc.acmicpc.db.dao.impl;

import cn.edu.uestc.acmicpc.db.dao.base.DAO;
import cn.edu.uestc.acmicpc.db.dao.iface.ITeamDAO;
import cn.edu.uestc.acmicpc.db.entity.Team;

/**
 * DAO for team entity
 */
public class TeamDAO extends DAO<Team, Integer> implements ITeamDAO {
  @Override
  protected Class<Integer> getPKClass() {
    return Integer.class;
  }

  @Override
  protected Class<Team> getReferenceClass() {
    return Team.class;
  }
}
