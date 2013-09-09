package cn.edu.uestc.acmicpc.db;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.edu.uestc.acmicpc.db.dto.impl.UserDTO;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.util.StringUtil;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Test cases for DTO entities.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-test.xml" })
public class DTOTest {

  @Test
  public void testUserDTO() throws AppException {
    UserDTO userDTO = new UserDTO();
    userDTO.setUserId(1);
    userDTO.setUserName("userName");
    userDTO.setDepartmentId(1);
    userDTO.setEmail("email@email.com");
    userDTO.setPassword("password");
    userDTO.setNickName("nickName");
    userDTO.setSchool("school");
    userDTO.setPasswordRepeat("password");
    userDTO.setStudentId("123456789");
    userDTO.setType(2);
    User user = userDTO.getEntity();
    Assert.assertEquals(Integer.valueOf(1), user.getUserId());
    Assert.assertEquals("userName", user.getUserName());
    Assert.assertEquals("nickName", user.getNickName());
    Assert.assertEquals(StringUtil.encodeSHA1("password"), user.getPassword());
    Assert.assertEquals(Integer.valueOf(2), user.getType());
  }
}
