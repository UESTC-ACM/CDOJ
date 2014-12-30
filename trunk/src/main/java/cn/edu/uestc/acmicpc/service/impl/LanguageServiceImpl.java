package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.dao.iface.LanguageDao;
import cn.edu.uestc.acmicpc.db.dto.impl.language.LanguageDto;
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

  private final LanguageDao languageDao;
  private List<LanguageDto> languageDtoList;

  @Autowired
  public LanguageServiceImpl(LanguageDao languageDao) {
    this.languageDao = languageDao;
  }

  @PostConstruct
  public void init() throws AppException {
    try {
      languageDtoList = languageDao.findAll(LanguageDto.class, LanguageDto.builder(),
          new Condition());
    } catch (NullPointerException e) {
      languageDtoList = new LinkedList<>();
    }
  }

  @Override
  public List<LanguageDto> getLanguageList() {
    return languageDtoList;
  }

  private LanguageDto getLanguage(Integer languageId) {
    for (LanguageDto languageDto : languageDtoList)
      if (languageDto.getLanguageId().equals(languageId))
        return languageDto;
    return null;
  }

  @Override
  public String getExtension(Integer languageId) {
    LanguageDto languageDto = getLanguage(languageId);
    if (languageDto == null)
      return null;
    return languageDto.getExtension();
  }

  @Override
  public String getLanguageName(Integer languageId) {
    LanguageDto languageDto = getLanguage(languageId);
    if (languageDto == null)
      return null;
    return languageDto.getName();
  }

  @Override
  public LanguageDao getDao() {
    return this.languageDao;
  }
}