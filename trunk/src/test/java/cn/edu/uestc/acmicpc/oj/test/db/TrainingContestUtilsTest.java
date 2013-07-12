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

package cn.edu.uestc.acmicpc.oj.test.db;

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
import cn.edu.uestc.acmicpc.ioc.condition.TrainingContestConditionAware;
import cn.edu.uestc.acmicpc.ioc.condition.TrainingStatusConditionAware;
import cn.edu.uestc.acmicpc.ioc.condition.TrainingUserConditionAware;
import cn.edu.uestc.acmicpc.ioc.dao.TrainingContestDAOAware;
import cn.edu.uestc.acmicpc.ioc.dao.TrainingStatusDAOAware;
import cn.edu.uestc.acmicpc.ioc.dao.TrainingUserDAOAware;
import cn.edu.uestc.acmicpc.ioc.dao.UserDAOAware;
import cn.edu.uestc.acmicpc.ioc.dto.TrainingContestDTOAware;
import cn.edu.uestc.acmicpc.ioc.dto.TrainingStatusDTOAware;
import cn.edu.uestc.acmicpc.ioc.dto.TrainingUserDTOAware;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Description
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext-test.xml"})
public class TrainingContestUtilsTest
        implements TrainingStatusConditionAware, TrainingContestConditionAware, TrainingUserConditionAware,
        TrainingUserDAOAware, TrainingStatusDAOAware, TrainingContestDAOAware,
        TrainingUserDTOAware, TrainingContestDTOAware, TrainingStatusDTOAware,
        UserDAOAware {

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

    @Test
    @Ignore
    public void updateUser() throws AppException {
        List<TrainingUser> trainingUserList = (List<TrainingUser>)trainingUserDAO.findAll();
        for (TrainingUser trainingUser: trainingUserList) {
            trainingUser.setMember(trainingUser.getName());
            trainingUserDAO.update(trainingUser);
        }
    }

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

    @Override
    public void setTrainingContestCondition(TrainingContestCondition trainingContestCondition) {
        this.trainingContestCondition = trainingContestCondition;
    }

    @Override
    public TrainingContestCondition getTrainingContestCondition() {
        return trainingContestCondition;
    }

    @Override
    public void setTrainingContestDAO(ITrainingContestDAO trainingContestDAO) {
        this.trainingContestDAO = trainingContestDAO;
    }

    @Override
    public void setTrainingContestDTO(TrainingContestDTO trainingContestDTO) {
        this.trainingContestDTO = trainingContestDTO;
    }

    @Override
    public TrainingContestDTO getTrainingContestDTO() {
        return trainingContestDTO;
    }

    @Override
    public void setTrainingStatusCondition(TrainingStatusCondition trainingStatusCondition) {
        this.trainingStatusCondition = trainingStatusCondition;
    }

    @Override
    public TrainingStatusCondition getTrainingStatusCondition() {
        return trainingStatusCondition;
    }

    @Override
    public void setTrainingStatusDAO(ITrainingStatusDAO trainingStatusDAO) {
        this.trainingStatusDAO = trainingStatusDAO;
    }

    @Override
    public void setTrainingUserCondition(TrainingUserCondition trainingUserCondition) {
        this.trainingUserCondition = trainingUserCondition;
    }

    @Override
    public TrainingUserCondition getTrainingUserCondition() {
        return trainingUserCondition;
    }

    @Override
    public void setTrainingUserDAO(ITrainingUserDAO trainingUserDAO) {
        this.trainingUserDAO = trainingUserDAO;
    }

    @Override
    public void setTrainingUserDTO(TrainingUserDTO trainingUserDTO) {
        this.trainingUserDTO = trainingUserDTO;
    }

    @Override
    public TrainingUserDTO getTrainingUserDTO() {
        return trainingUserDTO;
    }

    @Autowired
    private TrainingStatusDTO trainingStatusDTO;
    @Override
    public void setTrainingStatusDTO(TrainingStatusDTO trainingStatusDTO) {
        this.trainingStatusDTO = trainingStatusDTO;
    }

    @Override
    public TrainingStatusDTO getTrainingStatusDTO() {
        return trainingStatusDTO;
    }

    @Autowired
    private IUserDAO userDAO;
    @Override
    public void setUserDAO(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
