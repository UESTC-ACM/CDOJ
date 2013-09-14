package cn.edu.uestc.acmicpc.db;

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

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.impl.StatusCondition;
import cn.edu.uestc.acmicpc.db.condition.impl.UserCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IContestDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IDepartmentDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IProblemDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IStatusDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IUserDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.ContestDTO;
import cn.edu.uestc.acmicpc.db.entity.Contest;
import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.FieldNotUniqueException;

/**
 * Simple database test class.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-test.xml" })
public class DatabaseTest {

  private static final Logger LOGGER = LogManager.getLogger(DatabaseTest.class);

  /**
   * UserDAO entity
   */
  @Autowired
  private IUserDAO userDAO;

  @Autowired
  private IStatusDAO statusDAO;

  @Autowired
  private IDepartmentDAO departmentDAO;

  @Autowired
  private IProblemDAO problemDAO;

  @Autowired
  private UserCondition userCondition;

  @Autowired
  private StatusCondition statusCondition;

  @Test
  public void testDAO_getEntityByUnique() throws FieldNotUniqueException, AppException {
    User user = userDAO.getEntityByUniqueField("userName", "administrator");
    Assert.assertEquals("UESTC", user.getSchool());
  }

  @Test(expected = FieldNotUniqueException.class)
  public void testDAO_getEntityByUnique_withNotUniqueField() throws FieldNotUniqueException,
      AppException {
    userDAO.getEntityByUniqueField("password", "123456");
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

  @Autowired
  private IContestDAO contestDAO;

  @Autowired
  private ContestDTO contestDTO;

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
      LOGGER.error(e);
    }
  }

  @Test
  @Ignore(value = "not stable")
  public void testSQLGenerator() throws AppException {
    userCondition.clear();
    userCondition.setUserName("userName");
    userCondition.setStartId(10);
    Condition condition = userCondition.getCondition();
    Junction junction = Restrictions.disjunction();
    junction.add(Restrictions.eq("userName", "userName"));
    junction.add(Restrictions.ge("userId", 1));
    condition.addCriterion(junction);
    Assert.assertEquals(
        "where userName like %userName% and userId>=10 and (userName=userName or userId>=1)",
        userDAO.getSQLString(condition));
  }

  @Test
  public void testSQLUpdate() throws AppException {
    statusCondition.clear();
    statusCondition.setContestId(1);
    Map<String, Object> properties = new HashMap<>();
    properties.put("result", Global.OnlineJudgeReturnType.OJ_AC.ordinal());
    statusDAO.updateEntitiesByCondition(properties, statusCondition.getCondition());
  }
}
