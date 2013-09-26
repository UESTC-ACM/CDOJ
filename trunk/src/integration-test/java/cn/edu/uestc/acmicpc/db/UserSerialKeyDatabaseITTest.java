package cn.edu.uestc.acmicpc.db;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.edu.uestc.acmicpc.config.IntegrationTestContext;
import cn.edu.uestc.acmicpc.db.dao.iface.IUserDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IUserSerialKeyDAO;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.db.entity.UserSerialKey;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.FieldNotUniqueException;

/**
 * Test cases for {@link UserSerialKey}.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { IntegrationTestContext.class })
public class UserSerialKeyDatabaseITTest {

  @Autowired
  private IUserDAO userDAO;

  @Autowired
  private IUserSerialKeyDAO userSerialKeyDAO;

  @Test
  public void testFindUserSerialKeyByUserName() throws FieldNotUniqueException, AppException {
    // TODO(fish): add test case and not assert null here.
    User user = userDAO.getEntityByUniqueField("userName", "administrator");
    Assert.assertEquals(Integer.valueOf(1), user.getUserId());
    UserSerialKey userSerialKey =
        userSerialKeyDAO.getEntityByUniqueField("userId", user, "userByUserId", true);
    Assert.assertNull(userSerialKey);
  }
}
