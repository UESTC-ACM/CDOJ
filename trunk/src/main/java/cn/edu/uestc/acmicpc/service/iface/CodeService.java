package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.dto.impl.code.CodeDTO;
import cn.edu.uestc.acmicpc.db.entity.Code;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Code service interface.
 */
public interface CodeService extends DatabaseService<Code, Integer> {

  /**
   * Get {@link CodeDTO} by code id.
   *
   * @param codeId code's id.
   * @return {@link CodeDTO} entity.
   * @throws AppException
   */
  public CodeDTO getCodeDTOByCodeId(Integer codeId) throws AppException;

  /**
   * Create a new code record by {@link CodeDTO}.
   *
   * @param codeDTO {@link CodeDTO} entity.
   * @return record's id.
   * @throws AppException
   */
  public Integer createNewCode(CodeDTO codeDTO) throws AppException;

}
