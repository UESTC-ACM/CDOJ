package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.dto.impl.language.LanguageDto;
import cn.edu.uestc.acmicpc.db.entity.Language;

import java.util.List;

/**
 * Language service interface.
 */
public interface LanguageService extends DatabaseService<Language, Integer> {

  /**
   * Get all {@link LanguageDto} entities.
   *
   * @return list of all {@link LanguageDto} entities.
   */
  public List<LanguageDto> getLanguageList();

  /**
   * Get language extension by language id.
   *
   * @param languageId
   *          language's id.
   * @return language's extension.
   * @see LanguageDto
   */
  public String getExtension(Integer languageId);

  /**
   * Get language name by language id.
   *
   * @param languageId
   *          language's id.
   * @return language's name.
   * @see LanguageDto
   */
  public String getLanguageName(Integer languageId);
}
