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

package cn.edu.uestc.acmicpc.oj.test.db;

import cn.edu.uestc.acmicpc.db.condition.impl.ProblemCondition;
import cn.edu.uestc.acmicpc.db.condition.impl.UserCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IProblemDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IUserDAO;
import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.ioc.condition.ProblemConditionAware;
import cn.edu.uestc.acmicpc.ioc.condition.UserConditionAware;
import cn.edu.uestc.acmicpc.ioc.dao.ProblemDAOAware;
import cn.edu.uestc.acmicpc.ioc.dao.UserDAOAware;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Test cases for conditions entities.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext-test.xml"})
public class ConditionTest
        implements ProblemDAOAware, UserDAOAware,
        UserConditionAware, ProblemConditionAware {
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

    @Test
    public void testProblemCondition() throws AppException {
        problemCondition.clear();
        problemCondition.setStartId(1);
        problemCondition.setEndId(100);
        problemCondition.setIsSpj(null);
        System.out.println(problemDAO.count(problemCondition.getCondition()));
        problemCondition.setIsSpj(false);
        System.out.println(problemDAO.count(problemCondition.getCondition()));
        Assert.assertTrue(problemDAO.count(problemCondition.getCondition()) > 0);
        problemCondition.clear();
        problemCondition.setStartId(2);
        problemCondition.setEndId(1);
        Assert.assertEquals(new Long(0), problemDAO.count(problemCondition.getCondition()));
    }

    @SuppressWarnings("unchecked")
    @Test
    @Ignore
    public void testUserCondition() throws AppException {
        userCondition.clear();
        userCondition.setDepartmentId(2);
        System.out.println("test: " + userCondition.getDepartmentId());
        List<User> users = (List<User>) userDAO.findAll(userCondition.getCondition());
        Assert.assertEquals(1, users.size());
//        for (User user : users) {
//            System.out.println(user.getUserId() + " " + user.getUserName() + " "
//                    + user.getDepartmentByDepartmentId().getName());
//        }
    }

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

    /**
     * test for problem condition's {@code isTitleEmpty} property.
     */
    @SuppressWarnings("unchecked")
    @Test
    @Ignore
    public void testProblemConditionIsTitleEmpty() throws AppException {
        problemCondition.setIsTitleEmpty(true);
        List<Problem> problems = (List<Problem>) problemDAO.findAll(problemCondition.getCondition());
        for (Problem problem : problems)
            System.out.println(problem.toString());
    }
}
