package cn.edu.uestc.acmicpc.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import cn.edu.uestc.acmicpc.config.IntegrationTestContext;
import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.base.Condition.ConditionType;
import cn.edu.uestc.acmicpc.db.dao.iface.IStatusDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IUserDAO;
import cn.edu.uestc.acmicpc.db.entity.Status;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.FieldNotUniqueException;
import cn.edu.uestc.acmicpc.util.settings.Global;

/**
 * Test cases for {@link Status}.
 */
@ContextConfiguration(classes = {IntegrationTestContext.class})
public class StatusDatabaseITTest extends AbstractTestNGSpringContextTests {

  // TODO(fish): add status service test.

  @Autowired
  private IStatusDAO statusDAO;

  @Autowired
  IUserDAO userDAO;

  @Test
  public void testStatusDAO_withDistinctProblem() throws AppException, FieldNotUniqueException {
    User user = (User) userDAO.getEntityByUniqueField("userName", "administrator");
    Assert.assertEquals(Integer.valueOf(1), user.getUserId());
    Assert.assertEquals("administrator", user.getUserName());
    Condition condition = new Condition();
    condition.addEntry("userId", ConditionType.EQUALS, user.getUserId());
    condition.addEntry("result", ConditionType.EQUALS,
        Global.OnlineJudgeReturnType.OJ_AC.ordinal());
    List<?> results = statusDAO.findAll("problemId", condition);
    Assert.assertEquals(results.size(), 1);
    Assert.assertEquals(results.get(0), Integer.valueOf(1));
  }
}
