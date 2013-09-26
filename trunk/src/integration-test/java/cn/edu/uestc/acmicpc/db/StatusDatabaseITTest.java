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
import cn.edu.uestc.acmicpc.db.condition.base.Condition.ConditionType;
import cn.edu.uestc.acmicpc.db.dao.iface.IStatusDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IUserDAO;
import cn.edu.uestc.acmicpc.db.entity.Status;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.FieldNotUniqueException;

/**
 * Test cases for {@link Status}.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { IntegrationTestContext.class })
public class StatusDatabaseITTest {

  // TODO(fish): add status service test.

  @Autowired
  private IStatusDAO statusDAO;

  @Autowired
  IUserDAO userDAO;

  @Test
  public void testStatusDAO_withDistinctProblem() throws AppException, FieldNotUniqueException {
    User user = userDAO.getEntityByUniqueField("userName", "administrator");
    Assert.assertEquals(Integer.valueOf(1), user.getUserId());
    Assert.assertEquals("administrator", user.getUserName());
    Condition condition = new Condition();
    condition.addEntry("userId", ConditionType.EQUALS, user.getUserId());
    condition.addEntry("result", ConditionType.EQUALS,
        Global.OnlineJudgeReturnType.OJ_AC.ordinal());
    List<?> results = statusDAO.findAll("problemId", condition);
    Assert.assertEquals(1, results.size());
    Assert.assertEquals(Integer.valueOf(1), results.get(0));
  }
}
