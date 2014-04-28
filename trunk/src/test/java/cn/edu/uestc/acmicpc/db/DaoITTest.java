package cn.edu.uestc.acmicpc.db;

import cn.edu.uestc.acmicpc.config.IntegrationTestContext;
import cn.edu.uestc.acmicpc.db.dao.iface.IArticleDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test cases for Daos.
 */
@ContextConfiguration(classes = {IntegrationTestContext.class})
public class DaoITTest extends AbstractTestNGSpringContextTests {

  @Autowired
  IArticleDAO articleDAO;

  @Test
  public void test_daos_autowired_successful() {
    Assert.assertNotNull(articleDAO);
  }
}
