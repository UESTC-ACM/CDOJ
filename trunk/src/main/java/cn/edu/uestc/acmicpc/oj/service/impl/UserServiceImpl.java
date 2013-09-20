package cn.edu.uestc.acmicpc.oj.service.impl;

import java.sql.Timestamp;
import java.util.Date;

import cn.edu.uestc.acmicpc.db.entity.Department;
import cn.edu.uestc.acmicpc.ioc.service.GlobalServiceAware;
import cn.edu.uestc.acmicpc.service.iface.GlobalService;
import cn.edu.uestc.acmicpc.service.impl.AbstractService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.uestc.acmicpc.db.dao.iface.IDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IDepartmentDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IUserDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.UserDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.UserLoginDTO;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.ioc.dao.DepartmentDAOAware;
import cn.edu.uestc.acmicpc.ioc.dao.UserDAOAware;
import cn.edu.uestc.acmicpc.ioc.util.GlobalAware;
import cn.edu.uestc.acmicpc.oj.service.iface.UserService;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.StringUtil;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.FieldException;
import cn.edu.uestc.acmicpc.util.exception.FieldNotUniqueException;

/**
 * Implementation for {@link UserService}.
 */
@Service
public class UserServiceImpl extends AbstractService implements UserService, UserDAOAware,
    GlobalServiceAware {

  private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);
  @Autowired
  private IUserDAO userDAO;
  @Autowired
  private GlobalService globalService;

  @Override
  public void setUserDAO(IUserDAO userDAO) {
    this.userDAO = userDAO;
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
    if (!StringUtil.trimAllSpace(userDTO.getNickName()).equals(userDTO.getNickName()))
      throw new FieldException("nickName", "Nick name should not have useless blank.");
    if (getUserByUserName(userDTO.getUserName()) != null)
      throw new FieldException("userName", "User name has been used!");
    if (getUserByEmail(userDTO.getEmail()) != null)
      throw new FieldException("email", "Email has benn used!");
    Department department = globalService.getDepartmentByDepartmentId(userDTO.getDepartmentId());
    if (department == null)
      throw new FieldException("departmentId", "Please choose a validate department.");

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

  @Override
  public User getUserByUserId(Integer userId) throws AppException {
    return userDAO.get(userId);
  }

  @Override
  public IDAO<User, Integer> getDAO() {
    return userDAO;
  }

  @Override
  public void setGlobalService(GlobalService globalService) {
    this.globalService = globalService;
  }
}
