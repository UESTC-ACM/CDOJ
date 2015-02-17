package cn.edu.uestc.acmicpc.db;

import cn.edu.uestc.acmicpc.db.dao.iface.TagDao;
import cn.edu.uestc.acmicpc.db.entity.Tag;
import cn.edu.uestc.acmicpc.testing.PersistenceITTest;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test cases for {@link Tag}.
 */
public class TagDatabaseITTest extends PersistenceITTest {

  @Autowired
  private TagDao tagDao;

  @Test
  public void testCount() throws AppException {
    Assert.assertEquals(tagDao.count(), Long.valueOf(5));
  }
}
