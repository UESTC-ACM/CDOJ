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


import cn.edu.uestc.acmicpc.db.dao.DepartmentDAO;
import cn.edu.uestc.acmicpc.db.dao.TagDAO;
import cn.edu.uestc.acmicpc.db.dao.UserDAO;
import cn.edu.uestc.acmicpc.db.entity.Tag;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.util.AppException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Simple database test class.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 * @version 3
 */
public class DatabaseTest {

    /**
     * Resources and DAOs..
     */
    private ApplicationContext applicationContext;
    private TagDAO tagDAO;
    private UserDAO userDAO;
    private DepartmentDAO departmentDAO;

    @Before
    public void initDAO() throws Exception {
        applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        tagDAO = applicationContext.getBean(TagDAO.class);
        userDAO = applicationContext.getBean(UserDAO.class);
        departmentDAO = applicationContext.getBean(DepartmentDAO.class);
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
    public void testUserDAO() throws AppException {
        User user = userDAO.getUserByName("mzry1992");
        if (user == null) {
            user = new User();
            user.setUserName("mzry1992");
            user.setPassword("123456");
            user.setNickName("haha");
            user.setEmail("muziriyun@qq.com");
            user.setSchool("UESTC");
            user.setDepartmentByDepartmentId(departmentDAO.get(1));
            user.setStudentId("2010013100008");
            user.setLastLogin(new Timestamp(new Date().getTime()));
            userDAO.add(user);
        } else {
            System.out.println(user.getUserId() + " " + user.getUserName());
        }
    }

    @Test
    public void testCount() throws AppException {
        System.out.println(tagDAO.count());
    }
}
