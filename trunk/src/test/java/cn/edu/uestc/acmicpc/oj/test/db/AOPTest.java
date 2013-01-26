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

import cn.edu.uestc.acmicpc.oj.db.dao.iface.IUserDAO;
import cn.edu.uestc.acmicpc.oj.db.entity.User;
import cn.edu.uestc.acmicpc.oj.util.exception.AppException;
import cn.edu.uestc.acmicpc.oj.util.exception.FieldNotUniqueException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test cases for AOP framework
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 * @version 1
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:applicationContext-test.xml"})
public class AOPTest {
    @Autowired
    IUserDAO userDAO = null;

    public void setUserDAO(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Test
    public void testDataBaseConnection() throws FieldNotUniqueException, AppException {
        User user = userDAO.getEntityByUniqueField("userName", "UESTC_Izayoi");
        System.out.println(user.getUserName());
    }
}
