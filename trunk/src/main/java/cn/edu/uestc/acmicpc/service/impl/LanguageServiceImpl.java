package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.criteria.impl.LanguageCriteria;
import cn.edu.uestc.acmicpc.db.dao.iface.LanguageDao;
import cn.edu.uestc.acmicpc.db.dto.field.LanguageFields;
import cn.edu.uestc.acmicpc.db.dto.impl.LanguageDto;
import cn.edu.uestc.acmicpc.service.iface.LanguageService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Description
 */
@Service
public class LanguageServiceImpl extends AbstractService implements LanguageService {

  private final LanguageDao languageDao;
  private final Map<Integer, LanguageDto> languages;

  @Autowired
  public LanguageServiceImpl(LanguageDao languageDao) {
    this.languageDao = languageDao;
    this.languages = new HashMap<>();
  }

  @PostConstruct
  public void init() throws AppException {
    languages.clear();
    try {
      LanguageCriteria criteria = new LanguageCriteria(LanguageFields.ALL_FIELDS);
      List<LanguageDto> languageDtoList = languageDao.findAll(criteria.getCriteria(), null);
      languageDtoList.stream().forEach(dto -> languages.put(dto.getLanguageId(), dto));
    } catch (NullPointerException e) {
    }
  }

  @Override
  public List<LanguageDto> getLanguageList() {
    return new LinkedList<>(languages.values());
  }

  private LanguageDto getLanguage(Integer languageId) {
    return languages.get(languageId);
  }

  @Override
  public String getExtension(Integer languageId) {
    LanguageDto languageDto = getLanguage(languageId);
    if (languageDto == null) {
      return null;
    }
    return languageDto.getExtension();
  }

  @Override
  public String getLanguageName(Integer languageId) {
    LanguageDto languageDto = getLanguage(languageId);
    if (languageDto == null) {
      return null;
    }
    return languageDto.getName();
  }
}