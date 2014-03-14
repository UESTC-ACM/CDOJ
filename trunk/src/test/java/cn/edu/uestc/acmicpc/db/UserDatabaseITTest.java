package cn.edu.uestc.acmicpc.db;

import cn.edu.uestc.acmicpc.config.IntegrationTestContext;
import cn.edu.uestc.acmicpc.db.condition.impl.UserCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IUserDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDTO;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Test cases for {@link User}.
 */
@ContextConfiguration(classes = {IntegrationTestContext.class})
public class UserDatabaseITTest extends AbstractTestNGSpringContextTests {

  @Autowired
  private IUserDAO userDAO;

  @SuppressWarnings({"unchecked", "deprecation"})
  @Test
  public void testQuery_byName() throws AppException {
    UserCondition condition = new UserCondition();
    condition.userName = "admin";
    List<UserDTO> users =
        userDAO.findAll(UserDTO.class, UserDTO.builder(), condition.getCondition());
    Assert.assertEquals(users.size(), 2);
    Assert.assertEquals(users.get(0).getUserName(), "administrator");
    Assert.assertEquals(users.get(0).getUserId(), Integer.valueOf(1));
    Assert.assertEquals(users.get(1).getUserName(), "admin");
    Assert.assertEquals(users.get(1).getUserId(), Integer.valueOf(2));
  }

  @SuppressWarnings({"unchecked", "deprecation"})
  @Test
  public void testQuery_byDepartmentId() throws AppException {
    UserCondition condition = new UserCondition();
    condition.endId = 3;
    condition.departmentId = 1;
    List<UserDTO> users =
        userDAO.findAll(UserDTO.class, UserDTO.builder(), condition.getCondition());
    Assert.assertEquals(2, users.size());
    Assert.assertEquals(users.get(0).getUserName(), "administrator");
    Assert.assertEquals(users.get(1).getUserName(), "admin");
  }

  @Test
  public void testUserCondition_byStartIdAndEndId() throws AppException {
    UserCondition condition = new UserCondition();
    condition.startId = 2;
    condition.endId = 10;
    Assert.assertEquals(userDAO.count(condition.getCondition()), Long.valueOf(5));
  }
}
