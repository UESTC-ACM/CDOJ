package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.criteria.ContestUserCriteria;
import cn.edu.uestc.acmicpc.db.criteria.UserCriteria;
import cn.edu.uestc.acmicpc.db.dao.iface.ContestUserDao;
import cn.edu.uestc.acmicpc.db.dao.iface.UserDao;
import cn.edu.uestc.acmicpc.db.dto.field.ContestUserFields;
import cn.edu.uestc.acmicpc.db.dto.field.UserFields;
import cn.edu.uestc.acmicpc.db.dto.impl.ContestUserDto;
import cn.edu.uestc.acmicpc.db.dto.impl.UserDto;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.service.iface.UserService;
import cn.edu.uestc.acmicpc.util.enums.AuthenticationType;
import cn.edu.uestc.acmicpc.util.enums.GenderType;
import cn.edu.uestc.acmicpc.util.enums.GradeType;
import cn.edu.uestc.acmicpc.util.enums.TShirtsSizeType;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation for {@link UserService}.
 */
@SuppressWarnings("deprecation")
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends AbstractService implements UserService {
  private static final Logger logger = Logger.getLogger(UserServiceImpl.class);

  private final ContestUserDao contestUserDao;
  private final UserDao userDao;


  @Autowired
  public UserServiceImpl(ContestUserDao contestUserDao, UserDao userDao) {
    this.contestUserDao = contestUserDao;
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
  public List<UserDto> getUserListDtoList(
      UserCriteria criteria, PageInfo pageInfo) throws AppException {
    return userDao.findAll(criteria, pageInfo, UserFields.LIST_FIELDS);
  }

  @Override
  public List<UserDto> getUserTypeAheadDtoList(
      UserCriteria criteria, PageInfo pageInfo) throws AppException {
    return userDao.findAll(criteria, pageInfo, UserFields.TYPE_AHEAD_FIELDS);
  }

  @Override
  public UserDto getUserDtoByUserName(String userName) throws AppException {
    UserCriteria criteria = new UserCriteria();
    criteria.userNameForUniqueQuery = userName;
    List<UserDto> result = userDao.findAll(criteria, null, UserFields.ALL_FIELDS);
    return result.isEmpty() ? null : result.iterator().next();
  }

  @Override
  public UserDto getUserDtoByEmail(String email) throws AppException {
    UserCriteria criteria = new UserCriteria();
    criteria.emailForUniqueQuery = email;
    List<UserDto> result = userDao.findAll(criteria, null, UserFields.ALL_FIELDS);
    return result.isEmpty() ? null : result.iterator().next();
  }

  @Override
  public UserDto getUserDtoByUserId(Integer userId) throws AppException {
    UserCriteria criteria = new UserCriteria();
    criteria.userId = userId;
    List<UserDto> result = userDao.findAll(criteria, null, UserFields.ALL_FIELDS);
    return result.isEmpty() ? null : result.iterator().next();
  }

  @Override
  public UserDto getUserCenterDtoByUserName(String userName)
      throws AppException {
    UserCriteria criteria = new UserCriteria();
    criteria.userNameForUniqueQuery = userName;
    List<UserDto> result = userDao.findAll(criteria, null, UserFields.CENTER_FIELDS);
    return result.isEmpty() ? null : result.iterator().next();
  }

  @Override
  public Long count(UserCriteria criteria) throws AppException {
    return userDao.count(criteria);
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
    ContestUserCriteria contestUserCriteria = new ContestUserCriteria();
    contestUserCriteria.contestId = contestId;
    List<ContestUserDto> contestUsers =
        contestUserDao.findAll(contestUserCriteria, null, ContestUserFields.ALL_FIELDS);

    UserCriteria userCriteria = new UserCriteria();
    userCriteria.userIds =
        contestUsers.stream().map(ContestUserDto::getUserId).collect(Collectors.toSet());
    return userDao.findAll(userCriteria, null, UserFields.ALL_FIELDS);
  }

  @Override
  public UserDto getUserEditorDtoByUserName(String userName)
      throws AppException {
    UserCriteria criteria = new UserCriteria();
    criteria.userNameForUniqueQuery = userName;
    List<UserDto> results = userDao.findAll(criteria, null, UserFields.EDITOR_FIELDS);
    return results.isEmpty() ? null : results.iterator().next();
  }

}
