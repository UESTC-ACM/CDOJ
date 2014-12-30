package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.criteria.impl.CodeCriteria;
import cn.edu.uestc.acmicpc.db.dao.iface.CodeDao;
import cn.edu.uestc.acmicpc.db.dto.field.CodeFields;
import cn.edu.uestc.acmicpc.db.dto.impl.CodeDto;
import cn.edu.uestc.acmicpc.db.entity.Code;
import cn.edu.uestc.acmicpc.service.iface.CodeService;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description
 */
@Service
public class CodeServiceImpl extends AbstractService implements CodeService {

  private final CodeDao codeDao;

  @Autowired
  public CodeServiceImpl(CodeDao codeDao) {
    this.codeDao = codeDao;
  }

  @Override
  public CodeDto getCodeDto(Integer codeId,
      CodeFields codeFields) throws AppException {
    CodeCriteria codeCriteria = new CodeCriteria(CodeFields.ALL_FIELDS);
    codeCriteria.codeId = codeId;
    return codeDao.getDtoByUniqueField(codeCriteria.getCriteria());
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
    codeDao.add(code);
    return code.getCodeId();
  }

  @Override
  public CodeDao getDao() {
    return codeDao;
  }
}
