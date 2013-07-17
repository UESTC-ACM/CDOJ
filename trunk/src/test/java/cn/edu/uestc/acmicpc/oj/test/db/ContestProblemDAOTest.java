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

import cn.edu.uestc.acmicpc.db.dao.iface.IContestDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IContestProblemDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IProblemDAO;
import cn.edu.uestc.acmicpc.db.entity.ContestProblem;
import cn.edu.uestc.acmicpc.ioc.dao.ContestDAOAware;
import cn.edu.uestc.acmicpc.ioc.dao.ContestProblemDAOAware;
import cn.edu.uestc.acmicpc.ioc.dao.ProblemDAOAware;
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
public class ContestProblemDAOTest implements ProblemDAOAware, ContestDAOAware,
		ContestProblemDAOAware {

	@Autowired
	private IProblemDAO problemDAO;

	@Autowired
	private IContestDAO contestDAO;

	@Autowired
	private IContestProblemDAO contestProblemDAO;

	@Override
	public void setContestDAO(IContestDAO contestDAO) {
		this.contestDAO = contestDAO;
	}

	@Override
	public void setContestProblemDAO(IContestProblemDAO contestProblemDAO) {
		this.contestProblemDAO = contestProblemDAO;
	}

	@Override
	public void setProblemDAO(IProblemDAO problemDAO) {
		this.problemDAO = problemDAO;
	}

	@Test
	@Ignore
	public void testAddContestProblem() throws AppException {
		ContestProblem contestProblem = new ContestProblem();
		contestProblem.setContestByContestId(contestDAO.get(1));
		contestProblem.setProblemByProblemId(problemDAO.get(1));
		contestProblem.setOrder(0);
		contestProblemDAO.add(contestProblem);
	}
}
