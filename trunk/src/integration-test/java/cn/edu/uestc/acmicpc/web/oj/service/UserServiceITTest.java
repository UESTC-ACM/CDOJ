package cn.edu.uestc.acmicpc.web.oj.service;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import cn.edu.uestc.acmicpc.config.IntegrationTestContext;
import cn.edu.uestc.acmicpc.db.condition.impl.UserCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDTO;
import cn.edu.uestc.acmicpc.service.iface.UserService;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.FieldNotUniqueException;

/** Integration test cases for {@link UserService}. */
@ContextConfiguration(classes = { IntegrationTestContext.class })
public class UserServiceITTest extends AbstractTestNGSpringContextTests {

  @Autowired
  UserService userService;

  @Test
  public void testGetUserByUserId() throws AppException {
    UserDTO user = userService.getUserDTOByUserId(2);
    AssertJUnit.assertEquals("admin", user.getUserName());
  }

  @Test
  public void testGetUserByUserName() throws AppException {
    UserDTO user = userService.getUserDTOByUserName("admin");
    AssertJUnit.assertEquals("admin", user.getUserName());
  }

  @Test
  public void testGetUserByUserEmail() throws AppException {
    UserDTO user = userService.getUserDTOByEmail("acm_admin@uestc.edu.cn");
    AssertJUnit.assertEquals("acm_admin@uestc.edu.cn", user.getEmail());
  }

  @Test
  public void testUpdateUser() throws AppException, FieldNotUniqueException {
    UserDTO user = userService.getUserDTOByUserName("administrator");
    user.setLastLogin(new Timestamp(new Date().getTime()));
    userService.updateUser(user);
  }

  @Test(enabled = false)
  public void testCreateNewUser() {
  }

  @Test
  public void testCount_emptyCondition() throws AppException {
    UserCondition condition = new UserCondition();
    AssertJUnit.assertEquals(Long.valueOf(3L), userService.count(condition));
  }

  @Test
  public void testCount_byStartId() throws AppException {
    UserCondition condition = new UserCondition();
    condition.startId = 2;
    AssertJUnit.assertEquals(Long.valueOf(2L), userService.count(condition));
  }

  @Test
  public void testCount_byEndId() throws AppException {
    UserCondition condition = new UserCondition();
    condition.endId = 2;
    AssertJUnit.assertEquals(Long.valueOf(2L), userService.count(condition));
  }

  @Test
  public void testCount_byStartIdAndEndId() throws AppException {
    UserCondition condition = new UserCondition();
    condition.startId = 2;
    condition.endId = 10;
    AssertJUnit.assertEquals(Long.valueOf(2L), userService.count(condition));
  }

  @Test
  public void testCount_byStartIdAndEndId_emptyResult() throws AppException {
    UserCondition condition = new UserCondition();
    condition.startId = 3;
    condition.endId = 2;
    AssertJUnit.assertEquals(Long.valueOf(0L), userService.count(condition));
  }

  @Test
  public void testCount_byDepartmentId() throws AppException {
    UserCondition condition = new UserCondition();
    condition.departmentId = 1;
    AssertJUnit.assertEquals(Long.valueOf(2L), userService.count(condition));
  }

  @Test
  public void testCount_bySchool() throws AppException {
    UserCondition condition = new UserCondition();
    condition.school = "UES";
    AssertJUnit.assertEquals(Long.valueOf(3L), userService.count(condition));
  }

  @Test
  public void testCount_bySchool_emptyRsult() throws AppException {
    UserCondition condition = new UserCondition();
    condition.school = "USE";
    AssertJUnit.assertEquals(Long.valueOf(0L), userService.count(condition));
  }

  @Test
  public void testCount_byUserName() throws AppException {
    UserCondition condition = new UserCondition();
    condition.userName = "admin";
    AssertJUnit.assertEquals(Long.valueOf(2L), userService.count(condition));
  }

  @Test
  public void testCount_byType() throws AppException {
    UserCondition condition = new UserCondition();
    condition.type = Global.AuthenticationType.CONSTANT.ordinal();
    AssertJUnit.assertEquals(Long.valueOf(1L), userService.count(condition));
  }
}
