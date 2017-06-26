package cn.edu.uestc.acmicpc.service;

import cn.edu.uestc.acmicpc.db.dto.impl.LanguageDto;
import cn.edu.uestc.acmicpc.service.iface.LanguageService;
import cn.edu.uestc.acmicpc.testing.PersistenceITTest;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Integration test cases for {@link cn.edu.uestc.acmicpc.service.iface.LanguageService}.
 */
public class LanguageServiceITTest extends PersistenceITTest {

  @Autowired
  private LanguageService languageService;

  @Test
  public void testGetLanguageList() throws AppException {
    List<LanguageDto> languageList = languageService.getLanguageList();
    Assert.assertEquals(languageList.size(), 3);
  }

  @Test
  public void testGetExtension() throws AppException {
    Integer languageId = 2;
    String extension = languageService.getExtension(languageId);
    Assert.assertEquals(extension, ".cc");
  }

  @Test
  public void testGetExtension_noSuchLanguage() throws AppException {
    Integer languageId = 4;
    String extension = languageService.getExtension(languageId);
    Assert.assertNull(extension);
  }

  @Test
  public void testGetLanguageName() throws AppException {
    Integer languageId = 2;
    String languageName = languageService.getLanguageName(languageId);
    Assert.assertEquals(languageName, "C++");
  }

  @Test
  public void testGetLanguageName_noSuchLanguage() throws AppException {
    Integer languageId = 4;
    String languageName = languageService.getLanguageName(languageId);
    Assert.assertNull(languageName);
  }
}
