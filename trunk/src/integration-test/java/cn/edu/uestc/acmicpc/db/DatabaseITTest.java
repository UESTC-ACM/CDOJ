package cn.edu.uestc.acmicpc.db;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.edu.uestc.acmicpc.config.TestContext;
import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.impl.StatusCondition;
import cn.edu.uestc.acmicpc.db.condition.impl.UserCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IContestDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IDepartmentDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IStatusDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IUserDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.ContestDTO;
import cn.edu.uestc.acmicpc.db.entity.Contest;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.FieldNotUniqueException;

/**
 * Simple database test class.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestContext.class })
public class DatabaseITTest {

  private static final Logger LOGGER = LogManager.getLogger(DatabaseITTest.class);

  @Autowired
  private IUserDAO userDAO;

  @Autowired
  private IStatusDAO statusDAO;

  @Autowired
  private IDepartmentDAO departmentDAO;

  @Autowired
  private UserCondition userCondition;

  @Autowired
  private StatusCondition statusCondition;

  @Test
  public void testDAO_getEntityByUnique() throws FieldNotUniqueException, AppException {
    User user = userDAO.getEntityByUniqueField("userName", "administrator");
    Assert.assertEquals("UESTC", user.getSchool());
  }

  @Test(expected = FieldNotUniqueException.class)
  public void testDAO_getEntityByUnique_withNotUniqueField() throws FieldNotUniqueException,
      AppException {
    userDAO.getEntityByUniqueField("password", "123456");
  }

  @Autowired
  private IContestDAO contestDAO;

  @Test
  public void testContestDAO() {
    try {
      ContestDTO contestDTO = ContestDTO.builder()
          .setIsVisible(true)
          .setTitle("test title").build();
      Contest contest = contestDTO.getEntity();
      contestDAO.add(contest);
    } catch (AppException e) {
      LOGGER.error(e);
    }
  }

  @Test
  @Ignore(value = "not stable")
  public void testSQLGenerator() throws AppException {
    userCondition.clear();
    userCondition.setUserName("userName");
    userCondition.setStartId(10);
    Condition condition = userCondition.getCondition();
    Junction junction = Restrictions.disjunction();
    junction.add(Restrictions.eq("userName", "userName"));
    junction.add(Restrictions.ge("userId", 1));
    condition.addCriterion(junction);
    Assert.assertEquals(
        "where userName like %userName% and userId>=10 and (userName=userName or userId>=1)",
        userDAO.getSQLString(condition));
  }

  @Test
  public void testSQLUpdate() throws AppException {
    statusCondition.clear();
    statusCondition.setContestId(1);
    Map<String, Object> properties = new HashMap<>();
    properties.put("result", Global.OnlineJudgeReturnType.OJ_AC.ordinal());
    statusDAO.updateEntitiesByCondition(properties, statusCondition.getCondition());
  }
}
