package cn.edu.uestc.acmicpc.service;

import cn.edu.uestc.acmicpc.config.IntegrationTestContext;
import cn.edu.uestc.acmicpc.db.dao.iface.ILanguageDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.language.LanguageDTO;
import cn.edu.uestc.acmicpc.service.iface.LanguageService;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Integration test cases for {@link cn.edu.uestc.acmicpc.service.iface.LanguageService}.
 */
@ContextConfiguration(classes = {IntegrationTestContext.class})
public class LanguageServiceITTest extends AbstractTestNGSpringContextTests {

  @Autowired
  private LanguageService languageService;

  @Test
  public void testGetLanguageList() throws AppException {
    List<LanguageDTO> languageList = languageService.getLanguageList();
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

  @Test
  public void testGetDAO() throws AppException {
    Assert.assertTrue(languageService.getDAO() instanceof ILanguageDAO);
  }
}
