package cn.edu.uestc.acmicpc.db;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.edu.uestc.acmicpc.config.IntegrationTestContext;
import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.impl.UserCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IProblemDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IStatusDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IUserDAO;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Test cases for conditions entities.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { IntegrationTestContext.class })
public class ConditionITTest {

  @Autowired
  private IProblemDAO problemDAO;

  @Autowired
  private IUserDAO userDAO;

  @SuppressWarnings("unchecked")
  @Test
  public void testCondition_emptyEntrySet() throws AppException {
    Condition condition = new Condition();
    List<User> users = (List<User>) userDAO.findAll(condition);
    Assert.assertEquals(3, users.size());
    for (int i = 0 ; i < users.size(); i++) {
      Assert.assertEquals(Integer.valueOf(i + 1), users.get(i).getUserId());
    }
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testCondition_emptyEntrySetWithDescId() throws AppException {
    Condition condition = new Condition();
    condition.addOrder("userId", false);
    List<User> users = (List<User>) userDAO.findAll(condition);
    Assert.assertEquals(3, users.size());
    for (int i = 0 ; i < users.size(); i++) {
      Assert.assertEquals(Integer.valueOf(users.size() - i), users.get(i).getUserId());
    }
  }

  @Test
  public void testCondition_count_emptyCondition() throws AppException {
    UserCondition userCondition = new UserCondition();
    Assert.assertEquals(Long.valueOf(3), userDAO.count(userCondition.getCondition()));
  }

  @Test
  public void testCondition_count_withDepartmentId() throws AppException {
    UserCondition userCondition = new UserCondition();
    userCondition.departmentId = 1;
    Assert.assertEquals(Long.valueOf(2), userDAO.count(userCondition.getCondition()));
  }

  @Autowired
  private IStatusDAO statusDAO;
}
