package cn.edu.uestc.acmicpc.db;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.edu.uestc.acmicpc.config.TestContext;
import cn.edu.uestc.acmicpc.db.dto.impl.UserDTO;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Test cases for DTO entities.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestContext.class })
public class DTOTest {

  @Test
  public void testUserDTO() throws AppException {
    @SuppressWarnings("unused")
    UserDTO userDTO = UserDTO.builder()
        .setUserId(1)
        .setUserName("userName")
        .setDepartmentId(1)
        .setEmail("email@email.com")
        .setPassword("password")
        .setNickName("nickName")
        .setSchool("school")
        .setPasswordRepeat("password")
        .setStudentId("123456789")
        .setType(2)
        .build();
    /*
     * @TODO wuwu User user = userDTO.getEntity(); Assert.assertEquals(Integer.valueOf(1),
     * user.getUserId()); Assert.assertEquals("userName", user.getUserName());
     * Assert.assertEquals("nickName", user.getNickName());
     * Assert.assertEquals(StringUtil.encodeSHA1("password"), user.getPassword());
     * Assert.assertEquals(Integer.valueOf(2), user.getType());
     */
  }
}
