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

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.impl.StatusCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IProblemDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IStatusDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IUserDAO;
import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.ioc.condition.StatusConditionAware;
import cn.edu.uestc.acmicpc.ioc.dao.ProblemDAOAware;
import cn.edu.uestc.acmicpc.ioc.dao.StatusDAOAware;
import cn.edu.uestc.acmicpc.ioc.dao.UserDAOAware;
import cn.edu.uestc.acmicpc.util.exception.AppException;
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
@ContextConfiguration({ "classpath:applicationContext-test.xml" })
public class TriedTest implements StatusDAOAware, ProblemDAOAware, UserDAOAware,
    StatusConditionAware {

  @SuppressWarnings("unchecked")
  @Test
  public void syncUserTried() throws AppException {
    List<User> userList = (List<User>) userDAO.findAll();
    for (User user : userList) {
      statusCondition.clear();
      statusCondition.setUserId(user.getUserId());
      Condition condition = statusCondition.getCondition();
      Long count = statusDAO.count(condition);
      user.setTried(count.intValue());
      userDAO.update(user);
    }
  }

  @SuppressWarnings("unchecked")
  @Test
  public void syncProblemTried() throws AppException {
    List<Problem> problemList = (List<Problem>) problemDAO.findAll();
    for (Problem problem : problemList) {
      statusCondition.clear();
      statusCondition.setProblemId(problem.getProblemId());
      Condition condition = statusCondition.getCondition();
      Long count = statusDAO.count(condition);
      problem.setTried(count.intValue());
      problemDAO.update(problem);
    }
  }

  @Autowired
  private IStatusDAO statusDAO;
  @Autowired
  private IProblemDAO problemDAO;
  @Autowired
  private IUserDAO userDAO;
  @Autowired
  private StatusCondition statusCondition;

  @Override
  public void setProblemDAO(IProblemDAO problemDAO) {
    this.problemDAO = problemDAO;
  }

  @Override
  public void setStatusDAO(IStatusDAO statusDAO) {
    this.statusDAO = statusDAO;
  }

  @Override
  public void setUserDAO(IUserDAO userDAO) {
    this.userDAO = userDAO;
  }

  @Override
  public void setStatusCondition(StatusCondition statusCondition) {
    this.statusCondition = statusCondition;
  }

  @Override
  public StatusCondition getStatusCondition() {
    return statusCondition;
  }
}
