package cn.edu.uestc.acmicpc.db;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.edu.uestc.acmicpc.config.TestContext;
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
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestContext.class })
public class TrainingEntityITTest {

  @Test
  @Ignore
  public void testTrainingUserDAO_addTrainingUser() throws AppException {
    TrainingUser trainingUser = new TrainingUser();
    trainingUser.setRating(1200.0);
    trainingUser.setVolatility(550.0);
    trainingUser.setUserByUserId(userDAO.get(1));
    trainingUser.setName("01李昀");
    trainingUser.setType(0);
    trainingUserDAO.add(trainingUser);
  }

  @Test
  @Ignore
  public void testTrainingContestDAO_addTrainingContest() throws AppException {
    TrainingContest trainingContest = new TrainingContest();
    trainingContest.setTitle("World final 2013");
    trainingContest.setIsPersonal(false);
    trainingContestDAO.add(trainingContest);
  }

  @Test
  @Ignore
  public void testTrainingStatusDAO_addTrainingStatus() throws AppException {
    TrainingStatus trainingStatus = new TrainingStatus();
    trainingStatus.setRating(1000.0);
    trainingStatus.setVolatility(500.0);
    trainingStatus.setRank(1);
    trainingStatus.setPenalty(100);
    trainingStatus.setSolve(1);
    trainingStatus.setRatingVary(-200.0);
    trainingStatus.setVolatilityVary(-50.0);
    trainingStatus.setTrainingUserByTrainingUserId(trainingUserDAO.get(1));
    trainingStatus.setTrainingContestByTrainingContestId(trainingContestDAO.get(1));

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
