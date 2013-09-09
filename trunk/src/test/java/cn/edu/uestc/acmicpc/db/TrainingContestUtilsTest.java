/*
 * cdoj, UESTC ACMICPC Online Judge
 *
 * Copyright (c) 2013 fish <@link lyhypacm@gmail.com>,
 * mzry1992 <@link muziriyun@gmail.com>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package cn.edu.uestc.acmicpc.db;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.edu.uestc.acmicpc.db.condition.impl.TrainingContestCondition;
import cn.edu.uestc.acmicpc.db.condition.impl.TrainingStatusCondition;
import cn.edu.uestc.acmicpc.db.condition.impl.TrainingUserCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.ITrainingContestDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.ITrainingStatusDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.ITrainingUserDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IUserDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.TrainingContestDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.TrainingStatusDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.TrainingUserDTO;
import cn.edu.uestc.acmicpc.db.entity.TrainingContest;
import cn.edu.uestc.acmicpc.db.entity.TrainingStatus;
import cn.edu.uestc.acmicpc.db.entity.TrainingUser;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Test cases for {@link TrainingContest}
 *
 * TODO remove ignore tags.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-test.xml" })
public class TrainingContestUtilsTest {

  @Test
  @Ignore
  public void setTrainingUser() throws AppException {
    for (int i = 0; i < 10; i++) {
      trainingUserDTO.setName("UESTC_" + i);
      trainingUserDTO.setType(Global.TrainingUserType.PERSONAL.ordinal());
      TrainingUser trainingUser = trainingUserDTO.getEntity();
      trainingUser.setUserByUserId(userDAO.get(1));
      trainingUserDAO.add(trainingUser);
    }
  }

  @Test
  @Ignore
  public void setTrainingContet() throws AppException {
    for (int i = 0; i < 10; i++) {
      trainingContestDTO.setTitle("Contest " + i);
      trainingContestDTO.setIsPersonal(true);
      TrainingContest trainingContest = trainingContestDTO.getEntity();
      trainingContestDAO.add(trainingContest);
    }
  }

  @Test
  @Ignore
  public void setTrainingStatus() throws AppException {
    for (int i = 1; i <= 10; i++) {
      for (int j = 1; j <= 10; j++) {
        TrainingStatus trainingStatus = trainingStatusDTO.getEntity();
        trainingStatus.setTrainingUserByTrainingUserId(trainingUserDAO.get(j));
        trainingStatus.setTrainingContestByTrainingContestId(trainingContestDAO.get(i));
        trainingStatusDAO.add(trainingStatus);
      }
    }
  }

  @Test
  @Ignore
  public void testDeleteByCondition() throws AppException {
    trainingStatusCondition.clear();
    trainingStatusCondition.setTrainingContestId(5);
    trainingStatusDAO.deleteEntitiesByCondition(trainingStatusCondition.getCondition());
  }

  @SuppressWarnings("unchecked")
  @Test
  @Ignore
  public void updateUser() throws AppException {
    List<TrainingUser> trainingUserList = (List<TrainingUser>) trainingUserDAO.findAll();
    for (TrainingUser trainingUser : trainingUserList) {
      trainingUser.setMember(trainingUser.getName());
      trainingUserDAO.update(trainingUser);
    }
  }

  @Autowired
  private IUserDAO userDAO;

  @Autowired
  private TrainingStatusCondition trainingStatusCondition;

  @Autowired
  private TrainingContestCondition trainingContestCondition;

  @Autowired
  private TrainingUserCondition trainingUserCondition;

  @Autowired
  private ITrainingUserDAO trainingUserDAO;

  @Autowired
  private ITrainingStatusDAO trainingStatusDAO;

  @Autowired
  private ITrainingContestDAO trainingContestDAO;

  @Autowired
  private TrainingUserDTO trainingUserDTO;

  @Autowired
  private TrainingContestDTO trainingContestDTO;

  @Autowired
  private TrainingStatusDTO trainingStatusDTO;
}
