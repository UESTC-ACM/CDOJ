package cn.edu.uestc.acmicpc.db;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.edu.uestc.acmicpc.db.condition.impl.UserCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IUserDAO;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.ioc.condition.UserConditionAware;
import cn.edu.uestc.acmicpc.ioc.dao.UserDAOAware;
import cn.edu.uestc.acmicpc.util.exception.AppException;


/**
 * Test cases for {@link UserCondition} and {@link IUserDAO}.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-test.xml" })
public class UserConditionAndDAOTest implements UserConditionAware, UserDAOAware {

  @Before
  public void init() {
    userCondition.clear();
  }

  @Autowired
  private UserCondition userCondition;

  @Autowired
  private IUserDAO userDAO;

  @SuppressWarnings("unchecked")
  @Test
  public void testQuery_byName() throws AppException {
    userCondition.setUserName("admin");
    Assert.assertEquals("admin", userCondition.getUserName());
    List<User> users = (List<User>) userDAO.findAll(userCondition.getCondition());
    Assert.assertEquals(2, users.size());
    Assert.assertEquals("administrator", users.get(0).getUserName());
    Assert.assertEquals(Integer.valueOf(1), users.get(0).getUserId());
    Assert.assertEquals("admin", users.get(1).getUserName());
    Assert.assertEquals(Integer.valueOf(2), users.get(1).getUserId());
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testQuery_byDepartmentId() throws AppException {
    userCondition.setDepartmentId(1);
    Assert.assertEquals(Integer.valueOf(1), userCondition.getDepartmentId());
    List<User> users = (List<User>) userDAO.findAll(userCondition.getCondition());
    Assert.assertEquals(2, users.size());
    Assert.assertEquals("administrator", users.get(0).getUserName());
    Assert.assertEquals("admin", users.get(1).getUserName());
  }

  @Override
  public void setUserCondition(UserCondition userCondition) {
    this.userCondition = userCondition;
  }

  @Override
  public UserCondition getUserCondition() {
    return this.userCondition;
  }

  @Override
  public void setUserDAO(IUserDAO userDAO) {
    this.userDAO = userDAO;
  }
}
