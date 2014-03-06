package cn.edu.uestc.acmicpc.db;

import cn.edu.uestc.acmicpc.config.IntegrationTestContext;
import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.impl.UserCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IProblemDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IStatusDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IUserDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDTO;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Test cases for conditions entities.
 */
@ContextConfiguration(classes = {IntegrationTestContext.class})
public class ConditionITTest extends AbstractTestNGSpringContextTests {

  @Autowired
  private IProblemDAO problemDAO;

  @Autowired
  private IUserDAO userDAO;

  @SuppressWarnings({"unchecked", "deprecation"})
  @Test
  public void testCondition_emptyEntrySet() throws AppException {
    Condition condition = new Condition();
    List<UserDTO> users = userDAO.findAll(UserDTO.class, UserDTO.builder(), condition);
    Assert.assertEquals(6, users.size());
    for (int i = 0; i < users.size(); i++) {
      Assert.assertEquals(users.get(i).getUserId(), Integer.valueOf(i + 1));
    }
  }

  @SuppressWarnings({"unchecked", "deprecation"})
  @Test
  public void testCondition_emptyEntrySetWithDescId() throws AppException {
    Condition condition = new Condition();
    condition.addOrder("userId", false);
    List<UserDTO> users = userDAO.findAll(UserDTO.class, UserDTO.builder(), condition);
    Assert.assertEquals(6, users.size());
    for (int i = 0; i < users.size(); i++) {
      Assert.assertEquals(users.get(i).getUserId(), Integer.valueOf(users.size() - i));
    }
  }

  @Test
  public void testCondition_count_emptyCondition() throws AppException {
    UserCondition userCondition = new UserCondition();
    Assert.assertEquals(userDAO.count(userCondition.getCondition()), Long.valueOf(6));
  }

  @Test
  public void testCondition_count_withDepartmentId() throws AppException {
    UserCondition userCondition = new UserCondition();
    userCondition.departmentId = 1;
    Assert.assertEquals(userDAO.count(userCondition.getCondition()), Long.valueOf(5));
  }

  @Autowired
  private IStatusDAO statusDAO;
}
