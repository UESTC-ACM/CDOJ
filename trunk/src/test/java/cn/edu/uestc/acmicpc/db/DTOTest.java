package cn.edu.uestc.acmicpc.db;

import cn.edu.uestc.acmicpc.db.dto.impl.UserDTO;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test cases for DTO entities.
 * 
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-test.xml" })
public class DTOTest {

  @Test
  @Ignore
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
    System.out.println("Id: " + user.getUserId());
    System.out.println("name: " + user.getUserName());
    System.out.println("nickName: " + user.getNickName());
    System.out.println("password: " + user.getPassword());
    System.out.println("type: " + user.getType());
  }
}
