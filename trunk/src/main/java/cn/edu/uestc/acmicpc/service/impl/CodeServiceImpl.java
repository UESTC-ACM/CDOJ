package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.criteria.CodeCriteria;
import cn.edu.uestc.acmicpc.db.dao.iface.CodeDao;
import cn.edu.uestc.acmicpc.db.dto.field.CodeFields;
import cn.edu.uestc.acmicpc.db.dto.impl.CodeDto;
import cn.edu.uestc.acmicpc.db.entity.Code;
import cn.edu.uestc.acmicpc.service.iface.CodeService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Description
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CodeServiceImpl extends AbstractService implements CodeService {

  private final CodeDao codeDao;

  @Autowired
  public CodeServiceImpl(CodeDao codeDao) {
    this.codeDao = codeDao;
  }

  @Override
  public CodeDto getCodeDto(Integer codeId, Set<CodeFields> codeFields) throws AppException {
    CodeCriteria codeCriteria = new CodeCriteria();
    codeCriteria.codeId = codeId;
    return codeDao.getUniqueDto(codeCriteria, codeFields);
  }

  private void updateCodeByCodeDto(Code code, CodeDto codeDto) {
    if (codeDto.getContent() != null) {
      code.setContent(codeDto.getContent());
    }
    if (codeDto.getShare() != null) {
      code.setShare(codeDto.getShare());
    }
  }

  @Override
  public Integer createNewCode(CodeDto codeDto) throws AppException {
    Code code = new Code();
    updateCodeByCodeDto(code, codeDto);
    codeDao.addOrUpdate(code);
    return code.getCodeId();
  }
}
