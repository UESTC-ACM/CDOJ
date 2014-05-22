package cn.edu.uestc.acmicpc.db;

import cn.edu.uestc.acmicpc.config.IntegrationTestContext;
import cn.edu.uestc.acmicpc.db.dao.iface.UserDao;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.FieldNotUniqueException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * Test cases for AOP framework
 */
@ContextConfiguration(classes = {IntegrationTestContext.class})
public class AOPITTest extends AbstractTestNGSpringContextTests {

  @Autowired
  UserDao userDao;

  @Autowired
  DruidDataSource dataSource;

  @Test
  public void testFetchDataSource() {
    Assert.assertEquals(dataSource.getUrl(),
        "jdbc:mysql://localhost:3306/uestcojtest?useUnicode=true&characterEncoding=UTF-8");
  }

  @Test
  public void testDataBaseConnection() throws FieldNotUniqueException, AppException {
    User user = (User) userDao.getEntityByUniqueField("userName", "admin");
    Assert.assertEquals(user.getUserName(), "admin");
  }

  @Test
  public void testHQLQuery() throws AppException {
    userDao.findAll("from Article");
    userDao.findAll("from Code");
    userDao.findAll("from CompileInfo");
    userDao.findAll("from Contest");
    userDao.findAll("from ContestProblem");
    userDao.findAll("from ContestTeam");
    userDao.findAll("from ContestUser");
    userDao.findAll("from Department");
    userDao.findAll("from Language");
    userDao.findAll("from Message");
    userDao.findAll("from Code");
    userDao.findAll("from Problem");
    userDao.findAll("from ProblemTag");
    userDao.findAll("from Setting");
    userDao.findAll("from Status");
    userDao.findAll("from Tag");
    userDao.findAll("from Team");
    userDao.findAll("from TeamUser");
    userDao.findAll("from TrainingContest");
    userDao.findAll("from TrainingStatus");
    userDao.findAll("from TrainingUser");
    userDao.findAll("from User");
    userDao.findAll("from UserSerialKey");
  }
}
