package cn.edu.uestc.acmicpc.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
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
import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Test cases for problem tried property.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { IntegrationTestContext.class })
public class TriedITTest {

  @Before
  public void init() {
    statusCondition.clear();
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testSyncUserTried() throws AppException {
    List<User> userList = (List<User>) userDAO.findAll();
    for (User user : userList) {
      statusCondition.clear();
      statusCondition.setUserId(user.getUserId());
      Condition condition = statusCondition.getCondition();
      Long count = statusDAO.count(condition);
      user.setTried(count.intValue());
      userDAO.update(user);
    }
  }

  @SuppressWarnings("unchecked")
  @Test
  @Ignore
  public void testSyncProblemTried() throws AppException {
    // FIXME broken test case
    List<Problem> problemList = (List<Problem>) problemDAO.findAll();
    for (Problem problem : problemList) {
      statusCondition.clear();
      problemCondition.clear();
      statusCondition.setProblemId(problem.getProblemId());
      problemCondition.setStartId(problem.getProblemId());
      problemCondition.setEndId(problem.getProblemId());
      Long count = statusDAO.count(statusCondition.getCondition());
      problem.setTried(count.intValue());
      Map<String, Object> properties = new HashMap<>();
      properties.put("tried", count.intValue());
      problemDAO.updateEntitiesByCondition(properties, problemCondition.getCondition());
    }
  }

  @Autowired
  private IStatusDAO statusDAO;

  @Autowired
  private IProblemDAO problemDAO;

  @Autowired
  private IUserDAO userDAO;

  @Autowired
  private StatusCondition statusCondition;

  @Autowired
  private ProblemCondition problemCondition;
}
