package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.impl.UserCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IUserDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserCenterDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserEditorDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserListDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserTypeAheadDTO;
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

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
    if (userDTO.getUserName() != null) {
      user.setUserName(userDTO.getUserName());
    }
    if (userDTO.getStudentId() != null) {
      user.setStudentId(userDTO.getStudentId());
    }
    if (userDTO.getPassword() != null) {
      user.setPassword(userDTO.getPassword());
    }
    if (userDTO.getSchool() != null) {
      user.setSchool(userDTO.getSchool());
    }
    if (userDTO.getNickName() != null) {
      user.setNickName(userDTO.getNickName());
    }
    if (userDTO.getEmail() != null) {
      user.setEmail(userDTO.getEmail());
    }
    if (userDTO.getSolved() != null) {
      user.setSolved(userDTO.getSolved());
    }
    if (userDTO.getTried() != null) {
      user.setTried(userDTO.getTried());
    }
    if (userDTO.getType() != null) {
      user.setType(userDTO.getType());
    }
    if (userDTO.getDepartmentId() != null) {
      user.setDepartmentId(userDTO.getDepartmentId());
    }
    if (userDTO.getLastLogin() != null) {
      user.setLastLogin(userDTO.getLastLogin());
    }
    if (userDTO.getMotto() != null) {
      user.setMotto(userDTO.getMotto());
    }
    if (userDTO.getName() != null) {
      user.setName(userDTO.getName());
    }
    if (userDTO.getSex() != null) {
      user.setSex(userDTO.getSex());
    }
    if (userDTO.getGrade() != null) {
      user.setGrade(userDTO.getGrade());
    }
    if (userDTO.getPhone() != null) {
      user.setPhone(userDTO.getPhone());
    }
    if (userDTO.getSize() != null) {
      user.setSize(userDTO.getSize());
    }
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
  public Integer createNewUser(UserDTO userDTO) throws AppException {
    User user = new User();
    updateUserByUserDTO(user, userDTO);
    userDAO.add(user);
    return user.getUserId();
  }

  @Override
  public List<UserListDTO> getUserListDTOList(UserCondition userCondition,
                                              PageInfo pageInfo) throws AppException {
    Condition condition = userCondition.getCondition();
    condition.setPageInfo(pageInfo);
    return userDAO.findAll(UserListDTO.class, UserListDTO.builder(), condition);
  }

  @Override
  public List<UserTypeAheadDTO> getUserTypeAheadDTOList(UserCondition userCondition,
                                                        PageInfo pageInfo) throws AppException {
    Condition condition = userCondition.getCondition();
    condition.setPageInfo(pageInfo);
    return userDAO.findAll(UserTypeAheadDTO.class, UserTypeAheadDTO.builder(), condition);
  }

  @Override
  public UserDTO getUserDTOByUserName(String userName) throws AppException {
    return userDAO.getDTOByUniqueField(UserDTO.class, UserDTO.builder(),
        "userName", userName);
  }

  @Override
  public UserDTO getUserDTOByEmail(String email) throws AppException {
    return userDAO.getDTOByUniqueField(UserDTO.class, UserDTO.builder(),
        "email", email);
  }

  @Override
  public UserDTO getUserDTOByUserId(Integer userId) throws AppException {
    return userDAO.getDTOByUniqueField(UserDTO.class, UserDTO.builder(),
        "userId", userId);
  }

  @Override
  public UserCenterDTO getUserCenterDTOByUserName(String userName)
      throws AppException {
    return userDAO.getDTOByUniqueField(UserCenterDTO.class,
        UserCenterDTO.builder(), "userName",
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

  @Override
  public Boolean checkUserExists(String userName) throws AppException {
    UserDTO userDTO = getUserDTOByUserName(userName);
    return userDTO != null;
  }

  @Override
  public Boolean checkUserExists(Integer userId) throws AppException {
    UserDTO userDTO = getUserDTOByUserId(userId);
    return userDTO != null;
  }

  @Override
  public List<Integer> createOnsiteUsersByUserList(List<UserDTO> userList) throws AppException {
    // Property userName, password, nickName and name is already exists.
    List<Integer> userIdList = new LinkedList<>();
    for (UserDTO userDTO: userList) {
      userDTO.setStudentId("123456");
      userDTO.setSchool("UESTC");
      // Avoid conflict email address.
      userDTO.setEmail(userDTO.getUserName() + userDTO.getPassword() + "@cdoj.com");
      userDTO.setSolved(0);
      userDTO.setTried(0);
      // Constant user
      userDTO.setType(AuthenticationType.CONSTANT.ordinal());
      userDTO.setMotto("");
      userDTO.setLastLogin(new Timestamp(new Date().getTime()));
      userDTO.setDepartmentId(1);
      userDTO.setSex(GenderType.FEMALE.ordinal());
      userDTO.setGrade(GradeType.FRESHMAN.ordinal());
      userDTO.setPhone("123");
      userDTO.setSize(TShirtsSizeType.M.ordinal());

      Integer newUserID = createNewUser(userDTO);
      userIdList.add(newUserID);
    }
    return userIdList;
  }

  @Override
  public List<UserDTO> fetchAllOnsiteUsersByContestId(Integer contestId) throws AppException {
    StringBuilder hqlBuilder = new StringBuilder();
    hqlBuilder
        .append("from User where")
        .append(" userId in (")
        .append("   select userId from ContestUser where contestId = ")
        .append(contestId)
        .append(" )")
        .append(")");
    return userDAO.findAll(UserDTO.class, UserDTO.builder(), hqlBuilder.toString(), null);
  }

  @Override
  public UserEditorDTO getUserEditorDTOByUserName(String userName)
      throws AppException {
    return userDAO.getDTOByUniqueField(UserEditorDTO.class,
        UserEditorDTO.builder(), "userName", userName);
  }

}
