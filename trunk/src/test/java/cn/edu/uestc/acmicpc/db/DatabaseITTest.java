package cn.edu.uestc.acmicpc.db;

import cn.edu.uestc.acmicpc.config.IntegrationTestContext;
import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.impl.StatusCondition;
import cn.edu.uestc.acmicpc.db.condition.impl.UserCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IContestDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IDepartmentDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IStatusDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IUserDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserListDTO;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.util.enums.OnlineJudgeReturnType;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Simple database test class.
 */
@ContextConfiguration(classes = {IntegrationTestContext.class})
public class DatabaseITTest extends AbstractTestNGSpringContextTests {

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
    Assert.assertEquals(user.getSchool(), "UESTC");
  }

  @Test
  public void testDAO_getEntityByUnique_withNotUniqueField() {
    try {
      userDAO.getEntityByUniqueField("password", "123456");
    } catch (AppException e) {
      Assert.assertEquals(e, new AppException("the value is not unique."));
    }
  }

  @Autowired
  private IContestDAO contestDAO;

  @Test
  public void testDAO_findAllByBuilder() throws AppException {
    UserCondition userCondition = new UserCondition();
    userCondition.startId = 2;
    userCondition.endId = 2;
    List<UserListDTO> result = userDAO.findAll(UserListDTO.class, UserListDTO.builder(),
        userCondition.getCondition());
    Assert.assertEquals(result.size(), 1);
    UserListDTO dto = result.get(0);
    Assert.assertEquals(dto.getUserId(), Integer.valueOf(2));
    Assert.assertEquals(dto.getUserName(), "admin");
    Assert.assertEquals(dto.getNickName(), "admin");
    Assert.assertEquals(dto.getEmail(), "acm_admin@uestc.edu.cn");
    Assert.assertEquals(dto.getSolved(), Integer.valueOf(0));
    Assert.assertEquals(dto.getTried(), Integer.valueOf(0));
    Assert.assertEquals(dto.getType(), Integer.valueOf(1));
    Assert.assertEquals(dto.getSchool(), "UESTC");
  }

  @Test
  public void testDAO_findAllByBuilder_withPageInfo() throws AppException {
    UserCondition userCondition = new UserCondition();
    PageInfo pageInfo = PageInfo.create(103L, 3L, 0, 2L);
    Condition condition = userCondition.getCondition();
    condition.setPageInfo(pageInfo);
    List<UserListDTO> result = userDAO.findAll(UserListDTO.class, UserListDTO.builder(),
        condition);
    Assert.assertEquals(result.size(), 3);
    Assert.assertEquals(result.get(0).getUserId(), Integer.valueOf(4));
    Assert.assertEquals(result.get(1).getUserId(), Integer.valueOf(5));
    Assert.assertEquals(result.get(2).getUserId(), Integer.valueOf(6));
  }

  @Test
  public void testDAO_getDTOByUniqueField_null() throws AppException {
    UserListDTO result = userDAO.getDTOByUniqueField(UserListDTO.class,
        UserListDTO.builder(), "userName", "wrongUser");
    Assert.assertNull(result);
  }

  @Test
  public void testDAO_getDTOByUniqueField_successful_intType() throws AppException {
    UserListDTO userDTO = userDAO.getDTOByUniqueField(UserListDTO.class,
        UserListDTO.builder(), "userId", 2);
    Assert.assertEquals(Integer.valueOf(2), userDTO.getUserId());
    Assert.assertEquals(userDTO.getUserName(), "admin");
    Assert.assertEquals(userDTO.getNickName(), "admin");
    Assert.assertEquals(userDTO.getEmail(), "acm_admin@uestc.edu.cn");
    Assert.assertEquals(userDTO.getSolved(), Integer.valueOf(0));
    Assert.assertEquals(userDTO.getTried(), Integer.valueOf(0));
    Assert.assertEquals(userDTO.getType(), Integer.valueOf(1));
    Assert.assertEquals(userDTO.getSchool(), "UESTC");
  }

  @Test
  public void testDAO_getDTOByUniqueField_successful_stringType() throws AppException {
    UserListDTO userDTO = userDAO.getDTOByUniqueField(UserListDTO.class,
        UserListDTO.builder(), "userName", "admin");
    Assert.assertEquals(userDTO.getUserId(), Integer.valueOf(2));
    Assert.assertEquals(userDTO.getUserName(), "admin");
    Assert.assertEquals(userDTO.getNickName(), "admin");
    Assert.assertEquals(userDTO.getEmail(), "acm_admin@uestc.edu.cn");
    Assert.assertEquals(userDTO.getSolved(), Integer.valueOf(0));
    Assert.assertEquals(userDTO.getTried(), Integer.valueOf(0));
    Assert.assertEquals(userDTO.getType(), Integer.valueOf(1));
    Assert.assertEquals(userDTO.getSchool(), "UESTC");
  }

  @Test
  public void testDAO_getDTOByUniqueField_failed() {
    try {
      userDAO.getDTOByUniqueField(UserListDTO.class,
          UserListDTO.builder(), "departmentId", 1);
      Assert.fail();
    } catch (AppException e) {
      Assert.assertEquals(e, new AppException("the value is not unique."));
    }
  }

  @Test(enabled = false)
  public void testSQLUpdate() throws AppException {
    statusCondition.contestId = 1;
    Map<String, Object> properties = new HashMap<>();
    properties.put("result", OnlineJudgeReturnType.OJ_AC.ordinal());
    statusDAO.updateEntitiesByCondition(properties, statusCondition.getCondition());
  }
}
