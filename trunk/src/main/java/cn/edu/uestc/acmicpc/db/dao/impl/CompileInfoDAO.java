package cn.edu.uestc.acmicpc.db.dao.impl;

import org.springframework.stereotype.Repository;

import cn.edu.uestc.acmicpc.db.dao.base.DAO;
import cn.edu.uestc.acmicpc.db.dao.iface.ICompileInfoDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.CompileInfoDTO;
import cn.edu.uestc.acmicpc.db.entity.CompileInfo;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * DAO for compileinfo entity.
 */
@Repository
public class CompileInfoDAO extends DAO<CompileInfo, Integer, CompileInfoDTO>
    implements ICompileInfoDAO {

  @Override
  protected Class<Integer> getPKClass() {
    return Integer.class;
  }

  @Override
  protected Class<CompileInfo> getReferenceClass() {
    return CompileInfo.class;
  }

  @Override
  public void delete(Integer key) throws AppException {
    throw new UnsupportedOperationException();
  }

  @Override
  public CompileInfoDTO persist(CompileInfoDTO dto) throws AppException {
    throw new UnsupportedOperationException();
  }
}
