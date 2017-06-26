package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.dto.field.CodeFields;
import cn.edu.uestc.acmicpc.db.dto.impl.CodeDto;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import java.util.Set;

/**
 * Code service interface.
 */
public interface CodeService {

  /**
   * Get {@link CodeDto} by code id.
   *
   * @param codeId     code's id.
   * @param codeFields fields which is needed.
   * @return {@link CodeDto} entity.
   * @throws AppException
   */
  public CodeDto getCodeDto(Integer codeId, Set<CodeFields> codeFields) throws AppException;

  /**
   * Create a new code record by {@link CodeDto}.
   *
   * @param codeDto {@link CodeDto} entity.
   * @return record's id.
   * @throws AppException
   */
  public Integer createNewCode(CodeDto codeDto) throws AppException;

}
