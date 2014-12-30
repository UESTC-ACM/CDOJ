package cn.edu.uestc.acmicpc.db.dto;

import cn.edu.uestc.acmicpc.config.IntegrationTestContext;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDto;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

/**
 * Test cases for Dto entities.
 */
@ContextConfiguration(classes = { IntegrationTestContext.class })
public class DtoITTest extends AbstractTestNGSpringContextTests {

  @Test
  public void testUserDto() throws AppException {
    @SuppressWarnings("unused")
    UserDto userDto = UserDto.builder()
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
     * @TODO(fish): User user = userRegisterDto.getEntity();
     * Assert.assertEquals(Integer.valueOf(1), user.getUserId());
     * Assert.assertEquals("userName", user.getUserName());
     * Assert.assertEquals("nickName", user.getNickName());
     * Assert.assertEquals(StringUtil.encodeSHA1("password"),
     * user.getPassword()); Assert.assertEquals(Integer.valueOf(2),
     * user.getType());
     */
  }
}
