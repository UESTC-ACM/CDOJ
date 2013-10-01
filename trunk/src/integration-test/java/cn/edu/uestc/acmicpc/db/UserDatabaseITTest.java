package cn.edu.uestc.acmicpc.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import cn.edu.uestc.acmicpc.config.IntegrationTestContext;
import cn.edu.uestc.acmicpc.db.condition.impl.UserCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IUserDAO;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Test cases for {@link User}.
 */
@ContextConfiguration(classes = { IntegrationTestContext.class })
public class UserDatabaseITTest extends AbstractTestNGSpringContextTests {

  @Autowired
  private IUserDAO userDAO;

  @SuppressWarnings("unchecked")
  @Test
  public void testQuery_byName() throws AppException {
    UserCondition condition = new UserCondition();
    condition.userName = "admin";
    List<User> users = (List<User>) userDAO.findAll(condition.getCondition());
    Assert.assertEquals(2, users.size());
    Assert.assertEquals("administrator", users.get(0).getUserName());
    Assert.assertEquals(Integer.valueOf(1), users.get(0).getUserId());
    Assert.assertEquals("admin", users.get(1).getUserName());
    Assert.assertEquals(Integer.valueOf(2), users.get(1).getUserId());
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testQuery_byDepartmentId() throws AppException {
    UserCondition condition = new UserCondition();
    condition.endId = 5;
    condition.departmentId = 1;
    List<User> users = (List<User>) userDAO.findAll(condition.getCondition());
    Assert.assertEquals(2, users.size());
    Assert.assertEquals("administrator", users.get(0).getUserName());
    Assert.assertEquals("admin", users.get(1).getUserName());
  }

  @Test
  public void testUserCondition_byStartIdAndEndId() throws AppException {
    UserCondition condition = new UserCondition();
    condition.startId = 2;
    condition.endId = 10;
    Assert.assertEquals(Long.valueOf(2), userDAO.count(condition.getCondition()));
  }
}
