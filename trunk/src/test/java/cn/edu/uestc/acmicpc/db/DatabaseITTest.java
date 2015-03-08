package cn.edu.uestc.acmicpc.db;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.impl.UserCondition;
import cn.edu.uestc.acmicpc.db.criteria.StatusCriteria;
import cn.edu.uestc.acmicpc.db.dao.iface.ContestDao;
import cn.edu.uestc.acmicpc.db.dao.iface.DepartmentDao;
import cn.edu.uestc.acmicpc.db.dao.iface.StatusDao;
import cn.edu.uestc.acmicpc.db.dao.iface.UserDao;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserListDto;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.testing.PersistenceITTest;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Simple database test class.
 */
@SuppressWarnings("deprecation")
public class DatabaseITTest extends PersistenceITTest {

  @Autowired
  private UserDao userDao;

  @Autowired
  private StatusDao statusDao;

  @Autowired
  private DepartmentDao departmentDao;

  private StatusCriteria statusCriteria;

  @Test
  public void testDAO_getEntityByUnique() throws AppException {
    User user = (User) userDao.getEntityByUniqueField("userName", "administrator");
    Assert.assertEquals(user.getSchool(), "UESTC");
  }

  @Test
  public void testDAO_getEntityByUnique_withNotUniqueField() {
    try {
      userDao.getEntityByUniqueField("password", "123456");
    } catch (AppException e) {
      Assert.assertEquals(e, new AppException("the value is not unique."));
    }
  }

  @Autowired
  private ContestDao contestDao;

  @Test
  public void testDAO_findAllByBuilder() throws AppException {
    UserCondition userCondition = new UserCondition();
    userCondition.startId = 2;
    userCondition.endId = 2;
    List<UserListDto> result = userDao.findAll(UserListDto.class, UserListDto.builder(),
        userCondition.getCondition());
    Assert.assertEquals(result.size(), 1);
    UserListDto dto = result.get(0);
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
    List<UserListDto> result = userDao.findAll(UserListDto.class, UserListDto.builder(),
        condition);
    Assert.assertEquals(result.size(), 3);
    Assert.assertEquals(result.get(0).getUserId(), Integer.valueOf(4));
    Assert.assertEquals(result.get(1).getUserId(), Integer.valueOf(5));
    Assert.assertEquals(result.get(2).getUserId(), Integer.valueOf(6));
  }

  @Test
  public void testDAO_getDtoByUniqueField_null() throws AppException {
    UserListDto result = userDao.getDtoByUniqueField(UserListDto.class,
        UserListDto.builder(), "userName", "wrongUser");
    Assert.assertNull(result);
  }

  @Test
  public void testDAO_getDtoByUniqueField_successful_intType() throws AppException {
    UserListDto userDto = userDao.getDtoByUniqueField(UserListDto.class,
        UserListDto.builder(), "userId", 2);
    Assert.assertEquals(Integer.valueOf(2), userDto.getUserId());
    Assert.assertEquals(userDto.getUserName(), "admin");
    Assert.assertEquals(userDto.getNickName(), "admin");
    Assert.assertEquals(userDto.getEmail(), "acm_admin@uestc.edu.cn");
    Assert.assertEquals(userDto.getSolved(), Integer.valueOf(0));
    Assert.assertEquals(userDto.getTried(), Integer.valueOf(0));
    Assert.assertEquals(userDto.getType(), Integer.valueOf(1));
    Assert.assertEquals(userDto.getSchool(), "UESTC");
  }

  @Test
  public void testDAO_getDtoByUniqueField_successful_stringType() throws AppException {
    UserListDto userDto = userDao.getDtoByUniqueField(UserListDto.class,
        UserListDto.builder(), "userName", "admin");
    Assert.assertEquals(userDto.getUserId(), Integer.valueOf(2));
    Assert.assertEquals(userDto.getUserName(), "admin");
    Assert.assertEquals(userDto.getNickName(), "admin");
    Assert.assertEquals(userDto.getEmail(), "acm_admin@uestc.edu.cn");
    Assert.assertEquals(userDto.getSolved(), Integer.valueOf(0));
    Assert.assertEquals(userDto.getTried(), Integer.valueOf(0));
    Assert.assertEquals(userDto.getType(), Integer.valueOf(1));
    Assert.assertEquals(userDto.getSchool(), "UESTC");
  }

  @Test
  public void testDAO_getDtoByUniqueField_failed() {
    try {
      userDao.getDtoByUniqueField(UserListDto.class,
          UserListDto.builder(), "departmentId", 1);
      Assert.fail();
    } catch (AppException e) {
      Assert.assertEquals(e, new AppException("the value is not unique."));
    }
  }

  @Test(enabled = false)
  public void testSQLUpdate() throws AppException {
    statusCriteria.contestId = 1;
    // TODO(Yun Li): Not support yet.
    //Map<String, Object> properties = new HashMap<>();
    //properties.put("result", OnlineJudgeReturnType.OJ_AC.ordinal());
    //statusDao.updateEntitiesByCondition(properties, statusCriteria.getCriteria());
  }
}
