package cn.edu.uestc.acmicpc.db;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.edu.uestc.acmicpc.config.IntegrationTestContext;
import cn.edu.uestc.acmicpc.db.condition.impl.StatusCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IContestDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IDepartmentDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IStatusDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IUserDAO;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.FieldNotUniqueException;

/**
 * Simple database test class.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { IntegrationTestContext.class })
public class DatabaseITTest {

  @Autowired
  private IUserDAO userDAO;

  @Autowired
  private IStatusDAO statusDAO;

  @Autowired
  private IDepartmentDAO departmentDAO;

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
  @Ignore
  @Deprecated
  public void testSQLUpdate() throws AppException {
    statusCondition.clear();
//    statusCondition.setContestId(1);
    Map<String, Object> properties = new HashMap<>();
    properties.put("result", Global.OnlineJudgeReturnType.OJ_AC.ordinal());
    statusDAO.updateEntitiesByCondition(properties, statusCondition.getCondition());
  }
}
