package cn.edu.uestc.acmicpc.db;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.edu.uestc.acmicpc.config.IntegrationTestContext;
import cn.edu.uestc.acmicpc.db.dao.iface.IUserDAO;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.util.ObjectUtil;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.FieldNotUniqueException;

import com.jolbox.bonecp.BoneCPDataSource;

/**
 * Test cases for AOP framework
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { IntegrationTestContext.class })
public class AOPITTest {

  @Autowired
  IUserDAO userDAO;

  public void setUserDAO(IUserDAO userDAO) {
    this.userDAO = userDAO;
  }

  @Autowired
  BoneCPDataSource dataSource;

  @Test
  public void testFetchDataSource() {
    Assert.assertEquals(
        "jdbc:mysql://localhost:3306/uestcojtest?useUnicode=true&characterEncoding=UTF-8",
        dataSource.getJdbcUrl());
  }

  @Test
  public void testDataBaseConnection() throws FieldNotUniqueException, AppException {
    User user = userDAO.getEntityByUniqueField("userName", "admin");
    Assert.assertEquals("admin", user.getUserName());
  }

  @Test
  public void testHQLQuery() throws AppException {
    String hql = "select distinct departmentByDepartmentId from User where userId>=1 and userId<=3";
    List<?> results = userDAO.findAll(hql);
    System.err.println(ObjectUtil.toString(results));
  }
}
