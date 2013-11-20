package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.dao.iface.ICodeDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.code.CodeDTO;
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
  public CodeDTO getCodeDTOByCodeId(Integer codeId) throws AppException {
    return codeDAO.getDTOByUniqueField(CodeDTO.class, CodeDTO.builder(), "codeId", codeId);
  }

  private void updateCodeByCodeDTO(Code code, CodeDTO codeDTO) {
    if (codeDTO.getContent() != null)
      code.setContent(codeDTO.getContent());
    if (codeDTO.getShare() != null)
      code.setShare(codeDTO.getShare());
  }

  @Override
  public Integer createNewCode(CodeDTO codeDTO) throws AppException {
    Code code = new Code();
    updateCodeByCodeDTO(code, codeDTO);
    codeDAO.add(code);
    return code.getCodeId();
  }

  @Override
  public ICodeDAO getDAO() {
    return codeDAO;
  }
}
