package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.dao.iface.ILanguageDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.language.LanguageDTO;
import cn.edu.uestc.acmicpc.service.iface.LanguageService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;

/**
 * Description
 */
@Service
@Primary
public class LanguageServiceImpl extends AbstractService implements LanguageService {

  private ILanguageDAO languageDAO;
  private List<LanguageDTO> languageDTOList;

  @Autowired
  public LanguageServiceImpl(ILanguageDAO languageDAO) {
    this.languageDAO = languageDAO;
  }

  @PostConstruct
  public void init() throws AppException {
    try {
      languageDTOList = languageDAO.findAll(LanguageDTO.class, LanguageDTO.builder(),
          new Condition());
    } catch (NullPointerException e) {
      //TODO(fish) Please check this exception!
      languageDTOList = new LinkedList<>();
    }
  }

  @Override
  public List<LanguageDTO> getLanguageList() {
    return languageDTOList;
  }

  @Override
  public String getExtension(Integer languageId) {
    for (LanguageDTO languageDTO: languageDTOList)
      if (languageDTO.getLanguageId().equals(languageId))
        return languageDTO.getExtension();
    return null;
  }

  @Override
  public ILanguageDAO getDAO() {
    return this.languageDAO;
  }
}