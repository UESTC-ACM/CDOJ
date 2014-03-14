package cn.edu.uestc.acmicpc.db.dao.impl;

import cn.edu.uestc.acmicpc.db.dao.base.DAO;
import cn.edu.uestc.acmicpc.db.dao.iface.ITeamUserDAO;
import cn.edu.uestc.acmicpc.db.entity.TeamUser;

import org.springframework.stereotype.Repository;

/**
 * DAO for teamuser entity
 */
@Repository
public class TeamUserDAO extends DAO<TeamUser, Integer> implements ITeamUserDAO {
  @Override
  protected Class<Integer> getPKClass() {
    return Integer.class;
  }

  @Override
  protected Class<TeamUser> getReferenceClass() {
    return TeamUser.class;
  }
}
