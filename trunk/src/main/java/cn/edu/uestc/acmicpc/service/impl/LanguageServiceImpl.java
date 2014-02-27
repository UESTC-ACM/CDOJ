package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.dao.iface.ILanguageDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.language.LanguageDTO;
import cn.edu.uestc.acmicpc.service.iface.LanguageService;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;

/**
 * Description
 */
@Service
public class LanguageServiceImpl extends AbstractService implements LanguageService {

  private final ILanguageDAO languageDAO;
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
      languageDTOList = new LinkedList<>();
    }
  }

  @Override
  public List<LanguageDTO> getLanguageList() {
    return languageDTOList;
  }

  private LanguageDTO getLanguage(Integer languageId) {
    for (LanguageDTO languageDTO : languageDTOList)
      if (languageDTO.getLanguageId().equals(languageId))
        return languageDTO;
    return null;
  }

  @Override
  public String getExtension(Integer languageId) {
    LanguageDTO languageDTO = getLanguage(languageId);
    if (languageDTO == null)
      return null;
    return languageDTO.getExtension();
  }

  @Override
  public String getLanguageName(Integer languageId) {
    LanguageDTO languageDTO = getLanguage(languageId);
    if (languageDTO == null)
      return null;
    return languageDTO.getName();
  }

  @Override
  public ILanguageDAO getDAO() {
    return this.languageDAO;
  }
}