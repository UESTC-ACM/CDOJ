package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.impl.UserCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.UserDao;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserCenterDto;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDto;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserEditorDto;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserListDto;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserTypeAheadDto;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.service.iface.UserService;
import cn.edu.uestc.acmicpc.util.enums.AuthenticationType;
import cn.edu.uestc.acmicpc.util.enums.GenderType;
import cn.edu.uestc.acmicpc.util.enums.GradeType;
import cn.edu.uestc.acmicpc.util.enums.TShirtsSizeType;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Implementation for {@link UserService}.
 */
@SuppressWarnings("deprecation")
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends AbstractService implements UserService {

  private final UserDao userDao;

  @Autowired
  public UserServiceImpl(UserDao userDao) {
    this.userDao = userDao;
  }

  private void updateUserByUserDto(User user, UserDto userDto) {
    if (userDto.getUserName() != null) {
      user.setUserName(userDto.getUserName());
    }
    if (userDto.getStudentId() != null) {
      user.setStudentId(userDto.getStudentId());
    }
    if (userDto.getPassword() != null) {
      user.setPassword(userDto.getPassword());
    }
    if (userDto.getSchool() != null) {
      user.setSchool(userDto.getSchool());
    }
    if (userDto.getNickName() != null) {
      user.setNickName(userDto.getNickName());
    }
    if (userDto.getEmail() != null) {
      user.setEmail(userDto.getEmail());
    }
    if (userDto.getSolved() != null) {
      user.setSolved(userDto.getSolved());
    }
    if (userDto.getTried() != null) {
      user.setTried(userDto.getTried());
    }
    if (userDto.getType() != null) {
      user.setType(userDto.getType());
    }
    if (userDto.getDepartmentId() != null) {
      user.setDepartmentId(userDto.getDepartmentId());
    }
    if (userDto.getLastLogin() != null) {
      user.setLastLogin(userDto.getLastLogin());
    }
    if (userDto.getMotto() != null) {
      user.setMotto(userDto.getMotto());
    }
    if (userDto.getName() != null) {
      user.setName(userDto.getName());
    }
    if (userDto.getSex() != null) {
      user.setSex(userDto.getSex());
    }
    if (userDto.getGrade() != null) {
      user.setGrade(userDto.getGrade());
    }
    if (userDto.getPhone() != null) {
      user.setPhone(userDto.getPhone());
    }
    if (userDto.getSize() != null) {
      user.setSize(userDto.getSize());
    }
  }

  @Override
  public void updateUser(UserDto userDto) throws AppException {
    User user = userDao.get(userDto.getUserId());
    AppExceptionUtil.assertNotNull(user);
    AppExceptionUtil.assertNotNull(user.getUserId());
    updateUserByUserDto(user, userDto);
    userDao.addOrUpdate(user);
  }

  @Override
  public Integer createNewUser(UserDto userDto) throws AppException {
    User user = new User();
    updateUserByUserDto(user, userDto);
    userDao.addOrUpdate(user);
    return user.getUserId();
  }

  @Override
  public List<UserListDto> getUserListDtoList(UserCondition userCondition,
      PageInfo pageInfo) throws AppException {
    Condition condition = userCondition.getCondition();
    condition.setPageInfo(pageInfo);
    return userDao.findAll(UserListDto.class, UserListDto.builder(), condition);
  }

  @Override
  public List<UserTypeAheadDto> getUserTypeAheadDtoList(UserCondition userCondition,
      PageInfo pageInfo) throws AppException {
    Condition condition = userCondition.getCondition();
    condition.setPageInfo(pageInfo);
    return userDao.findAll(UserTypeAheadDto.class, UserTypeAheadDto.builder(), condition);
  }

  @Override
  public UserDto getUserDtoByUserName(String userName) throws AppException {
    return userDao.getDtoByUniqueField(UserDto.class, UserDto.builder(),
        "userName", userName);
  }

  @Override
  public UserDto getUserDtoByEmail(String email) throws AppException {
    return userDao.getDtoByUniqueField(UserDto.class, UserDto.builder(),
        "email", email);
  }

  @Override
  public UserDto getUserDtoByUserId(Integer userId) throws AppException {
    return userDao.getDtoByUniqueField(UserDto.class, UserDto.builder(),
        "userId", userId);
  }

  @Override
  public UserCenterDto getUserCenterDtoByUserName(String userName)
      throws AppException {
    return userDao.getDtoByUniqueField(UserCenterDto.class,
        UserCenterDto.builder(), "userName",
        userName);
  }

  @Override
  public Long count(UserCondition userCondition) throws AppException {
    return userDao.count(userCondition.getCondition());
  }

  @Override
  public void updateUserByUserId(Map<String, Object> properties, Integer userId)
      throws AppException {
    userDao.updateEntitiesByField(properties, "userId", userId.toString());
  }

  @Override
  public Boolean checkUserExists(String userName) throws AppException {
    UserDto userDto = getUserDtoByUserName(userName);
    return userDto != null;
  }

  @Override
  public Boolean checkUserExists(Integer userId) throws AppException {
    UserDto userDto = getUserDtoByUserId(userId);
    return userDto != null;
  }

  @Override
  public List<Integer> createOnsiteUsersByUserList(List<UserDto> userList) throws AppException {
    // Property userName, password, nickName and name is already exists.
    List<Integer> userIdList = new LinkedList<>();
    for (UserDto userDto : userList) {
      userDto.setStudentId("123456");
      userDto.setSchool("UESTC");
      // Avoid conflict email address.
      userDto.setEmail(userDto.getUserName() + userDto.getPassword() + "@cdoj.com");
      userDto.setSolved(0);
      userDto.setTried(0);
      // Constant user
      userDto.setType(AuthenticationType.CONSTANT.ordinal());
      userDto.setMotto("");
      userDto.setLastLogin(new Timestamp(new Date().getTime()));
      userDto.setDepartmentId(1);
      userDto.setSex(GenderType.FEMALE.ordinal());
      userDto.setGrade(GradeType.FRESHMAN.ordinal());
      userDto.setPhone("123");
      userDto.setSize(TShirtsSizeType.M.ordinal());

      Integer newUserID = createNewUser(userDto);
      userIdList.add(newUserID);
    }
    return userIdList;
  }

  @Override
  public List<UserDto> fetchAllOnsiteUsersByContestId(Integer contestId) throws AppException {
    StringBuilder hqlBuilder = new StringBuilder();
    hqlBuilder
        .append("from User where")
        .append(" userId in (")
        .append("   select userId from ContestUser where contestId = ")
        .append(contestId)
        .append(" )")
        .append(")");
    return userDao.findAll(UserDto.class, UserDto.builder(), hqlBuilder.toString(), null);
  }

  @Override
  public UserEditorDto getUserEditorDtoByUserName(String userName)
      throws AppException {
    return userDao.getDtoByUniqueField(UserEditorDto.class,
        UserEditorDto.builder(), "userName", userName);
  }

}
