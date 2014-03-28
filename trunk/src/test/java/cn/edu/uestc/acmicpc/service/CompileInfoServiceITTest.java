package cn.edu.uestc.acmicpc.service;

import cn.edu.uestc.acmicpc.config.IntegrationTestContext;
import cn.edu.uestc.acmicpc.db.dao.iface.ICompileInfoDAO;
import cn.edu.uestc.acmicpc.service.iface.CompileInfoService;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Integration test cases for {@link cn.edu.uestc.acmicpc.service.iface.CompileInfoService}.
 */
@ContextConfiguration(classes = {IntegrationTestContext.class})
public class CompileInfoServiceITTest extends AbstractTestNGSpringContextTests {

  @Autowired
  private CompileInfoService compileInfoService;

  @Test
  public void testGetCompileInfo() throws AppException {
    Integer compileInfoId = 1;
    Assert.assertEquals(compileInfoService.getCompileInfo(compileInfoId), "compile info");
  }

  @Test
  public void testGetCompileInfo_noSuchCompileInfo() throws AppException {
    Integer compileInfoId = 2;
    Assert.assertNull(compileInfoService.getCompileInfo(compileInfoId));
  }

  @Test
  public void testGetDAO() throws AppException {
    Assert.assertTrue(compileInfoService.getDAO() instanceof ICompileInfoDAO);
  }

  @Test
  public void testUpdateCompileInfoContent() throws AppException {
    Integer compileInfoId = 1;
    String contentToUpdate = "new compile content";
    String contentOriginal = compileInfoService.getCompileInfo(compileInfoId);
    Assert.assertNotEquals(contentOriginal, contentToUpdate);
    compileInfoService.updateCompileInfoContent(compileInfoId, contentToUpdate);
    Assert.assertEquals(compileInfoService.getCompileInfo(compileInfoId), contentToUpdate);
    compileInfoService.updateCompileInfoContent(compileInfoId, contentOriginal);
  }
}
