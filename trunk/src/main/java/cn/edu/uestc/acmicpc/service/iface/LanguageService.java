package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.dto.impl.language.LanguageDTO;
import cn.edu.uestc.acmicpc.db.entity.Language;

import java.util.List;

/**
 * Language service interface.
 */
public interface LanguageService extends DatabaseService<Language, Integer> {

  /**
   * Get all {@link LanguageDTO} entities.
   *
   * @return list of all {@link LanguageDTO} entities.
   */
  public List<LanguageDTO> getLanguageList();

  /**
   * Get language extension by language id.
   *
   * @param languageId language's id.
   * @return language's extension.
   * @see LanguageDTO
   */
  public String getExtension(Integer languageId);

  /**
   * Get language name by language id.
   *
   * @param languageId language's id.
   * @return language's name.
   * @see LanguageDTO
   */
  public String getLanguageName(Integer languageId);
}
