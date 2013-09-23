package cn.edu.uestc.acmicpc.db.dao.iface;

import cn.edu.uestc.acmicpc.db.dto.impl.CodeDTO;
import cn.edu.uestc.acmicpc.db.entity.Code;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * CodeDAO AOP interface.
 */
public interface ICodeDAO extends IDAO<Code, Integer, CodeDTO> {

  @Override
  public CodeDTO persist(CodeDTO dto) throws AppException;
}
