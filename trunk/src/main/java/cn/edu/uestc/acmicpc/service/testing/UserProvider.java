package cn.edu.uestc.acmicpc.service.testing;

import cn.edu.uestc.acmicpc.db.dto.impl.UserDto;
import cn.edu.uestc.acmicpc.service.iface.UserService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.helper.StringUtil;
import com.google.common.collect.Lists;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * User provider.
 */
@Component
public class UserProvider {

  @Autowired
  private UserService userService;

  public static UserDto createUnpersistedUser() {
    return createUnpersistedUser("Test" + TestUtil.getUniqueId());
  }

  public static UserDto createUnpersistedUser(String userName) {
    return UserDto.builder()
        .setUserName(userName)
        .setDepartmentId(1)
        .setEmail(userName + "@uestc.acm.com")
        .setNickName(userName)
        .setPassword(StringUtil.encodeSHA1("password"))
        .setPasswordRepeat(StringUtil.encodeSHA1("password"))
        .setOldPassword(StringUtil.encodeSHA1("password"))
        .setNewPassword(StringUtil.encodeSHA1("password"))
        .setNewPasswordRepeat(StringUtil.encodeSHA1("password"))
        .setSchool("UESTC")
        .setGrade(1)
        .setStudentId("1234567")
        .setName("name")
        .setSex(0)
        .setPhone("123456789")
        .setSize(1)
        .build();
  }

  public UserDto createUser() throws AppException {
    return createUser("Test" + TestUtil.getUniqueId());
  }

  public UserDto createUser(String userName) throws AppException {
    UserDto user = createUnpersistedUser(userName);
    return createUser(user);

  }

  public UserDto createUser(UserDto user) throws AppException {
    Integer userId = userService.createNewUser(user);
    return userService.getUserDtoByUserId(userId);
  }

  public List<Integer> createUsers(int count) throws AppException {
    List<Integer> userIds = Lists.newLinkedList();
    for (int i = 0; i < count; i++) {
      userIds.add(userService.createNewUser(createUnpersistedUser()));
    }
    return userIds;
  }
}
