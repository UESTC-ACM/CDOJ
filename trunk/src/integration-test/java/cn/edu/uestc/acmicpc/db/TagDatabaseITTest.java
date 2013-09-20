package cn.edu.uestc.acmicpc.db;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.edu.uestc.acmicpc.config.TestContext;
import cn.edu.uestc.acmicpc.db.dao.iface.ITagDAO;
import cn.edu.uestc.acmicpc.db.entity.Tag;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Test cases for {@link Tag}.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestContext.class })
public class TagDatabaseITTest {

  @Autowired
  private ITagDAO tagDAO;

  @SuppressWarnings("unchecked")
  @Test
  public void testQuery_fetchAllTags() throws AppException {
    List<Tag> tags = (List<Tag>) tagDAO.findAll();
    Assert.assertEquals("tag1", tags.get(0).getName());
    Assert.assertEquals("tag2", tags.get(1).getName());
    Assert.assertEquals("tag3", tags.get(2).getName());
    Assert.assertEquals("tag4", tags.get(3).getName());
    Assert.assertEquals("tag5", tags.get(4).getName());
  }

  @Test
  public void testCount() throws AppException {
    Assert.assertEquals(Long.valueOf(5), tagDAO.count());
  }
}
