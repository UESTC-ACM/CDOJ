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


import cn.edu.uestc.acmicpc.oj.db.condition.impl.UserCondition;
import cn.edu.uestc.acmicpc.oj.db.dao.iface.IDepartmentDAO;
import cn.edu.uestc.acmicpc.oj.db.dao.iface.ITagDAO;
import cn.edu.uestc.acmicpc.oj.db.dao.iface.IUserDAO;
import cn.edu.uestc.acmicpc.oj.db.dao.impl.DepartmentDAO;
import cn.edu.uestc.acmicpc.oj.db.dao.impl.UserDAO;
import cn.edu.uestc.acmicpc.oj.db.entity.Tag;
import cn.edu.uestc.acmicpc.oj.db.entity.User;
import cn.edu.uestc.acmicpc.oj.ioc.TagDAOAware;
import cn.edu.uestc.acmicpc.util.StringUtil;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.FieldNotUniqueException;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Simple database test class.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 * @version 7
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:applicationContext-test.xml"})
public class DatabaseTest implements TagDAOAware {

    @Before
    public void init() {
        try {
            User user = new User();
            user.setUserName("admin");
            user.setPassword(StringUtil.encodeSHA1("admin"));
            user.setNickName("admin");
            user.setEmail("acm@uestc.edu.cn");
            user.setSchool("UESTC");
            user.setDepartmentByDepartmentId(departmentDAO.get(1));
            user.setStudentId("2010013100008");
            user.setLastLogin(new Timestamp(new Date().getTime()));
            User check = userDAO.getEntityByUniqueField("userName", user.getUserName());
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

    /**
     * DepartmentDAO entity
     */
    @Autowired
    private IDepartmentDAO departmentDAO;

    @Override
    public void setTagDAO(ITagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void setDepartmentDAO(DepartmentDAO departmentDAO) {
        this.departmentDAO = departmentDAO;
    }

    /**
     * Simple test by connecting with database with DAO.
     */
    @Test
    public void testDataBaseConnection() throws AppException {
        List<Tag> tags = tagDAO.findAll();
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
            User check = userDAO.getEntityByUniqueField("userName", user.getUserName());
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
    public void testCount() throws AppException {
        System.out.println(tagDAO.count());
    }

    /**
     * Test new method for get entity by unique field name
     *
     * @throws FieldNotUniqueException
     */
    @Test
    public void testGetEntityByUnique() throws FieldNotUniqueException, AppException {
        User user = userDAO.getEntityByUniqueField("userName", "admin");
        Assert.assertEquals("UESTC", user.getSchool());
    }

    /**
     * Test new method for get entity by unique field name by a non-unique field
     *
     * @throws FieldNotUniqueException
     */
    @Test(expected = FieldNotUniqueException.class)
    public void testGetEntityByUniqueWithNotUniqueField() throws FieldNotUniqueException, AppException {
        userDAO.getEntityByUniqueField("password", "123456");
    }

    /**
     * Test for userDAO update method.
     *
     * @throws AppException
     * @throws FieldNotUniqueException
     */
    @Test
    public void testUserUpdate() throws AppException, FieldNotUniqueException {
        User user = userDAO.getEntityByUniqueField("userName", "admin");
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
        UserCondition userCondition = new UserCondition();
        userCondition.startId = 50;
        userCondition.endId = 100;
        Long count = userDAO.count(userCondition.getCondition());
        Assert.assertEquals(51L, count.longValue());
    }

    /**
     * Test for user departmentId condition.
     *
     * @throws AppException
     */
    @Test
    public void testUserConditionDepartmentId() throws AppException {
        UserCondition userCondition = new UserCondition();
        userCondition.departmentId = 1;
        userCondition.userName = "admin";
        Long count = userDAO.count(userCondition.getCondition());
        Assert.assertEquals(1L, count.longValue());
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
}
