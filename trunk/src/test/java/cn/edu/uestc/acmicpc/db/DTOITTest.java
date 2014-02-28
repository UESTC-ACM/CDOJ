package cn.edu.uestc.acmicpc.db;

import cn.edu.uestc.acmicpc.config.IntegrationTestContext;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDTO;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

/**
 * Test cases for DTO entities.
 */
@ContextConfiguration(classes = {IntegrationTestContext.class})
public class DTOITTest extends AbstractTestNGSpringContextTests {

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
        .setStudentId("123456789")
        .setType(2)
        .build();
    /*
     * @TODO(fish): User user = userRegisterDTO.getEntity(); Assert.assertEquals(Integer.valueOf(1),
     * user.getUserId()); Assert.assertEquals("userName", user.getUserName());
     * Assert.assertEquals("nickName", user.getNickName());
     * Assert.assertEquals(StringUtil.encodeSHA1("password"), user.getPassword());
     * Assert.assertEquals(Integer.valueOf(2), user.getType());
     */
  }
}
