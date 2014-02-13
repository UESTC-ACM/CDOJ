package cn.edu.uestc.acmicpc.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import cn.edu.uestc.acmicpc.config.IntegrationTestContext;
import cn.edu.uestc.acmicpc.db.dao.iface.IUserDAO;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.FieldNotUniqueException;
import com.jolbox.bonecp.BoneCPDataSource;

/**
 * Test cases for AOP framework
 */
@ContextConfiguration(classes = {IntegrationTestContext.class})
public class AOPITTest extends AbstractTestNGSpringContextTests {

  @Autowired
  IUserDAO userDAO;

  @Autowired
  BoneCPDataSource dataSource;

  @Test
  public void testFetchDataSource() {
    Assert.assertEquals(dataSource.getJdbcUrl(),
        "jdbc:mysql://localhost:3306/uestcojtest?useUnicode=true&characterEncoding=UTF-8");
  }

  @Test
  public void testDataBaseConnection() throws FieldNotUniqueException, AppException {
    User user = (User) userDAO.getEntityByUniqueField("userName", "admin");
    Assert.assertEquals(user.getUserName(), "admin");
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
