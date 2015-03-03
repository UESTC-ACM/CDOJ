package cn.edu.uestc.acmicpc.db;

import cn.edu.uestc.acmicpc.db.condition.impl.UserCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.UserDao;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDto;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.testing.PersistenceITTest;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Test cases for {@link User}.
 */
@SuppressWarnings("deprecation")
public class UserDatabaseITTest extends PersistenceITTest {

  @Autowired
  private UserDao userDao;

  @Test
  public void testQuery_byName() throws AppException {
    UserCondition condition = new UserCondition();
    condition.userName = "admin";
    List<UserDto> users =
        userDao.findAll(UserDto.class, UserDto.builder(), condition.getCondition());
    Assert.assertEquals(users.size(), 2);
    Assert.assertEquals(users.get(0).getUserName(), "administrator");
    Assert.assertEquals(users.get(0).getUserId(), Integer.valueOf(1));
    Assert.assertEquals(users.get(1).getUserName(), "admin");
    Assert.assertEquals(users.get(1).getUserId(), Integer.valueOf(2));
  }

  @Test
  public void testQuery_byDepartmentId() throws AppException {
    UserCondition condition = new UserCondition();
    condition.endId = 3;
    condition.departmentId = 1;
    List<UserDto> users =
        userDao.findAll(UserDto.class, UserDto.builder(), condition.getCondition());
    Assert.assertEquals(2, users.size());
    Assert.assertEquals(users.get(0).getUserName(), "administrator");
    Assert.assertEquals(users.get(1).getUserName(), "admin");
  }

  @Test
  public void testUserCondition_byStartIdAndEndId() throws AppException {
    UserCondition condition = new UserCondition();
    condition.startId = 2;
    condition.endId = 10;
    Assert.assertEquals(userDao.count(condition.getCondition()), Long.valueOf(5));
  }
}
