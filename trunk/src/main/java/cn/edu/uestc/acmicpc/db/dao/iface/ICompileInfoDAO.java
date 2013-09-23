package cn.edu.uestc.acmicpc.db.dao.iface;

import cn.edu.uestc.acmicpc.db.dto.impl.CompileInfoDTO;
import cn.edu.uestc.acmicpc.db.entity.CompileInfo;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * CompileInfoDAO AOP interface.
 */
public interface ICompileInfoDAO extends IDAO<CompileInfo, Integer, CompileInfoDTO> {

  @Override
  public CompileInfoDTO persist(CompileInfoDTO dto) throws AppException;
}
