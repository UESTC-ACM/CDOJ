package cn.edu.uestc.acmicpc.db.dao.impl;

import org.springframework.stereotype.Repository;

import cn.edu.uestc.acmicpc.db.dao.base.DAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IContestDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.ContestDTO;
import cn.edu.uestc.acmicpc.db.entity.Contest;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * DAO for contest entity.
 */
@Repository
public class ContestDAO extends DAO<Contest, Integer, ContestDTO> implements IContestDAO {

  @Override
  protected Class<Integer> getPKClass() {
    return Integer.class;
  }

  @Override
  protected Class<Contest> getReferenceClass() {
    return Contest.class;
  }

  @Override
  public void delete(Integer key) throws AppException {
    throw new UnsupportedOperationException();
  }

  @Override
  public ContestDTO persist(ContestDTO dto) throws AppException {
    throw new UnsupportedOperationException();
  }
}
