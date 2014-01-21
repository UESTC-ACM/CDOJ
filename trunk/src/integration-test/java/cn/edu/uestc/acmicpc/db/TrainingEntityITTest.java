package cn.edu.uestc.acmicpc.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import cn.edu.uestc.acmicpc.config.IntegrationTestContext;
import cn.edu.uestc.acmicpc.db.dao.iface.ITrainingContestDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.ITrainingStatusDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.ITrainingUserDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IUserDAO;
import cn.edu.uestc.acmicpc.db.entity.TrainingContest;
import cn.edu.uestc.acmicpc.db.entity.TrainingStatus;
import cn.edu.uestc.acmicpc.db.entity.TrainingUser;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Test cases for training entity.
 */
@ContextConfiguration(classes = {IntegrationTestContext.class})
public class TrainingEntityITTest extends AbstractTestNGSpringContextTests {

  @Test(enabled = false)
  public void testTrainingUserDAO_addTrainingUser() throws AppException {
    TrainingUser trainingUser = new TrainingUser();
    trainingUser.setRating(1200.0);
    trainingUser.setVolatility(550.0);
    trainingUser.setUserId(1);
    trainingUser.setName("01李昀");
    trainingUser.setType(0);
    trainingUserDAO.add(trainingUser);
  }

  @Test(enabled = false)
  public void testTrainingContestDAO_addTrainingContest() throws AppException {
    TrainingContest trainingContest = new TrainingContest();
    trainingContest.setTitle("World final 2013");
    trainingContest.setIsPersonal(false);
    trainingContestDAO.add(trainingContest);
  }

  @Test(enabled = false)
  public void testTrainingStatusDAO_addTrainingStatus() throws AppException {
    TrainingStatus trainingStatus = new TrainingStatus();
    trainingStatus.setRating(1000.0);
    trainingStatus.setVolatility(500.0);
    trainingStatus.setRank(1);
    trainingStatus.setPenalty(100);
    trainingStatus.setSolve(1);
    trainingStatus.setRatingVary(-200.0);
    trainingStatus.setVolatilityVary(-50.0);
    trainingStatus.setTrainingUserId(1);
    trainingStatus.setTrainingContestId(1);

    trainingStatusDAO.add(trainingStatus);
  }

  @Autowired
  private ITrainingContestDAO trainingContestDAO;
  @Autowired
  private ITrainingUserDAO trainingUserDAO;
  @Autowired
  private ITrainingStatusDAO trainingStatusDAO;

  @Autowired
  private IUserDAO userDAO;
}
