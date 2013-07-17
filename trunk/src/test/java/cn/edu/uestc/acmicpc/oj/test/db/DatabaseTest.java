package cn.edu.uestc.acmicpc.oj.test.db;

/*
 *
 *  * cdoj, UESTC ACMICPC Online Judge
 *  * Copyright (c) 2013 fish <@link lyhypacm@gmail.com>,
 *  * 	mzry1992 <@link muziriyun@gmail.com>
 *  *
 *  * This program is free software; you can redistribute it and/or
 *  * modify it under the terms of the GNU General Public License
 *  * as published by the Free Software Foundation; either version 2
 *  * of the License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program; if not, write to the Free Software
 *  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.impl.StatusCondition;
import cn.edu.uestc.acmicpc.db.condition.impl.UserCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.*;
import cn.edu.uestc.acmicpc.db.dto.impl.ContestDTO;
import cn.edu.uestc.acmicpc.db.entity.*;
import cn.edu.uestc.acmicpc.ioc.condition.StatusConditionAware;
import cn.edu.uestc.acmicpc.ioc.condition.UserConditionAware;
import cn.edu.uestc.acmicpc.ioc.dao.*;
import cn.edu.uestc.acmicpc.ioc.dto.ContestDTOAware;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.ObjectUtil;
import cn.edu.uestc.acmicpc.util.StringUtil;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.FieldNotUniqueException;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.*;

/**
 * Simple database test class.
 * 
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-test.xml" })
public class DatabaseTest implements TagDAOAware, UserDAOAware,
		DepartmentDAOAware, ProblemDAOAware, UserConditionAware,
		StatusConditionAware, StatusDAOAware, UserSerialKeyDAOAware,
		ContestDAOAware, ContestDTOAware {

	@Ignore
	@Before
	public void init() {
		try {
			User user = new User();
			user.setUserName("admin");
			user.setPassword(StringUtil.encodeSHA1("admin"));
			user.setNickName("admin");
			user.setEmail("admin@localhost.com");
			user.setSchool("UESTC");
			user.setDepartmentByDepartmentId(departmentDAO.get(1));
			user.setStudentId("2010013100008");
			user.setLastLogin(new Timestamp(new Date().getTime()));
			user.setSolved(0);
			user.setTried(0);
			user.setType(0);
			User check = userDAO.getEntityByUniqueField("userName",
					user.getUserName());
			if (check == null)
				userDAO.add(user);
		} catch (Exception e) {
		}
	}

	/**
	 * ITagDAO entity
	 */
	@Autowired
	private ITagDAO tagDAO;
	/**
	 * UserDAO entity
	 */
	@Autowired
	private IUserDAO userDAO;

	@Autowired
	private IStatusDAO statusDAO;

	/**
	 * DepartmentDAO entity
	 */
	@Autowired
	private IDepartmentDAO departmentDAO;

	/**
	 * ProblemDAO entity
	 */
	@Autowired
	private IProblemDAO problemDAO;

	/**
	 * User serial key entity.
	 */
	@Autowired
	private IUserSerialKeyDAO userSerialKeyDAO;

	/**
	 * Conditions for database query.
	 */
	@Autowired
	private UserCondition userCondition;

	@Autowired
	private StatusCondition statusCondition;

	public void setProblemDAO(IProblemDAO problemDAO) {
		this.problemDAO = problemDAO;
	}

	@Override
	public void setTagDAO(ITagDAO tagDAO) {
		this.tagDAO = tagDAO;
	}

	public void setUserDAO(IUserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public void setDepartmentDAO(IDepartmentDAO departmentDAO) {
		this.departmentDAO = departmentDAO;
	}

	/**
	 * Simple test by connecting with database with DAO.
	 */
	@SuppressWarnings("unchecked")
	@Test
	@Ignore
	public void testDataBaseConnection() throws AppException {
		List<Tag> tags = (List<Tag>) tagDAO.findAll();
		for (Tag tag : tags)
			System.out.println(tag.getTagId() + " " + tag.getName());
	}

	/**
	 * Test for User DAO
	 */
	@Test
	@Ignore
	public void testUserDAO() throws AppException, FieldNotUniqueException {
		for (int i = 0; i < 500; i++) {
			User user = new User();
			int id = new Random().nextInt();
			user.setUserName(String.format("TEST_%d", id));
			user.setPassword("123456");
			user.setNickName("haha");
			user.setEmail(String.format("TEST_%d@mzry1992.com", id));
			user.setSchool("UESTC");
			user.setDepartmentByDepartmentId(departmentDAO.get(1));
			user.setStudentId("2010013100008");
			user.setLastLogin(new Timestamp(new Date().getTime()));
			user.setSolved(0);
			user.setTried(0);
			user.setType(0);
			User check = userDAO.getEntityByUniqueField("userName",
					user.getUserName());
			if (check == null)
				userDAO.add(user);
		}
	}

	/**
	 * Test DAO count method.
	 * 
	 * @throws AppException
	 */
	@Test
	@Ignore
	public void testCount() throws AppException {
		System.out.println(tagDAO.count());
	}

	/**
	 * Test new method for get entity by unique field name
	 * 
	 * @throws FieldNotUniqueException
	 */
	@Test
	@Ignore
	public void testGetEntityByUnique() throws FieldNotUniqueException,
			AppException {
		User user = userDAO.getEntityByUniqueField("userName", "administrator");
		Assert.assertEquals("UESTC", user.getSchool());
	}

	/**
	 * Test new method for get entity by unique field name by a non-unique field
	 * 
	 * @throws FieldNotUniqueException
	 */
	@Test(expected = FieldNotUniqueException.class)
	@Ignore
	public void testGetEntityByUniqueWithNotUniqueField()
			throws FieldNotUniqueException, AppException {
		userDAO.getEntityByUniqueField("password", "123456");
	}

	/**
	 * Test for userDAO update method.
	 * 
	 * @throws AppException
	 * @throws FieldNotUniqueException
	 */
	@Test
	@Ignore
	public void testUserUpdate() throws AppException, FieldNotUniqueException {
		User user = userDAO.getEntityByUniqueField("userName", "administrator");
		user.setLastLogin(new Timestamp(new Date().getTime()));
		userDAO.update(user);
	}

	/**
	 * Test for user startId and endId condition.
	 * 
	 * @throws AppException
	 */
	@Test
	@Ignore
	public void testUserConditionByStartIdAndEndId() throws AppException {
		userCondition.clear();
		userCondition.setStartId(50);
		userCondition.setEndId(100);
		Long count = userDAO.count(userCondition.getCondition());
		Assert.assertEquals(51L, count.longValue());
	}

	/**
	 * Test for user departmentId condition.
	 * 
	 * @throws AppException
	 */
	@Test
	@Ignore
	public void testUserConditionDepartmentId() throws AppException {
		userCondition.clear();
		userCondition.setDepartmentId(1);
		userCondition.setUserName("admin");
		Long count = userDAO.count(userCondition.getCondition());
		System.out.println(count);
	}

	/**
	 * Test for DAO delete method.
	 */
	@Test
	@Ignore
	public void testDelete() throws AppException, FieldNotUniqueException {
		User user = userDAO.getEntityByUniqueField("userName", "admin");
		Long oldCount = userDAO.count();
		userDAO.delete(user);
		Long newCount = userDAO.count();
		Assert.assertEquals(oldCount - 1, newCount.longValue());
	}

	/**
	 * Test for add new problem
	 */
	@Test
	@Ignore
	public void testAddProblem() throws Exception {
		for (int i = 1; i <= 200; i++) {
			Problem problem = new Problem();
			Integer randomId = new Random().nextInt();
			problem.setTitle("Problem " + randomId.toString());
			problem.setDescription("Description " + randomId.toString());
			problem.setInput("Input " + randomId.toString());
			problem.setOutput("Output " + randomId.toString());
			problem.setSampleInput("Sample input " + randomId.toString());
			problem.setSampleOutput("Sample output " + randomId.toString());
			problem.setHint("Hint " + randomId.toString());
			problem.setSource("Source " + randomId.toString());
			problem.setTimeLimit(Math.abs(new Random().nextInt()));
			problem.setMemoryLimit(Math.abs(new Random().nextInt()));
			problem.setIsSpj(new Random().nextBoolean());
			problem.setIsVisible(new Random().nextBoolean());
			problem.setOutputLimit(Math.abs(new Random().nextInt()));
			problem.setJavaMemoryLimit(Math.abs(new Random().nextInt()));
			problem.setJavaTimeLimit(Math.abs(new Random().nextInt()));
			problem.setDataCount(Math.abs(new Random().nextInt()));
			problem.setDifficulty(Math.abs(new Random().nextInt()) % 5 + 1);
			problemDAO.add(problem);
		}
	}

	@Override
	public void setUserCondition(UserCondition userCondition) {
		this.userCondition = userCondition;
	}

	@Override
	public UserCondition getUserCondition() {
		return userCondition;
	}

	/**
	 * test single user uniquly.
	 * 
	 * @throws FieldNotUniqueException
	 * @throws AppException
	 */
	@Test
	@Ignore
	public void testSingleUser() throws FieldNotUniqueException, AppException {
		User user = new User();
		int id = new Random().nextInt();
		user.setUserName(String.format("TEST_%d", id));
		user.setPassword(StringUtil.encodeSHA1("123456"));
		user.setNickName("haha");
		user.setEmail(String.format("TEST_%d@mzry1992.com", id));
		user.setSchool("UESTC");
		user.setDepartmentByDepartmentId(departmentDAO.get(1));
		user.setStudentId("2010013100008");
		user.setLastLogin(new Timestamp(new Date().getTime()));
		user.setSolved(0);
		user.setTried(0);
		user.setType(0);
		User check = userDAO.getEntityByUniqueField("userName",
				user.getUserName());
		if (check == null)
			userDAO.add(user);
	}

	/**
	 * Testing for fetch status group by problems.
	 * 
	 * @throws AppException
	 * @throws FieldNotUniqueException
	 */
	@SuppressWarnings("unchecked")
	@Test
	@Ignore
	public void testStatusDAOWithDistinctProblem() throws AppException,
			FieldNotUniqueException {
		User user = userDAO.getEntityByUniqueField("userName", "lyhypacm");
		if (user == null)
			return;
		System.out.println(ObjectUtil.toString(user));
		statusCondition.clear();
		statusCondition.setUserId(user.getUserId());
		statusCondition.setResultId(Global.OnlineJudgeReturnType.OJ_AC
				.ordinal());
		Condition condition = statusCondition.getCondition();
		condition
				.addProjection(Projections.groupProperty("problemByProblemId"));
		List<Problem> results = (List<Problem>) statusDAO.findAll(condition);
		for (Problem result : results)
			System.out.println(ObjectUtil.toString(result));
	}

	/**
	 * Find userSerialKey entity by user name.
	 */
	@Test
	@Ignore
	public void testFindUserSerialKeyByUserName()
			throws FieldNotUniqueException, AppException {
		String userName = "administrator";
		User user = userDAO.getEntityByUniqueField("userName", userName);
		System.out.println(user.toString());
		UserSerialKey userSerialKey = userSerialKeyDAO.getEntityByUniqueField(
				"userId", user, "userByUserId", true);
		System.out.println(userSerialKey == null ? null : userSerialKey
				.toString());
	}

	@Override
	public void setStatusCondition(StatusCondition statusCondition) {
		this.statusCondition = statusCondition;
	}

	@Override
	public StatusCondition getStatusCondition() {
		return statusCondition;
	}

	@Override
	public void setStatusDAO(IStatusDAO statusDAO) {
		this.statusDAO = statusDAO;
	}

	@Override
	public void setUserSerialKeyDAO(IUserSerialKeyDAO userSerialKeyDAO) {
		this.userSerialKeyDAO = userSerialKeyDAO;
	}

	@Autowired
	private IContestDAO contestDAO;

	@Override
	public void setContestDAO(IContestDAO contestDAO) {
		this.contestDAO = contestDAO;
	}

	@Autowired
	private ContestDTO contestDTO;

	@Override
	public void setContestDTO(ContestDTO contestDTO) {
		this.contestDTO = contestDTO;
	}

	@Override
	public ContestDTO getContestDTO() {
		return contestDTO;
	}

	/**
	 * Test cases for contest DAO.
	 */
	@Test
	@Ignore
	public void testContestDAO() {
		try {
			Contest contest = contestDTO.getEntity();
			System.out.println(contest.toString());
			contestDAO.add(contest);
		} catch (AppException e) {
			e.printStackTrace();
		}
	}

	@Test
	@Ignore
	public void testSQLGenerator() throws AppException {
		userCondition.clear();
		userCondition.setUserName("userName");
		userCondition.setStartId(10);
		Condition condition = userCondition.getCondition();
		Junction junction = Restrictions.disjunction();
		junction.add(Restrictions.eq("userName", "userName"));
		junction.add(Restrictions.ge("userId", 1));
		condition.addCriterion(junction);
		System.out.println(userDAO.getSQLString(condition));
	}

	@Test
	public void testSQLUpdate() throws AppException {
		statusCondition.clear();
		statusCondition.setContestId(1);
		Map<String, Object> properties = new HashMap<>();
		properties.put("result", Global.OnlineJudgeReturnType.OJ_AC.ordinal());
		statusDAO.updateEntitiesByCondition(properties,
				statusCondition.getCondition());
	}
}
