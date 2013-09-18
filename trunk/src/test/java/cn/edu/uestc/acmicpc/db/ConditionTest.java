package cn.edu.uestc.acmicpc.db;

import java.util.List;

import cn.edu.uestc.acmicpc.config.ApplicationContextConfig;
import org.hibernate.criterion.Projections;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.impl.ProblemCondition;
import cn.edu.uestc.acmicpc.db.condition.impl.StatusCondition;
import cn.edu.uestc.acmicpc.db.condition.impl.UserCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IProblemDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IStatusDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IUserDAO;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.FieldNotUniqueException;

/**
 * Test cases for conditions entities.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
    ApplicationContextConfig.class
})
public class ConditionTest {

  @Before
  public void init() {
    problemCondition.clear();
    statusCondition.clear();
    userCondition.clear();
  }

  @Autowired
  private IProblemDAO problemDAO;

  @Autowired
  private IUserDAO userDAO;

  @Autowired
  private UserCondition userCondition;

  @Autowired
  private ProblemCondition problemCondition;

  @Autowired
  private StatusCondition statusCondition;

  @Test
  public void testClear() throws AppException {
    problemCondition.setStartId(2);
    problemCondition.setTitle("a+b problem");
    Assert.assertEquals(Long.valueOf(3), problemDAO.count(problemCondition.getCondition()));
    problemCondition.clear();
    Assert.assertEquals(Long.valueOf(5), problemDAO.count(problemCondition.getCondition()));
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testProjections() throws AppException, FieldNotUniqueException {
    statusCondition.setUserId(1);
    statusCondition.setResultId(Global.OnlineJudgeReturnType.OJ_AC.ordinal());
    Condition condition = statusCondition.getCondition();
    condition.addProjection(Projections.groupProperty("problemByProblemId.problemId"));
    List<Integer> results = (List<Integer>) statusDAO.findAll(condition);
    Assert.assertEquals(1, results.size());
    Assert.assertEquals(Integer.valueOf(1), results.get(0));
  }

  @Autowired
  private IStatusDAO statusDAO;
}
