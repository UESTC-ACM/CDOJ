package cn.edu.uestc.acmicpc.db;

import cn.edu.uestc.acmicpc.config.IntegrationTestContext;
import cn.edu.uestc.acmicpc.db.dao.iface.TagDao;
import cn.edu.uestc.acmicpc.db.entity.Tag;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test cases for {@link Tag}.
 */
@ContextConfiguration(classes = { IntegrationTestContext.class })
public class TagDatabaseITTest extends AbstractTestNGSpringContextTests {

  @Autowired
  private TagDao tagDao;

  @Test
  public void testCount() throws AppException {
    Assert.assertEquals(tagDao.count(), Long.valueOf(5));
  }
}
