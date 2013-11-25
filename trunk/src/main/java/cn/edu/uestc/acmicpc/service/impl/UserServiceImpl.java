package cn.edu.uestc.acmicpc.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.impl.UserCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IUserDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserAdminSummaryDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserCenterDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserSummaryDTO;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.service.iface.UserService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;
import cn.edu.uestc.acmicpc.web.view.PageInfo;

/**
 * Implementation for {@link UserService}.
 */
@Service
public class UserServiceImpl extends AbstractService implements UserService {

  private final IUserDAO userDAO;

  @Autowired
  public UserServiceImpl(IUserDAO userDAO) {
    this.userDAO = userDAO;
  }

  private void updateUserByUserDTO(User user, UserDTO userDTO) {
    if (userDTO.getUserName() != null)
      user.setUserName(userDTO.getUserName());
    if (userDTO.getStudentId() != null)
      user.setStudentId(userDTO.getStudentId());
    if (userDTO.getPassword() != null)
      user.setPassword(userDTO.getPassword());
    if (userDTO.getSchool() != null)
      user.setSchool(userDTO.getSchool());
    if (userDTO.getNickName() != null)
      user.setNickName(userDTO.getNickName());
    if (userDTO.getEmail() != null)
      user.setEmail(userDTO.getEmail());
    if (userDTO.getSolved() != null)
      user.setSolved(userDTO.getSolved());
    if (userDTO.getTried() != null)
      user.setTried(userDTO.getTried());
    if (userDTO.getType() != null)
      user.setType(userDTO.getType());
    if (userDTO.getDepartmentId() != null)
      user.setDepartmentId(userDTO.getDepartmentId());
    if (userDTO.getLastLogin() != null)
      user.setLastLogin(userDTO.getLastLogin());
    if (userDTO.getMotto() != null)
      user.setMotto(userDTO.getMotto());
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

  @Override
  public List<UserSummaryDTO> search(UserCondition userCondition, PageInfo pageInfo)
      throws AppException {
    Condition condition = userCondition.getCondition();
    condition.setPageInfo(pageInfo);
    return userDAO.findAll(UserSummaryDTO.class,
        UserSummaryDTO.builder(),
        condition);
  }

  @Override
  public List<UserAdminSummaryDTO> adminSearch(UserCondition userCondition, PageInfo pageInfo)
      throws AppException {
    Condition condition = userCondition.getCondition();
    condition.setPageInfo(pageInfo);
    return userDAO.findAll(UserAdminSummaryDTO.class, UserAdminSummaryDTO.builder(),
        condition);
  }

  @Override
  public UserDTO getUserDTOByUserName(String userName) throws AppException {
    return userDAO.getDTOByUniqueField(UserDTO.class, UserDTO.builder(), "userName", userName);
  }

  @Override
  public UserDTO getUserDTOByEmail(String email) throws AppException {
    return userDAO.getDTOByUniqueField(UserDTO.class, UserDTO.builder(), "email", email);
  }

  @Override
  public UserDTO getUserDTOByUserId(Integer userId) throws AppException {
    return userDAO.getDTOByUniqueField(UserDTO.class, UserDTO.builder(), "userId", userId);
  }

  @Override
  public UserCenterDTO getUserCenterDTOByUserName(String userName) throws AppException {
    return userDAO.getDTOByUniqueField(UserCenterDTO.class, UserCenterDTO.builder(), "userName",
        userName);
  }

  @Override
  public Long count(UserCondition userCondition) throws AppException {
    return userDAO.count(userCondition.getCondition());
  }

  @Override
  public void updateUserByUserId(Map<String, Object> properties, Integer userId)
      throws AppException {
    userDAO.updateEntitiesByField(properties, "userId", userId.toString());
  }

}
