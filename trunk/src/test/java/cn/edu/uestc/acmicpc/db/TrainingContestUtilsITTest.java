package cn.edu.uestc.acmicpc.db;

import cn.edu.uestc.acmicpc.config.IntegrationTestContext;
import cn.edu.uestc.acmicpc.db.condition.impl.TrainingContestCondition;
import cn.edu.uestc.acmicpc.db.condition.impl.TrainingStatusCondition;
import cn.edu.uestc.acmicpc.db.condition.impl.TrainingUserCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.ITrainingContestDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.ITrainingStatusDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.ITrainingUserDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IUserDAO;
import cn.edu.uestc.acmicpc.db.entity.TrainingContest;
import cn.edu.uestc.acmicpc.db.entity.TrainingUser;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Test cases for {@link TrainingContest}
 * <p/>
 * TODO(fish): remove ignore tags.
 */

@ContextConfiguration(classes = {IntegrationTestContext.class})
public class TrainingContestUtilsITTest extends AbstractTestNGSpringContextTests {

  @Test(enabled = false)
  public void setTrainingUser() throws AppException {
    for (int i = 0; i < 10; i++) {
      // TrainingUserDTO trainingUserDTO = TrainingUserDTO.builder()
      // .setName("UESTC_" + i).build();
      // TrainingUser trainingUser = strainingUserDTO.getEntity();
      // trainingUser.setUserByUserId(userDAO.get(1));
      // trainingUserDAO.add(trainingUser);
    }
  }

  @Test(enabled = false)
  public void setTrainingContet() throws AppException {
//    for (int i = 0; i < 10; i++) {
//      TrainingContestDTO trainingContestDTO = TrainingContestDTO.builder()
//          .setTitle("Contest " + i)
//          .setIsPersonal(true).build();
//      TrainingContest trainingContest = trainingContestDTO.getEntity();
//      trainingContestDAO.add(trainingContest);
//    }
  }

  @Test(enabled = false)
  public void setTrainingStatus() throws AppException {
//    for (int i = 1; i <= 10; i++) {
//      for (int j = 1; j <= 10; j++) {
//        TrainingStatusDTO trainingStatusDTO = TrainingStatusDTO.builder().build();
//        TrainingStatus trainingStatus = trainingStatusDTO.getEntity();
//        trainingStatus.setTrainingUserByTrainingUserId(trainingUserDAO.get(j));
//        trainingStatus.setTrainingContestByTrainingContestId(trainingContestDAO.get(i));
//        trainingStatusDAO.add(trainingStatus);
//      }
//    }
  }

  @SuppressWarnings("unchecked")
  @Test(enabled = false)
  @Deprecated
  public void updateUser() throws AppException {
    List<TrainingUser> trainingUserList = (List<TrainingUser>) trainingUserDAO.findAll();
    for (TrainingUser trainingUser : trainingUserList) {
      trainingUser.setMember(trainingUser.getName());
      trainingUserDAO.update(trainingUser);
    }
  }

  @Autowired
  private IUserDAO userDAO;

  private TrainingStatusCondition trainingStatusCondition;

  private TrainingContestCondition trainingContestCondition;

  private TrainingUserCondition trainingUserCondition;

  @Autowired
  private ITrainingUserDAO trainingUserDAO;

  @Autowired
  private ITrainingStatusDAO trainingStatusDAO;

  @Autowired
  private ITrainingContestDAO trainingContestDAO;

}
