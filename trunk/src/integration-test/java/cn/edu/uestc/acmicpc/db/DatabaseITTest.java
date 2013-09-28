package cn.edu.uestc.acmicpc.db;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
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
import cn.edu.uestc.acmicpc.db.condition.impl.UserCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IContestDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IDepartmentDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IStatusDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IUserDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserSummaryDTO;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.exception.AppException;

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
  public void testDAO_getEntityByUnique() throws AppException {
    User user = (User) userDAO.getEntityByUniqueField("userName", "administrator");
    Assert.assertEquals("UESTC", user.getSchool());
  }

  @Test
  public void testDAO_getEntityByUnique_withNotUniqueField() {
    try {
      userDAO.getEntityByUniqueField("password", "123456");
    } catch (AppException e) {
      Assert.assertEquals(new AppException("the value is not unique."), e);
    }
  }

  @Autowired
  private IContestDAO contestDAO;

  @Test
  public void testDAO_findAddByBuilder() throws AppException {
    UserCondition userCondition = new UserCondition();
    userCondition.startId = 2;
    userCondition.endId = 2;
    List<UserSummaryDTO> result = userDAO.findAll(UserSummaryDTO.class, UserSummaryDTO.builder(),
        userCondition.getCondition());
    Assert.assertEquals(1, result.size());
    UserSummaryDTO dto = result.get(0);
    Assert.assertEquals(Integer.valueOf(2), dto.getUserId());
    Assert.assertEquals("admin", dto.getUserName());
    Assert.assertEquals("admin", dto.getNickName());
    Assert.assertEquals("acm_admin@uestc.edu.cn", dto.getEmail());
    Assert.assertEquals(Integer.valueOf(0), dto.getSolved());
    Assert.assertEquals(Integer.valueOf(0), dto.getTried());
    Assert.assertEquals(Integer.valueOf(1), dto.getType());
    Assert.assertEquals("UESTC", dto.getSchool());
    Assert.assertEquals(new Timestamp(1359523046000L), dto.getLastLogin());
  }

  @Test
  @Ignore
  public void testSQLUpdate() throws AppException {
    statusCondition.contestId = 1;
    Map<String, Object> properties = new HashMap<>();
    properties.put("result", Global.OnlineJudgeReturnType.OJ_AC.ordinal());
    statusDAO.updateEntitiesByCondition(properties, statusCondition.getCondition());
  }
}
