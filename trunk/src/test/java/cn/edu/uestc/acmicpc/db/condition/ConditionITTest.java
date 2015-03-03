package cn.edu.uestc.acmicpc.db.condition;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.impl.UserCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.ProblemDao;
import cn.edu.uestc.acmicpc.db.dao.iface.StatusDao;
import cn.edu.uestc.acmicpc.db.dao.iface.UserDao;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDto;
import cn.edu.uestc.acmicpc.testing.PersistenceITTest;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Test cases for conditions entities.
 */
@Deprecated
public class ConditionITTest extends PersistenceITTest {

  @Autowired
  private ProblemDao problemDao;

  @Autowired
  private UserDao userDao;

  @Test
  public void testCondition_emptyEntrySet() throws AppException {
    Condition condition = new Condition();
    List<UserDto> users = userDao.findAll(UserDto.class, UserDto.builder(), condition);
    Assert.assertEquals(6, users.size());
    for (int i = 0; i < users.size(); i++) {
      Assert.assertEquals(users.get(i).getUserId(), Integer.valueOf(i + 1));
    }
  }

  @Test
  public void testCondition_emptyEntrySetWithDescId() throws AppException {
    Condition condition = new Condition();
    condition.addOrder("userId", false);
    List<UserDto> users = userDao.findAll(UserDto.class, UserDto.builder(), condition);
    Assert.assertEquals(6, users.size());
    for (int i = 0; i < users.size(); i++) {
      Assert.assertEquals(users.get(i).getUserId(), Integer.valueOf(users.size() - i));
    }
  }

  @Test
  public void testCondition_count_emptyCondition() throws AppException {
    UserCondition userCondition = new UserCondition();
    Assert.assertEquals(userDao.count(userCondition.getCondition()), Long.valueOf(6));
  }

  @Test
  public void testCondition_count_withDepartmentId() throws AppException {
    UserCondition userCondition = new UserCondition();
    userCondition.departmentId = 1;
    Assert.assertEquals(userDao.count(userCondition.getCondition()), Long.valueOf(5));
  }

  @Autowired
  private StatusDao statusDao;
}
