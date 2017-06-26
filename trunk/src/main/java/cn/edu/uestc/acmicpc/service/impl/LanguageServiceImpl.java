package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.criteria.LanguageCriteria;
import cn.edu.uestc.acmicpc.db.dao.iface.LanguageDao;
import cn.edu.uestc.acmicpc.db.dto.field.LanguageFields;
import cn.edu.uestc.acmicpc.db.dto.impl.LanguageDto;
import cn.edu.uestc.acmicpc.service.iface.LanguageService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Description
 */
@Service
@Transactional(rollbackFor = Exception.class)
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
    LanguageCriteria criteria = new LanguageCriteria();
    try {
      List<LanguageDto> languageDtoList = languageDao.findAll(criteria, null,
          LanguageFields.ALL_FIELDS);
      languageDtoList.stream().forEach(dto -> languages.put(dto.getLanguageId(), dto));
    } catch (NullPointerException ignored) {
      // TODO(Yun Li): Fix it
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