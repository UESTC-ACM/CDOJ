package cn.edu.uestc.acmicpc.service.testing;

import cn.edu.uestc.acmicpc.db.dto.field.CodeFields;
import cn.edu.uestc.acmicpc.db.dto.impl.CodeDto;
import cn.edu.uestc.acmicpc.service.iface.CodeService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Code provider for integration testing.
 */
@Component
public class CodeProvider {

  @Autowired
  private CodeService codeService;

  public CodeDto createCode() throws AppException {
    Integer codeId = codeService.createNewCode(
        CodeDto.builder().setShare(false).setContent("content").build());
    return codeService.getCodeDto(codeId, CodeFields.ALL_FIELDS);
  }
}
