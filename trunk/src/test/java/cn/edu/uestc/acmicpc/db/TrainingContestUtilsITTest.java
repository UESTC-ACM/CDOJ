package cn.edu.uestc.acmicpc.db;

import cn.edu.uestc.acmicpc.config.IntegrationTestContext;
import cn.edu.uestc.acmicpc.db.condition.impl.TrainingContestCondition;
import cn.edu.uestc.acmicpc.db.condition.impl.TrainingStatusCondition;
import cn.edu.uestc.acmicpc.db.condition.impl.TrainingUserCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.TrainingContestDao;
import cn.edu.uestc.acmicpc.db.dao.iface.TrainingStatusDao;
import cn.edu.uestc.acmicpc.db.dao.iface.TrainingUserDao;
import cn.edu.uestc.acmicpc.db.dao.iface.UserDao;
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
      // trainingUser.setUserByUserId(userDao.get(1));
      // trainingUserDao.add(trainingUser);
    }
  }

  @Test(enabled = false)
  public void setTrainingContet() throws AppException {
//    for (int i = 0; i < 10; i++) {
//      TrainingContestDTO trainingContestDTO = TrainingContestDTO.builder()
//          .setTitle("Contest " + i)
//          .setIsPersonal(true).build();
//      TrainingContest trainingContest = trainingContestDTO.getEntity();
//      trainingContestDao.add(trainingContest);
//    }
  }

  @Test(enabled = false)
  public void setTrainingStatus() throws AppException {
//    for (int i = 1; i <= 10; i++) {
//      for (int j = 1; j <= 10; j++) {
//        TrainingStatusDTO trainingStatusDTO = TrainingStatusDTO.builder().build();
//        TrainingStatus trainingStatus = trainingStatusDTO.getEntity();
//        trainingStatus.setTrainingUserByTrainingUserId(trainingUserDao.get(j));
//        trainingStatus.setTrainingContestByTrainingContestId(trainingContestDao.get(i));
//        trainingStatusDao.add(trainingStatus);
//      }
//    }
  }

  @SuppressWarnings("unchecked")
  @Test(enabled = false)
  @Deprecated
  public void updateUser() throws AppException {
    List<TrainingUser> trainingUserList = (List<TrainingUser>) trainingUserDao.findAll();
    for (TrainingUser trainingUser : trainingUserList) {
      trainingUser.setMember(trainingUser.getName());
      trainingUserDao.update(trainingUser);
    }
  }

  @Autowired
  private UserDao userDao;

  private TrainingStatusCondition trainingStatusCondition;

  private TrainingContestCondition trainingContestCondition;

  private TrainingUserCondition trainingUserCondition;

  @Autowired
  private TrainingUserDao trainingUserDao;

  @Autowired
  private TrainingStatusDao trainingStatusDao;

  @Autowired
  private TrainingContestDao trainingContestDao;

}
