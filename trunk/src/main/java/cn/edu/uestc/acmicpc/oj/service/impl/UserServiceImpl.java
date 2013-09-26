package cn.edu.uestc.acmicpc.oj.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import cn.edu.uestc.acmicpc.db.condition.impl.UserCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IUserDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.UserDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.UserLoginDTO;
import cn.edu.uestc.acmicpc.db.entity.Department;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.db.view.impl.UserView;
import cn.edu.uestc.acmicpc.oj.service.iface.ProblemService;
import cn.edu.uestc.acmicpc.oj.service.iface.StatusService;
import cn.edu.uestc.acmicpc.oj.service.iface.UserService;
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
  @SuppressWarnings("unused")
  private final GlobalService globalService;
  @SuppressWarnings("unused")
  private final EmailService emailService;
  private final ProblemService problemService;
  private final StatusService statusService;

  @Autowired
  public UserServiceImpl(IUserDAO userDAO,
                         GlobalService globalService,
                         EmailService emailService,
                         ProblemService problemService,
                         StatusService statusService) {
    this.userDAO = userDAO;
    this.globalService = globalService;
    this.emailService = emailService;
    this.problemService = problemService;
    this.statusService = statusService;
  }

  @Override
  public User getUserByUserName(String userName) throws AppException {
    try {
      return (User) userDAO.getEntityByUniqueField("userName", userName);
    } catch (FieldNotUniqueException e) {
      LOGGER.error(e);
      throw new AppException(e);
    }
  }

  @Override
  public void updateUser(User user) throws AppException {
    AppExceptionUtil.assertNotNull(user);
    AppExceptionUtil.assertNotNull(user.getUserId());
    userDAO.update(user);
  }

  @Override
  public User getUserByEmail(String email) throws AppException {
    try {
      return (User) userDAO.getEntityByUniqueField("email", email);
    } catch (FieldNotUniqueException e) {
      LOGGER.error(e);
      throw new AppException(e);
    }
  }

  @Override
  public void createNewUser(User user) throws AppException {
    userDAO.add(user);
  }

  @Override
  public UserDTO login(UserLoginDTO userLoginDTO) throws AppException {
    User user = getUserByUserName(userLoginDTO.getUserName());
    if (user == null
        || !StringUtil.encodeSHA1(userLoginDTO.getPassword()).equals(user.getPassword()))
      throw new FieldException("password", "User or password is wrong, please try again");

    user.setLastLogin(new Timestamp(new Date().getTime() / 1000 * 1000));
    userDAO.update(user);

    return getUserDTOByUser(user);
  }

  @Override
  public UserDTO register(UserDTO userDTO) throws AppException {
    if (userDTO.getPassword() == null) {
      throw new FieldException("password", "Please enter your password.");
    }
    if (userDTO.getPasswordRepeat() == null) {
      throw new FieldException("passwordRepeat", "Please repeat your password.");
    }
    if (!userDTO.getPassword().equals(userDTO.getPasswordRepeat())) {
      throw new FieldException("passwordRepeat", "Password do not match.");
    }
    if (!StringUtil.trimAllSpace(userDTO.getNickName()).equals(userDTO.getNickName())) {
      throw new FieldException("nickName", "Nick name should not have useless blank.");
    }
    if (getUserByUserName(userDTO.getUserName()) != null) {
      throw new FieldException("userName", "User name has been used!");
    }
    if (getUserByEmail(userDTO.getEmail()) != null) {
      throw new FieldException("email", "Email has benn used!");
    }

    User user = getUserByUserDTO(userDTO);
    // TODO(mzry1992): just use depaetment Id.
    if (user.getDepartmentId() == null) {
      throw new FieldException("departmentId", "Please choose a validate department.");
    }

    createNewUser(user);

    return getUserDTOByUser(user);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<UserView> search(UserCondition userCondition, PageInfo pageInfo)
      throws AppException {
    userCondition.currentPage = pageInfo.getCurrentPage();
    userCondition.countPerPage = Global.RECORD_PER_PAGE;
    List<User> userList = (List<User>) userDAO.findAll(userCondition.getCondition());
    List<UserView> userViewList = new ArrayList<>();
    for (User user : userList) {
      UserView userView = new UserView(user);
      userViewList.add(userView);
    }
    return userViewList;
  }

  @Override
  public Long count(UserCondition userCondition) throws AppException {
    return userDAO.count(userCondition.getCondition());
  }

  @Override
  public UserView getUserViewByUserName(String userName) throws AppException {
    User user = getUserByUserName(userName);
    if (user == null)
      throw new AppException("No such user!");
    return new UserView(user);
  }

  @Override
  public void edit(UserDTO userDTO, UserDTO currentUser) throws AppException {
    if (!currentUser.getUserId().equals(userDTO.getUserId())) {
      throw new AppException("You can only edit your information.");
    }
    User user = getUserByUserId(userDTO.getUserId());
    if (user == null) {
      throw new AppException("No such user.");
    }
    if (!StringUtil.encodeSHA1(userDTO.getOldPassword()).equals(user.getPassword())) {
      throw new FieldException("oldPassword", "Your passowrd is wrong, please try again.");
    }
    if (userDTO.getPassword() != null) {
      if (userDTO.getPasswordRepeat() == null) {
        throw new FieldException("passwordRepeat", "Please repeat your new password.");
      }
      if (!userDTO.getPassword().equals(userDTO.getPasswordRepeat())) {
        throw new FieldException("passwordRepeat", "Password do not match.");
      }
    }

    updateUserByUserDTO(user, userDTO);
    updateUser(user);
  }

  @Override
  public UserDTO getUserDTOByUser(User user) throws AppException {
    return UserDTO.builder()
        .setUserId(user.getUserId())
        .setUserName(user.getUserName())
        .setNickName(user.getNickName())
        .setEmail(user.getEmail())
        .setLastLogin(user.getLastLogin())
        .setType(user.getType())
        .build();
  }

  @Override
  public User getUserByUserDTO(UserDTO userDTO) throws AppException {
    Department department = globalService.getDepartmentById(userDTO.getDepartmentId());
    User user = new User();
    user.setTried(0);
    user.setNickName(userDTO.getNickName());
    user.setDepartmentId(department == null ? null : department.getDepartmentId());
    user.setEmail(userDTO.getEmail());
    user.setLastLogin(new Timestamp(new Date().getTime() / 1000 * 1000));
    user.setPassword(StringUtil.encodeSHA1(userDTO.getPassword()));
    user.setSchool(userDTO.getSchool());
    user.setSolved(0);
    user.setStudentId(userDTO.getStudentId());
    // TODO(mzry1992): I think here should be type?
    user.setType(userDTO.getType());
    user.setUserName(userDTO.getUserName());
    return user;
  }

  @Override
  public void updateUserByUserDTO(User user, UserDTO userDTO) throws AppException {
    if (userDTO.getPassword() != null)
      user.setPassword(StringUtil.encodeSHA1(userDTO.getPassword()));
    user.setNickName(userDTO.getNickName());
    user.setSchool(userDTO.getSchool());
    // TODO(mzry1992): just use departmentId.
//    user.setDepartmentByDepartmentId(globalService.getDepartmentById(userDTO.getDepartmentId()));
    user.setStudentId(userDTO.getStudentId());
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
    /*UserSerialKey userSerialKey =
        userSerialKeyDAO.getEntityByUniqueField("userId", targetUser, "userByUserId", true);
    if (userSerialKey != null) {
      // less than 30 minutes
         * if (new Date().getTime() - userSerialKey.getTime().getTime() <= 1800000) { throw new
         * AppException( "serial key can only be generated in every 30 minutes."); }
    } else {
      userSerialKey = new UserSerialKey();
    }
    StringBuilder stringBuilder = new StringBuilder();
    Random random = new Random();
    for (int i = 0; i < Global.USER_SERIAL_KEY_LENGTH; ++i) {
      stringBuilder.append((char) (random.nextInt(126 - 33 + 1) + 33));
    }
    String serialKey = stringBuilder.toString();
    userSerialKey.setTime(new Timestamp(new Date().getTime()));
    userSerialKey.setSerialKey(serialKey);
    userSerialKey.setUserByUserId(targetUser);
    userSerialKeyDAO.update(userSerialKey);

    String url =
        settings.SETTING_HOST
            + getActionURL("/user",
            "activate/" + targetUser.getUserName() + "/" + StringUtil.encodeSHA1(serialKey));
    stringBuilder = new StringBuilder();
    stringBuilder.append("Dear ").append(targetUser.getUserName()).append(" :\n\n");
    stringBuilder
        .append("To reset your password, simply click on the link below or paste into the url field on your favorite browser:\n\n");
    stringBuilder.append(url).append("\n\n");
    stringBuilder
        .append("The activation link will only be good for 30 minutes, after that you will have to try again from the beginning. When you visit the above page, you'll be able to set your password as you like.\n\n");
    stringBuilder
        .append("If you have any questions about the system, feel free to contact us anytime at acm@uestc.edu.cn.\n\n");
    stringBuilder.append("The UESTC OJ Team.\n");

    eMailSender.send(targetUser.getEmail(), "UESTC Online Judge", stringBuilder.toString());
    */
    return true;
  }

  @Override
  public User getUserByUserId(Integer userId) throws AppException {
    return userDAO.get(userId);
  }

  @Override
  public IUserDAO getDAO() {
    return userDAO;
  }
}
