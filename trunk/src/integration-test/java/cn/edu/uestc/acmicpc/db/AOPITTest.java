package cn.edu.uestc.acmicpc.db;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.edu.uestc.acmicpc.config.IntegrationTestContext;
import cn.edu.uestc.acmicpc.db.dao.iface.IUserDAO;
import cn.edu.uestc.acmicpc.db.entity.User;
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
    User user = (User) userDAO.getEntityByUniqueField("userName", "admin");
    Assert.assertEquals("admin", user.getUserName());
  }

  @Test
  public void testHQLQuery() throws AppException {
    userDAO.findAll("from Article");
    userDAO.findAll("from Code");
    userDAO.findAll("from CompileInfo");
    userDAO.findAll("from Contest");
    userDAO.findAll("from ContestProblem");
    userDAO.findAll("from ContestTeamInfo");
    userDAO.findAll("from ContestUser");
    userDAO.findAll("from Department");
    userDAO.findAll("from Language");
    userDAO.findAll("from Message");
    userDAO.findAll("from Code");
    userDAO.findAll("from Problem");
    userDAO.findAll("from ProblemTag");
    userDAO.findAll("from Status");
    userDAO.findAll("from Tag");
    userDAO.findAll("from TrainingContest");
    userDAO.findAll("from TrainingStatus");
    userDAO.findAll("from TrainingUser");
    userDAO.findAll("from User");
    userDAO.findAll("from UserSerialKey");
  }
}
