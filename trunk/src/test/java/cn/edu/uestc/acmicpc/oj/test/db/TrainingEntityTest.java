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

import cn.edu.uestc.acmicpc.db.dao.iface.ITrainingContestDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.ITrainingStatusDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.ITrainingUserDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IUserDAO;
import cn.edu.uestc.acmicpc.db.entity.TrainingContest;
import cn.edu.uestc.acmicpc.db.entity.TrainingStatus;
import cn.edu.uestc.acmicpc.db.entity.TrainingUser;
import cn.edu.uestc.acmicpc.ioc.dao.TrainingContestDAOAware;
import cn.edu.uestc.acmicpc.ioc.dao.TrainingStatusDAOAware;
import cn.edu.uestc.acmicpc.ioc.dao.TrainingUserDAOAware;
import cn.edu.uestc.acmicpc.ioc.dao.UserDAOAware;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Description
 * 
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-test.xml" })
public class TrainingEntityTest implements TrainingContestDAOAware,
		TrainingStatusDAOAware, TrainingUserDAOAware, UserDAOAware {

	@Test
	@Ignore
	public void testTrainingUser() throws AppException {
		System.out.println("Hello");
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
	public void testTrainingContest() throws AppException {
		TrainingContest trainingContest = new TrainingContest();
		trainingContest.setTitle("World final 2013");
		trainingContest.setIsPersonal(false);
		trainingContestDAO.add(trainingContest);
	}

	@Test
	@Ignore
	public void testTrainingStatus() throws AppException {
		TrainingStatus trainingStatus = new TrainingStatus();
		trainingStatus.setRating(1000.0);
		trainingStatus.setVolatility(500.0);
		trainingStatus.setRank(1);
		trainingStatus.setPenalty(100);
		trainingStatus.setSolve(1);
		trainingStatus.setRatingVary(-200.0);
		trainingStatus.setVolatilityVary(-50.0);
		trainingStatus.setTrainingUserByTrainingUserId(trainingUserDAO.get(1));
		trainingStatus.setTrainingContestByTrainingContestId(trainingContestDAO
				.get(1));

		trainingStatusDAO.add(trainingStatus);
	}

	@Autowired
	private ITrainingContestDAO trainingContestDAO;
	@Autowired
	private ITrainingUserDAO trainingUserDAO;
	@Autowired
	private ITrainingStatusDAO trainingStatusDAO;

	@Override
	public void setTrainingContestDAO(ITrainingContestDAO trainingContestDAO) {
		this.trainingContestDAO = trainingContestDAO;
	}

	@Override
	public void setTrainingStatusDAO(ITrainingStatusDAO trainingStatusDAO) {
		this.trainingStatusDAO = trainingStatusDAO;
	}

	@Override
	public void setTrainingUserDAO(ITrainingUserDAO trainingUserDAO) {
		this.trainingUserDAO = trainingUserDAO;
	}

	@Autowired
	private IUserDAO userDAO;

	@Override
	public void setUserDAO(IUserDAO userDAO) {
		this.userDAO = userDAO;
	}
}
