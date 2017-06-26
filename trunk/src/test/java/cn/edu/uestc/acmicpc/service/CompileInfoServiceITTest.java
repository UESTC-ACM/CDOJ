package cn.edu.uestc.acmicpc.service;

import cn.edu.uestc.acmicpc.service.iface.CompileInfoService;
import cn.edu.uestc.acmicpc.testing.PersistenceITTest;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Integration test cases for
 * {@link cn.edu.uestc.acmicpc.service.iface.CompileInfoService}.
 */
public class CompileInfoServiceITTest extends PersistenceITTest {

  @Autowired
  private CompileInfoService compileInfoService;

  @Test
  public void testGetCompileInfo() throws AppException {
    Integer compileInfoId = compileInfoService.createCompileInfo("compile info");
    Assert.assertEquals(compileInfoService.getCompileInfo(compileInfoId), "compile info");
  }

  @Test
  public void testGetCompileInfo_noSuchCompileInfo() throws AppException {
    Integer compileInfoId = 2;
    Assert.assertNull(compileInfoService.getCompileInfo(compileInfoId));
  }

  @Test
  public void testUpdateCompileInfoContent() throws AppException {
    Integer compileInfoId = compileInfoService.createCompileInfo("compile info");
    String contentToUpdate = "new compile content";
    String contentOriginal = compileInfoService.getCompileInfo(compileInfoId);
    Assert.assertNotEquals(contentOriginal, contentToUpdate);
    compileInfoService.updateCompileInfoContent(compileInfoId, contentToUpdate);
    Assert.assertEquals(compileInfoService.getCompileInfo(compileInfoId), contentToUpdate);
  }
}
