package cn.edu.uestc.acmicpc.db.dao.impl;

import cn.edu.uestc.acmicpc.db.dao.base.DAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IContestUserDAO;
import cn.edu.uestc.acmicpc.db.entity.ContestUser;

import org.springframework.stereotype.Repository;

/**
 * DAO for contestuser entity.
 */
@Repository
public class ContestUserDAO extends DAO<ContestUser, Integer> implements IContestUserDAO {

  @Override
  protected Class<Integer> getPKClass() {
    return Integer.class;
  }

  @Override
  protected Class<ContestUser> getReferenceClass() {
    return ContestUser.class;
  }
}
