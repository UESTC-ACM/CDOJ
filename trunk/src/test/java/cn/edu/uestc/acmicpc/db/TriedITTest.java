package cn.edu.uestc.acmicpc.db;

import cn.edu.uestc.acmicpc.config.IntegrationTestContext;
import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.impl.ProblemCondition;
import cn.edu.uestc.acmicpc.db.condition.impl.StatusCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.ProblemDao;
import cn.edu.uestc.acmicpc.db.dao.iface.StatusDao;
import cn.edu.uestc.acmicpc.db.dao.iface.UserDao;
import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Test cases for problem tried property.
 */

@ContextConfiguration(classes = {IntegrationTestContext.class})
public class TriedITTest extends AbstractTestNGSpringContextTests {

  @SuppressWarnings("unchecked")
  @Test(enabled = false)
  @Deprecated
  public void testSyncUserTried() throws AppException {
    List<User> userList = (List<User>) userDao.findAll();
    for (User user : userList) {
//      statusCondition.clear();
//      statusCondition.setUserId(user.getUserId());
      Condition condition = statusCondition.getCondition();
      Long count = statusDao.count(condition);
      user.setTried(count.intValue());
      userDao.update(user);
    }
  }

  @SuppressWarnings("unchecked")
  @Test(enabled = false)
  @Deprecated
  public void testSyncProblemTried() throws AppException {
    // FIXME(fish): broken test case
    List<Problem> problemList = (List<Problem>) problemDao.findAll();
    for (Problem problem : problemList) {
//      statusCondition.clear();
//      problemCondition.clear();
//      statusCondition.setProblemId(problem.getProblemId());
//      problemCondition.setStartId(problem.getProblemId());
//      problemCondition.setEndId(problem.getProblemId());
      Long count = statusDao.count(statusCondition.getCondition());
      problem.setTried(count.intValue());
      Map<String, Object> properties = new HashMap<>();
      properties.put("tried", count.intValue());
      problemDao.updateEntitiesByCondition(properties, problemCondition.getCondition());
    }
  }

  @Autowired
  private StatusDao statusDao;

  @Autowired
  private ProblemDao problemDao;

  @Autowired
  private UserDao userDao;

  private StatusCondition statusCondition;

  private ProblemCondition problemCondition;
}
