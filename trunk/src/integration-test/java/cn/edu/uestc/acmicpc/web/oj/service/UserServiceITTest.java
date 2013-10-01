package cn.edu.uestc.acmicpc.web.oj.service;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.edu.uestc.acmicpc.config.IntegrationTestContext;
import cn.edu.uestc.acmicpc.db.condition.impl.UserCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDTO;
import cn.edu.uestc.acmicpc.service.iface.UserService;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.FieldNotUniqueException;

/** Integration test cases for {@link UserService}. */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { IntegrationTestContext.class })
public class UserServiceITTest {

  @Autowired
  UserService userService;

  public void testGetUserByUserId() throws AppException {
    UserDTO user = userService.getUserDTOByUserId(2);
    Assert.assertEquals("admin", user.getUserName());
  }

  @Test
  public void testGetUserByUserName() throws AppException {
    UserDTO user = userService.getUserDTOByUserName("admin");
    Assert.assertEquals("admin", user.getUserName());
  }

  @Test
  public void testGetUserByUserEmail() throws AppException {
    UserDTO user = userService.getUserDTOByEmail("acm_admin@uestc.edu.cn");
    Assert.assertEquals("acm_admin@uestc.edu.cn", user.getEmail());
  }

  @Test
  public void testUpdateUser() throws AppException, FieldNotUniqueException {
    UserDTO user = userService.getUserDTOByUserName("administrator");
    user.setLastLogin(new Timestamp(new Date().getTime()));
    userService.updateUser(user);
  }

  @Test
  @Ignore
  public void testCreateNewUser() {
  }

  @Test
  public void testCount_emptyCondition() throws AppException {
    UserCondition condition = new UserCondition();
    Assert.assertEquals(Long.valueOf(3L), userService.count(condition));
  }

  @Test
  public void testCount_byStartId() throws AppException {
    UserCondition condition = new UserCondition();
    condition.startId = 2;
    Assert.assertEquals(Long.valueOf(2L), userService.count(condition));
  }

  @Test
  public void testCount_byEndId() throws AppException {
    UserCondition condition = new UserCondition();
    condition.endId = 2;
    Assert.assertEquals(Long.valueOf(2L), userService.count(condition));
  }

  @Test
  public void testCount_byStartIdAndEndId() throws AppException {
    UserCondition condition = new UserCondition();
    condition.startId = 2;
    condition.endId = 10;
    Assert.assertEquals(Long.valueOf(2L), userService.count(condition));
  }

  @Test
  public void testCount_byStartIdAndEndId_emptyResult() throws AppException {
    UserCondition condition = new UserCondition();
    condition.startId = 3;
    condition.endId = 2;
    Assert.assertEquals(Long.valueOf(0L), userService.count(condition));
  }

  @Test
  public void testCount_byDepartmentId() throws AppException {
    UserCondition condition = new UserCondition();
    condition.departmentId = 1;
    Assert.assertEquals(Long.valueOf(2L), userService.count(condition));
  }

  @Test
  public void testCount_bySchool() throws AppException {
    UserCondition condition = new UserCondition();
    condition.school = "UES";
    Assert.assertEquals(Long.valueOf(3L), userService.count(condition));
  }

  @Test
  public void testCount_bySchool_emptyRsult() throws AppException {
    UserCondition condition = new UserCondition();
    condition.school = "USE";
    Assert.assertEquals(Long.valueOf(0L), userService.count(condition));
  }

  @Test
  public void testCount_byUserName() throws AppException {
    UserCondition condition = new UserCondition();
    condition.userName = "admin";
    Assert.assertEquals(Long.valueOf(2L), userService.count(condition));
  }

  @Test
  public void testCount_byType() throws AppException {
    UserCondition condition = new UserCondition();
    condition.type = Global.AuthenticationType.CONSTANT.ordinal();
    Assert.assertEquals(Long.valueOf(1L), userService.count(condition));
  }
}
