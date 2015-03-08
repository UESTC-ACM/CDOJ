package cn.edu.uestc.acmicpc.db;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.base.Condition.ConditionType;
import cn.edu.uestc.acmicpc.db.dao.iface.StatusDao;
import cn.edu.uestc.acmicpc.db.dao.iface.UserDao;
import cn.edu.uestc.acmicpc.db.entity.Status;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.testing.PersistenceITTest;
import cn.edu.uestc.acmicpc.util.enums.OnlineJudgeReturnType;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.FieldNotUniqueException;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Test cases for {@link Status}.
 */
@SuppressWarnings("deprecation")
public class StatusDatabaseITTest extends PersistenceITTest {

  // TODO(fish): add status service test.

  @Autowired
  private StatusDao statusDao;

  @Autowired
  UserDao userDao;

  @Test
  public void testStatusDAO_withDistinctProblem() throws AppException, FieldNotUniqueException {
    User user = (User) userDao.getEntityByUniqueField("userName", "administrator");
    Assert.assertEquals(Integer.valueOf(1), user.getUserId());
    Assert.assertEquals("administrator", user.getUserName());
    Condition condition = new Condition();
    condition.addEntry("userId", ConditionType.EQUALS, user.getUserId());
    condition.addEntry("result", ConditionType.EQUALS,
        OnlineJudgeReturnType.OJ_AC.ordinal());
    List<?> results = statusDao.findAll("problemId", condition);
    Assert.assertEquals(results.size(), 1);
    Assert.assertEquals(results.get(0), Integer.valueOf(1));
  }
}
