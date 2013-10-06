package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.dto.impl.code.CodeDTO;
import cn.edu.uestc.acmicpc.db.entity.Code;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Description
 * TODO(mzry1992)
 */
public interface CodeService extends DatabaseService<Code, Integer> {

  public CodeDTO getCodeDTOByCodeId(Integer codeId) throws AppException;

  public Integer createNewCode(CodeDTO codeDTO) throws AppException;

}
