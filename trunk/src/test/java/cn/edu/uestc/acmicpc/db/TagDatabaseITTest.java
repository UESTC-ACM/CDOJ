package cn.edu.uestc.acmicpc.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import cn.edu.uestc.acmicpc.config.IntegrationTestContext;
import cn.edu.uestc.acmicpc.db.dao.iface.ITagDAO;
import cn.edu.uestc.acmicpc.db.entity.Tag;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Test cases for {@link Tag}.
 */
@ContextConfiguration(classes = {IntegrationTestContext.class})
public class TagDatabaseITTest extends AbstractTestNGSpringContextTests {

  @Autowired
  private ITagDAO tagDAO;

  @SuppressWarnings({"unchecked", "deprecation"})
  @Test
  public void testQuery_fetchAllTags() throws AppException {
    List<Tag> tags = (List<Tag>) tagDAO.findAll();
    Assert.assertEquals(tags.get(0).getName(), "tag1");
    Assert.assertEquals(tags.get(1).getName(), "tag2");
    Assert.assertEquals(tags.get(2).getName(), "tag3");
    Assert.assertEquals(tags.get(3).getName(), "tag4");
    Assert.assertEquals(tags.get(4).getName(), "tag5");
  }

  @Test
  public void testCount() throws AppException {
    Assert.assertEquals(tagDAO.count(), Long.valueOf(5));
  }
}
