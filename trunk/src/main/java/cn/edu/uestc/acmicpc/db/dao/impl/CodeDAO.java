package cn.edu.uestc.acmicpc.db.dao.impl;

import org.springframework.stereotype.Repository;

import cn.edu.uestc.acmicpc.db.dao.base.DAO;
import cn.edu.uestc.acmicpc.db.dao.iface.ICodeDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.CodeDTO;
import cn.edu.uestc.acmicpc.db.entity.Code;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * DAO for code entity.
 */
@Repository
public class CodeDAO extends DAO<Code, Integer, CodeDTO> implements ICodeDAO {

  @Override
  protected Class<Integer> getPKClass() {
    return Integer.class;
  }

  @Override
  protected Class<Code> getReferenceClass() {
    return Code.class;
  }

  @Override
  public void delete(Integer key) throws AppException {
    throw new UnsupportedOperationException();
  }

  @Override
  public CodeDTO persist(CodeDTO dto) throws AppException {
    throw new UnsupportedOperationException();
  }
}
