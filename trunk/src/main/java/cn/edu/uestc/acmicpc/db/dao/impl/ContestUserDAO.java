package cn.edu.uestc.acmicpc.db.dao.impl;

import org.springframework.stereotype.Repository;

import cn.edu.uestc.acmicpc.db.dao.base.DAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IContestUserDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.ContestUserDTO;
import cn.edu.uestc.acmicpc.db.entity.ContestUser;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * DAO for contestuser entity.
 */
@Repository
public class ContestUserDAO extends DAO<ContestUser, Integer, ContestUserDTO>
    implements IContestUserDAO {

  @Override
  protected Class<Integer> getPKClass() {
    return Integer.class;
  }

  @Override
  protected Class<ContestUser> getReferenceClass() {
    return ContestUser.class;
  }

  @Override
  public void delete(Integer key) throws AppException {
    throw new UnsupportedOperationException();
  }

  @Override
  public ContestUserDTO persist(ContestUserDTO dto) throws AppException {
    throw new UnsupportedOperationException();
  }
}
