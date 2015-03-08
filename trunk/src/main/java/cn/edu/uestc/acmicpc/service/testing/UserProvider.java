package cn.edu.uestc.acmicpc.service.testing;

import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDto;
import cn.edu.uestc.acmicpc.service.iface.UserService;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * User provider.
 */
@Component
public class UserProvider {

  @Autowired
  private UserService userService;

  public UserDto createUnpersistedUser() {
    return createUnpersistedUser("Test" + TestUtil.getUniqueId());
  }

  public UserDto createUnpersistedUser(String userName) {
    return UserDto.builder()
        .setUserName(userName)
        .setDepartmentId(1)
        .setEmail(userName + "@uestc.acm.com")
        .setNickName(userName)
        .setPassword("password")
        .build();
  }

  public UserDto createUser() throws AppException {
    return createUser("Test" + TestUtil.getUniqueId());
  }

  public UserDto createUser(String userName) throws AppException {
    UserDto user = createUnpersistedUser(userName);
    Integer userId = userService.createNewUser(user);
    return userService.getUserDtoByUserId(userId);
  }

  public Integer[] createUsers(int count) throws AppException {
    Integer[] userIds = new Integer[count];
    for (int i = 0; i < count; i++) {
      userIds[i] = userService.createNewUser(createUnpersistedUser());
    }
    return userIds;
  }
}
