package cn.edu.uestc.acmicpc.oj.service.impl;

import java.sql.Timestamp;
import java.util.*;

import cn.edu.uestc.acmicpc.db.dto.impl.UserDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.UserRegisterDTO;
import cn.edu.uestc.acmicpc.db.entity.UserSerialKey;
import cn.edu.uestc.acmicpc.oj.service.iface.*;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import cn.edu.uestc.acmicpc.db.condition.impl.UserCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IUserDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.UserLoginDTO;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.db.view.impl.UserView;
import cn.edu.uestc.acmicpc.oj.view.PageInfo;
import cn.edu.uestc.acmicpc.service.iface.EmailService;
import cn.edu.uestc.acmicpc.service.iface.GlobalService;
import cn.edu.uestc.acmicpc.service.impl.AbstractService;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.StringUtil;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;
import cn.edu.uestc.acmicpc.util.exception.FieldException;
import cn.edu.uestc.acmicpc.util.exception.FieldNotUniqueException;

/**
 * Implementation for {@link UserService}.
 */
@Service
@Primary
public class UserServiceImpl extends AbstractService implements UserService {

  private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);
  private final IUserDAO userDAO;
  private final GlobalService globalService;
  private final EmailService emailService;
  private final ProblemService problemService;
  private final StatusService statusService;
  private final UserSerialKeyService userSerialKeyService;
  private final DepartmentService departmentService;

  @Autowired
  public UserServiceImpl(IUserDAO userDAO,
                         GlobalService globalService,
                         EmailService emailService,
                         ProblemService problemService,
                         StatusService statusService,
                         UserSerialKeyService userSerialKeyService,
                         DepartmentService departmentService) {
    this.userDAO = userDAO;
    this.globalService = globalService;
    this.emailService = emailService;
    this.problemService = problemService;
    this.statusService = statusService;
    this.userSerialKeyService = userSerialKeyService;
    this.departmentService = departmentService;
  }

  private UserDTO getUserDTOByUser(User user) {
    return UserDTO.builder()
        .setUserId(user.getUserId())
        .setUserName(user.getUserName())
        .setStudentId(user.getStudentId())
        .setPassword(user.getPassword())
        .setSchool(user.getSchool())
        .setNickName(user.getNickName())
        .setEmail(user.getEmail())
        .setSolved(user.getSolved())
        .setTried(user.getTried())
        .setType(user.getType())
        .setTypeName(globalService.getAuthenticationName(user.getType()))
        .setDepartmentId(user.getDepartmentId())
        .setDepartmentName(departmentService.getDepartmentName(user.getDepartmentId()))
        .build();
  }

  private void updateUserByUserDTO(User user, UserDTO userDTO) {
    user.setUserId(userDTO.getUserId());
    user.setUserName(userDTO.getUserName());
    user.setStudentId(userDTO.getStudentId());
    user.setPassword(userDTO.getPassword());
    user.setSchool(userDTO.getSchool());
    user.setNickName(userDTO.getNickName());
    user.setEmail(userDTO.getEmail());
    user.setSolved(userDTO.getSolved());
    user.setTried(userDTO.getTried());
    user.setType(userDTO.getType());
    user.setDepartmentId(userDTO.getDepartmentId());
  }

  @Override
  public UserDTO getUserByUserName(String userName) throws AppException {
    try {
      User user = userDAO.getEntityByUniqueField("userName", userName);
      return getUserDTOByUser(user);
    } catch (FieldNotUniqueException e) {
      LOGGER.error(e);
      throw new AppException(e);
    }
  }

  @Override
  public UserDTO getUserByEmail(String email) throws AppException {
    try {
      User user = userDAO.getEntityByUniqueField("email", email);
      return getUserDTOByUser(user);
    } catch (FieldNotUniqueException e) {
      LOGGER.error(e);
      throw new AppException(e);
    }
  }

  @Override
  public UserDTO getUserByUserId(Integer userId) throws AppException {
    User user = userDAO.get(userId);
    return getUserDTOByUser(user);
  }

  @Override
  public IUserDAO getDAO() {
    return userDAO;
  }

  @Override
  public void updateUser(UserDTO userDTO) throws AppException {
    User user = userDAO.get(userDTO.getUserId());
    AppExceptionUtil.assertNotNull(user);
    AppExceptionUtil.assertNotNull(user.getUserId());
    updateUserByUserDTO(user, userDTO);
    userDAO.update(user);
  }

  @Override
  public void createNewUser(UserDTO userDTO) throws AppException {
    User user = new User();
    updateUserByUserDTO(user, userDTO);
    userDAO.add(user);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<UserDTO> search(UserCondition userCondition, PageInfo pageInfo)
      throws AppException {
    userCondition.currentPage = pageInfo.getCurrentPage();
    userCondition.countPerPage = Global.RECORD_PER_PAGE;
    List<User> userList = (List<User>) userDAO.findAll(userCondition.getCondition());
    List<UserDTO> userDTOList = new ArrayList<>();
    for (User user : userList)
    userDTOList.add(getUserDTOByUser(user));
    return userDTOList;
  }

  @Override
  public Long count(UserCondition userCondition) throws AppException {
    return userDAO.count(userCondition.getCondition());
  }

  /*
  @Override
  public UserView getUserViewByUserName(String userName) throws AppException {
    User user = getUserByUserName(userName);
    if (user == null)
      throw new AppException("No such user!");
    return new UserView(user);
  }

  @Override
  public void edit(UserRegisterDTO userRegisterDTO, UserRegisterDTO currentUser) throws AppException {
    if (!currentUser.getUserId().equals(userRegisterDTO.getUserId())) {
      throw new AppException("You can only edit your information.");
    }
    User user = getUserByUserId(userRegisterDTO.getUserId());
    if (user == null) {
      throw new AppException("No such user.");
    }
    if (!StringUtil.encodeSHA1(userRegisterDTO.getOldPassword()).equals(user.getPassword())) {
      throw new FieldException("oldPassword", "Your passowrd is wrong, please try again.");
    }
    if (userRegisterDTO.getPassword() != null) {
      if (userRegisterDTO.getPasswordRepeat() == null) {
        throw new FieldException("passwordRepeat", "Please repeat your new password.");
      }
      if (!userRegisterDTO.getPassword().equals(userRegisterDTO.getPasswordRepeat())) {
        throw new FieldException("passwordRepeat", "Password do not match.");
      }
    }

    updateUserByUserDTO(user, userRegisterDTO);
    updateUser(user);
  }

  @Override
  public UserRegisterDTO getUserDTOByUser(User user) throws AppException {
    return UserRegisterDTO.builder()
        .setUserId(user.getUserId())
        .setUserName(user.getUserName())
        .setNickName(user.getNickName())
        .setEmail(user.getEmail())
        .setLastLogin(user.getLastLogin())
        .setType(user.getType())
        .build();
  }

  @Override
  public User getUserByUserDTO(UserRegisterDTO userRegisterDTO) throws AppException {
//    Department department = globalService.getDepartmentById(userRegisterDTO.getDepartmentId());
    User user = new User();
    user.setTried(0);
    user.setNickName(userRegisterDTO.getNickName());
    // TODO(mzry1992): just use departmentId.
//    user.setDepartmentByDepartmentId(department);
    user.setEmail(userRegisterDTO.getEmail());
    user.setLastLogin(new Timestamp(new Date().getTime() / 1000 * 1000));
    user.setPassword(StringUtil.encodeSHA1(userRegisterDTO.getPassword()));
    user.setSchool(userRegisterDTO.getSchool());
    user.setSolved(0);
    user.setStudentId(userRegisterDTO.getStudentId());
    // TODO(mzry1992): I think here should be type?
    user.setType(userRegisterDTO.getType());
    user.setUserName(userRegisterDTO.getUserName());
    return user;
  }

  @Override
  public void updateUserByUserDTO(User user, UserRegisterDTO userRegisterDTO) throws AppException {
    if (userRegisterDTO.getPassword() != null)
      user.setPassword(StringUtil.encodeSHA1(userRegisterDTO.getPassword()));
    user.setNickName(userRegisterDTO.getNickName());
    user.setSchool(userRegisterDTO.getSchool());
    // TODO(mzry1992): just use departmentId.
//    user.setDepartmentByDepartmentId(globalService.getDepartmentById(userRegisterDTO.getDepartmentId()));
    user.setStudentId(userRegisterDTO.getStudentId());
  }

  @Override
  public Map<Integer, Global.AuthorStatusType> getUserProblemStatus(String userName) throws AppException {
    User user = getUserByUserName(userName);
    if (user == null)
      throw new AppException("No such user!");
    Map<Integer, Global.AuthorStatusType> problemStatus = new HashMap<>();

    List<Integer> results = problemService.getAllVisibleProblemIds();
    for (Integer result : results)
      problemStatus.put(result, Global.AuthorStatusType.NONE);

    results = statusService.findAllUserTriedProblemIds(user.getUserId());
    for (Integer result : results)
      if (problemStatus.containsKey(result))
        problemStatus.put(result, Global.AuthorStatusType.FAIL);

    results = statusService.findAllUserAcceptedProblemIds(user.getUserId());
    for (Integer result : results)
      if (problemStatus.containsKey(result))
        problemStatus.put(result, Global.AuthorStatusType.PASS);

    return problemStatus;
  }

  @Override
  public Boolean sendSerialKey(String userName) throws AppException {
    User user = getUserByUserName(userName);
    if (user == null) {
      throw new AppException("No such user!");
    }

    UserSerialKey userSerialKey = userSerialKeyService.generateUserSerialKey(user.getUserId());

    StringBuilder stringBuilder = new StringBuilder();
    Random random = new Random();
    for (int i = 0; i < Global.USER_SERIAL_KEY_LENGTH; ++i) {
      stringBuilder.append((char) (random.nextInt(126 - 33 + 1) + 33));
    }
    String serialKey = stringBuilder.toString();
    userSerialKey.setTime(new Timestamp(new Date().getTime()));
    userSerialKey.setSerialKey(serialKey);
    userSerialKey.setUserByUserId(user);
    userSerialKeyService.createNewUserSerialKey(userSerialKey);

    return emailService.sendUserSerialKey(userSerialKey);
  }
  */
}
