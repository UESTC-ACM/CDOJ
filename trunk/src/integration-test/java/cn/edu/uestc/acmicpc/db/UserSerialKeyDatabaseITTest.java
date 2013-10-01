package cn.edu.uestc.acmicpc.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

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
@ContextConfiguration(classes = { IntegrationTestContext.class })
public class UserSerialKeyDatabaseITTest extends AbstractTestNGSpringContextTests {

  @Autowired
  private IUserDAO userDAO;

  @Autowired
  private IUserSerialKeyDAO userSerialKeyDAO;

  @Test
  public void testFindUserSerialKeyByUserName() throws FieldNotUniqueException, AppException {
    // TODO(fish): add test case and not assert null here.
    User user = (User) userDAO.getEntityByUniqueField("userName", "administrator");
    AssertJUnit.assertEquals(Integer.valueOf(1), user.getUserId());
    UserSerialKey userSerialKey =
        (UserSerialKey) userSerialKeyDAO.getEntityByUniqueField("userId", user.getUserId(),
            null, true);
    AssertJUnit.assertNull(userSerialKey);
  }
}
