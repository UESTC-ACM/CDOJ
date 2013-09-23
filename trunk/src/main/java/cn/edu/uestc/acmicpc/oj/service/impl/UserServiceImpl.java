package cn.edu.uestc.acmicpc.oj.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.impl.UserCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IUserDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.UserDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.UserLoginDTO;
import cn.edu.uestc.acmicpc.db.entity.Department;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.db.view.impl.UserView;
import cn.edu.uestc.acmicpc.oj.service.iface.UserService;
import cn.edu.uestc.acmicpc.oj.view.PageInfo;
import cn.edu.uestc.acmicpc.service.iface.GlobalService;
import cn.edu.uestc.acmicpc.service.impl.AbstractService;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.StringUtil;
import cn.edu.uestc.acmicpc.util.exception.AppException;
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

  @Autowired
  public UserServiceImpl(IUserDAO userDAO, GlobalService globalService) {
    this.userDAO = userDAO;
    this.globalService = globalService;
  }

  @Override
  public User getUserByUserName(String userName) throws AppException {
    try {
      return userDAO.getEntityByUniqueField("userName", userName);
    } catch (FieldNotUniqueException e) {
      LOGGER.error(e);
      throw new AppException(e);
    }
  }

  @Override
  public void updateUser(User user) throws AppException {
    userDAO.update(user);
  }

  @Override
  public User getUserByEmail(String email) throws AppException {
    try {
      return userDAO.getEntityByUniqueField("email", email);
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

    return UserDTO.builder()
        .setUserName(user.getUserName())
        .setNickName(user.getNickName())
        .setEmail(user.getEmail())
        .setLastLogin(user.getLastLogin())
        .setType(user.getType())
        .build();
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
    Department department = globalService.getDepartmentById(userDTO.getDepartmentId());
    if (department == null) {
      throw new FieldException("departmentId", "Please choose a validate department.");
    }

    User user = new User();
    user.setTried(0);
    user.setNickName(userDTO.getNickName());
    user.setDepartmentByDepartmentId(department);
    user.setEmail(userDTO.getEmail());
    user.setLastLogin(new Timestamp(new Date().getTime() / 1000 * 1000));
    user.setPassword(StringUtil.encodeSHA1(userDTO.getPassword()));
    user.setSchool(userDTO.getSchool());
    user.setSolved(0);
    user.setStudentId(userDTO.getStudentId());
    user.setType(Global.AuthenticationType.NORMAL.ordinal());
    user.setUserName(userDTO.getUserName());
    createNewUser(user);
    return userDTO;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<UserView> search(UserCondition userCondition, PageInfo pageInfo) throws AppException {
    Condition condition = userCondition.getCondition();
    condition.setCurrentPage(pageInfo.getCurrentPage());
    condition.setCountPerPage(Global.RECORD_PER_PAGE);
    List<User> userList = (List<User>) userDAO.findAll(condition);
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
    if (!currentUser.getUserName().equals(userDTO.getUserName())) {
      throw new AppException("You can only edit your information.");
    }
    User user = getUserByUserName(userDTO.getUserName());
    if (user == null) {
      throw new AppException("No such user.");
    }
    if (!StringUtil.encodeSHA1(userDTO.getOldPassword()).equals(user.getPassword())) {
      throw new FieldException("oldPassword", "Your passowrd is wrong, please try again.");
    }
    System.out.println("|" + userDTO.getPassword() + "|");
    if (userDTO.getPassword() != null) {
      if (userDTO.getPasswordRepeat() == null) {
        throw new FieldException("passwordRepeat", "Please repeat your new password.");
      }
      if (!userDTO.getPassword().equals(userDTO.getPasswordRepeat())) {
        throw new FieldException("passwordRepeat", "Password do not match.");
      }
      user.setPassword(StringUtil.encodeSHA1(userDTO.getPassword()));
    }
    user.setNickName(userDTO.getNickName());
    user.setSchool(userDTO.getSchool());
    user.setDepartmentByDepartmentId(globalService.getDepartmentById(userDTO.getDepartmentId()));
    user.setStudentId(userDTO.getStudentId());
    updateUser(user);
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
