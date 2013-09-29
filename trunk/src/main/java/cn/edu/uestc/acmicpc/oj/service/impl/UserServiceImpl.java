package cn.edu.uestc.acmicpc.oj.service.impl;

import java.util.ArrayList;
import java.util.List;

import cn.edu.uestc.acmicpc.db.dto.impl.user.UserSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import cn.edu.uestc.acmicpc.db.condition.impl.UserCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IUserDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.UserDTO;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.oj.service.iface.DepartmentService;
import cn.edu.uestc.acmicpc.oj.service.iface.UserService;
import cn.edu.uestc.acmicpc.oj.view.PageInfo;
import cn.edu.uestc.acmicpc.service.iface.GlobalService;
import cn.edu.uestc.acmicpc.service.impl.AbstractService;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;

/**
 * Implementation for {@link UserService}.
 */
@Service
@Primary
public class UserServiceImpl extends AbstractService implements UserService {

  private final IUserDAO userDAO;
  private final GlobalService globalService;
  private final DepartmentService departmentService;

  @Autowired
  public UserServiceImpl(IUserDAO userDAO,
                         GlobalService globalService,
                         DepartmentService departmentService) {
    this.userDAO = userDAO;
    this.globalService = globalService;
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
    User user = (User)userDAO.getEntityByUniqueField("userName", userName);
    return getUserDTOByUser(user);
  }

  @Override
  public UserDTO getUserByEmail(String email) throws AppException {
    User user = (User)userDAO.getEntityByUniqueField("email", email);
    return getUserDTOByUser(user);
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
  public List<UserSummaryDTO> search(UserCondition userCondition, PageInfo pageInfo)
      throws AppException {
    userCondition.currentPage = pageInfo.getCurrentPage();
    userCondition.countPerPage = Global.RECORD_PER_PAGE;
    return userDAO.findAll(UserSummaryDTO.class,
        UserSummaryDTO.builder(),
        userCondition.getCondition());
  }

  @Override
  public Long count(UserCondition userCondition) throws AppException {
    return userDAO.count(userCondition.getCondition());
  }

}
