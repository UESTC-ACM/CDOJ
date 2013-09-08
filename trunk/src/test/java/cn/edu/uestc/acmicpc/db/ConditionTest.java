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

package cn.edu.uestc.acmicpc.db;

import java.util.List;

import org.hibernate.criterion.Projections;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.impl.ProblemCondition;
import cn.edu.uestc.acmicpc.db.condition.impl.StatusCondition;
import cn.edu.uestc.acmicpc.db.condition.impl.UserCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IProblemDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IStatusDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IUserDAO;
import cn.edu.uestc.acmicpc.ioc.condition.ProblemConditionAware;
import cn.edu.uestc.acmicpc.ioc.condition.StatusConditionAware;
import cn.edu.uestc.acmicpc.ioc.condition.UserConditionAware;
import cn.edu.uestc.acmicpc.ioc.dao.ProblemDAOAware;
import cn.edu.uestc.acmicpc.ioc.dao.StatusDAOAware;
import cn.edu.uestc.acmicpc.ioc.dao.UserDAOAware;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.FieldNotUniqueException;

/**
 * Test cases for conditions entities.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-test.xml" })
public class ConditionTest implements ProblemDAOAware, UserDAOAware, UserConditionAware,
    ProblemConditionAware, StatusConditionAware, StatusDAOAware {

  @Before
  public void init() {
    problemCondition.clear();
    statusCondition.clear();
    userCondition.clear();
  }

  /**
   * DAOs for database query.
   */
  @Autowired
  private IProblemDAO problemDAO;

  @Autowired
  private IUserDAO userDAO;

  /**
   * Conditions for database query.
   */
  @Autowired
  private UserCondition userCondition;

  @Autowired
  private ProblemCondition problemCondition;

  @Autowired
  private StatusCondition statusCondition;

  @Override
  public void setProblemDAO(IProblemDAO problemDAO) {
    this.problemDAO = problemDAO;
  }

  @Override
  public void setUserDAO(IUserDAO userDAO) {
    this.userDAO = userDAO;
  }

  @Override
  public void setUserCondition(UserCondition userCondition) {
    this.userCondition = userCondition;
  }

  @Override
  public UserCondition getUserCondition() {
    return userCondition;
  }

  @Override
  public void setProblemCondition(ProblemCondition problemCondition) {
    this.problemCondition = problemCondition;
  }

  @Override
  public ProblemCondition getProblemCondition() {
    return problemCondition;
  }

  @Test
  public void testConditionClear() throws AppException {
    problemCondition.setStartId(2);
    problemCondition.setTitle("a+b problem");
    Assert.assertEquals(Long.valueOf(3), problemDAO.count(problemCondition.getCondition()));
    problemCondition.clear();
    Assert.assertEquals(Long.valueOf(5), problemDAO.count(problemCondition.getCondition()));
  }

  @Override
  public void setStatusCondition(StatusCondition statusCondition) {
    this.statusCondition = statusCondition;
  }

  @Override
  public StatusCondition getStatusCondition() {
    return statusCondition;
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testProjections() throws AppException, FieldNotUniqueException {
    statusCondition.setUserId(1);
    statusCondition.setResultId(Global.OnlineJudgeReturnType.OJ_AC.ordinal());
    Condition condition = statusCondition.getCondition();
    condition.addProjection(Projections.groupProperty("problemByProblemId.problemId"));
    List<Integer> results = (List<Integer>) statusDAO.findAll(condition);
    Assert.assertEquals(1, results.size());
    Assert.assertEquals(Integer.valueOf(1), results.get(0));
  }

  @Autowired
  private IStatusDAO statusDAO;

  @Override
  public void setStatusDAO(IStatusDAO statusDAO) {
    this.statusDAO = statusDAO;
  }
}
