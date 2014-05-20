package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.criteria.impl.CodeCriteria;
import cn.edu.uestc.acmicpc.db.dao.iface.ICodeDAO;
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

  private final ICodeDAO codeDAO;

  @Autowired
  public CodeServiceImpl(ICodeDAO codeDAO) {
    this.codeDAO = codeDAO;
  }

  @Override
  public CodeDto getCodeDto(Integer codeId,
                                    CodeFields codeFields) throws AppException {
    CodeCriteria codeCriteria = new CodeCriteria(CodeFields.ALL);
    codeCriteria.codeId = codeId;
    return codeDAO.getDtoByUniqueField(codeCriteria.getCriteria());
  }

  private void updateCodeByCodeDTO(Code code, CodeDto codeDto) {
    if (codeDto.getContent() != null)
      code.setContent(codeDto.getContent());
    if (codeDto.getShare() != null)
      code.setShare(codeDto.getShare());
  }

  @Override
  public Integer createNewCode(CodeDto codeDto) throws AppException {
    Code code = new Code();
    updateCodeByCodeDTO(code, codeDto);
    codeDAO.add(code);
    return code.getCodeId();
  }

  @Override
  public ICodeDAO getDAO() {
    return codeDAO;
  }
}
