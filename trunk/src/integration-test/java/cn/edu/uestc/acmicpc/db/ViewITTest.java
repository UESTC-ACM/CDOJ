package cn.edu.uestc.acmicpc.db;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.edu.uestc.acmicpc.config.IntegrationTestContext;
import cn.edu.uestc.acmicpc.db.dao.iface.IDepartmentDAO;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.db.view.impl.UserView;
import cn.edu.uestc.acmicpc.util.StringUtil;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Test cases for views.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { IntegrationTestContext.class })
public class ViewITTest {

  @Autowired
  private IDepartmentDAO departmentDAO;

  @Test
  @Ignore
  @Deprecated
  public void testUserView() throws AppException {
    User user = new User();
    user.setUserName("admin");
    user.setPassword(StringUtil.encodeSHA1("admin"));
    user.setNickName("admin");
    user.setEmail("acm@uestc.edu.cn");
    user.setSchool("UESTC");
//    user.setDepartmentByDepartmentId(departmentDAO.get(1));
    user.setStudentId("2010013100008");
    user.setLastLogin(new Timestamp(new Date().getTime()));
    user.setType(0);
    UserView userView = new UserView(user);
    Assert.assertEquals("admin", userView.getUserName());
  }
}
