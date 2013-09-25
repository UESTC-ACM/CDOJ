package cn.edu.uestc.acmicpc.db;

import java.util.List;

import org.hibernate.criterion.Projections;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.edu.uestc.acmicpc.config.IntegrationTestContext;
import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.impl.ProblemCondition;
import cn.edu.uestc.acmicpc.db.condition.impl.StatusCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IProblemDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IStatusDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IUserDAO;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.FieldNotUniqueException;

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

  private ProblemCondition problemCondition;

  private StatusCondition statusCondition;

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
  @Ignore
  @Deprecated
  public void testClear() throws AppException {
//    problemCondition.setStartId(2);
//    problemCondition.setTitle("a+b problem");
    Assert.assertEquals(Long.valueOf(3), problemDAO.count(problemCondition.getCondition()));
    problemCondition.clear();
    Assert.assertEquals(Long.valueOf(5), problemDAO.count(problemCondition.getCondition()));
  }

  @SuppressWarnings("unchecked")
  @Test
  @Ignore
  @Deprecated
  public void testProjections() throws AppException, FieldNotUniqueException {
//    statusCondition.setUserId(1);
//    statusCondition.setResultId(Global.OnlineJudgeReturnType.OJ_AC.ordinal());
    Condition condition = statusCondition.getCondition();
    condition.addProjection(Projections.groupProperty("problemByProblemId.problemId"));
    List<Integer> results = (List<Integer>) statusDAO.findAll(condition);
    Assert.assertEquals(1, results.size());
    Assert.assertEquals(Integer.valueOf(1), results.get(0));
  }

  @Autowired
  private IStatusDAO statusDAO;
}
