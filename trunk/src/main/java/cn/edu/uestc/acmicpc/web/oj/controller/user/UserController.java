package cn.edu.uestc.acmicpc.web.oj.controller.user;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.uestc.acmicpc.db.condition.impl.UserCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserActivateDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserCenterDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserEditDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserLoginDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserRegisterDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserSummaryDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.userSerialKey.UserSerialKeyDTO;
import cn.edu.uestc.acmicpc.service.iface.DepartmentService;
import cn.edu.uestc.acmicpc.service.iface.EmailService;
import cn.edu.uestc.acmicpc.service.iface.GlobalService;
import cn.edu.uestc.acmicpc.service.iface.ProblemService;
import cn.edu.uestc.acmicpc.service.iface.StatusService;
import cn.edu.uestc.acmicpc.service.iface.UserSerialKeyService;
import cn.edu.uestc.acmicpc.service.iface.UserService;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.StringUtil;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.FieldException;
import cn.edu.uestc.acmicpc.web.oj.controller.base.BaseController;
import cn.edu.uestc.acmicpc.web.view.PageInfo;

/**
 * User controller
 * <ul>
 *   <li><strong>/user/login</strong> Upload user's name and password in json, and do login operation on server</li>
 *   <li><strong>/user/logout</strong> Logout current user</li>
 *   <li><strong>/user/register</strong> Upload user's information and generate a new user</li>
 *   <li><strong>/user/sendSerialKey/{userName}</strong></li>
 *   <li><strong>/user/activate/{userName}/{serialKey}</strong></li>
 *   <li><strong>/user/resetPassword</strong></li> TODO
 *   <li><strong>/user/center/{userName}</strong> Visit user center</li>
 *   <li><strong>/user/status/{userName}</strong> User problem submission status</li>
 *   <li><strong>/user/list</strong> User list page</li>
 *   <li><strong>/user/search</strong> Search users by user condition</li>
 *   <li><strong>/user/edit</strong> Update user information</li>
 * </ul>
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

  private UserService userService;
  private GlobalService globalService;
  private DepartmentService departmentService;
  private ProblemService problemService;
  private StatusService statusService;
  private UserSerialKeyService userSerialKeyService;
  private EmailService emailService;

  @Autowired
  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  @Autowired
  public void setGlobalService(GlobalService globalService) {
    this.globalService = globalService;
  }

  @Autowired
  public void setDepartmentService(DepartmentService departmentService) {
    this.departmentService = departmentService;
  }

  @Autowired
  public void setProblemService(ProblemService problemService) {
    this.problemService = problemService;
  }

  @Autowired
  public void setStatusService(StatusService statusService) {
    this.statusService = statusService;
  }

  @Autowired
  public void setUserSerialKeyService(UserSerialKeyService userSerialKeyService) {
    this.userSerialKeyService = userSerialKeyService;
  }

  @Autowired
  public void setEmailService(EmailService emailService) {
    this.emailService = emailService;
  }

  /**
   * Login controller.
   *
   * @param session HTTP session entity.
   * @param userLoginDTO User DTO
   * @param validateResult Validation result
   * @return <ul>
   *         <li>For success: {"result":"success"}</li>
   *         <li>For error: {"result":"error", "error_msg":<strong>error message</strong>}</li>
   *         <li>For validation error: {"result":"field_error","field":<strong>Field
   *         errors</strong>}</li>
   *         </ul>
   */
  @RequestMapping("login")
  @LoginPermit(NeedLogin = false)
  public @ResponseBody
  Map<String, Object> login(HttpSession session,
      @RequestBody @Valid UserLoginDTO userLoginDTO,
      BindingResult validateResult) {
    Map<String, Object> json = new HashMap<>();
    if (validateResult.hasErrors()) {
      json.put("result", "field_error");
      json.put("field", validateResult.getFieldErrors());
    } else {
      try {
        UserDTO userDTO = userService.getUserDTOByUserName(userLoginDTO.getUserName());
        if (userDTO == null || !StringUtil.encodeSHA1(userLoginDTO.getPassword())
            .equals(userDTO.getPassword()))
          throw new FieldException("password", "User or password is wrong, please try again");
        userDTO.setLastLogin(new Timestamp(new Date().getTime() / 1000 * 1000));
        userService.updateUser(userDTO);

        session.setAttribute("currentUser", userDTO);
        json.put("result", "success");
      } catch (FieldException e) {
        putFieldErrorsIntoBindingResult(e, validateResult);
        json.put("result", "field_error");
        json.put("field", validateResult.getFieldErrors());
      } catch (AppException e) {
        json.put("result", "error");
        json.put("error_msg", e.getMessage());
      }
    }
    return json;
  }

  /**
   * Logout controller.
   *
   * @param session session
   * @return {"result":"success"}
   */
  @RequestMapping("logout")
  @LoginPermit(NeedLogin = true)
  public @ResponseBody
  Map<String, Object> logout(HttpSession session) {
    Map<String, Object> json = new HashMap<>();
    session.removeAttribute("currentUser");
    json.put("result", "success");
    return json;
  }

  /**
   * Register controller
   *
   * @param session session
   * @param userRegisterDTO User DTO
   * @param validateResult Validation result
   * @return <ul>
   *         <li>For success: {"result":"success"}</li>
   *         <li>For error: {"result":"error", "error_msg":<strong>error message</strong>}</li>
   *         <li>For validation error: {"result":"field_error","field":<strong>Field
   *         errors</strong>}</li>
   *         </ul>
   */
  @RequestMapping("register")
  @LoginPermit(NeedLogin = false)
  public @ResponseBody
  Map<String, Object> register(HttpSession session,
      @RequestBody @Valid UserRegisterDTO userRegisterDTO,
      BindingResult validateResult) {
    Map<String, Object> json = new HashMap<>();
    if (validateResult.hasErrors()) {
      json.put("result", "field_error");
      json.put("field", validateResult.getFieldErrors());
    } else {
      try {
        if (userRegisterDTO.getPassword() == null) {
          throw new FieldException("password", "Please enter your password.");
        }
        if (userRegisterDTO.getPasswordRepeat() == null) {
          throw new FieldException("passwordRepeat", "Please repeat your password.");
        }
        if (!userRegisterDTO.getPassword().equals(userRegisterDTO.getPasswordRepeat())) {
          throw new FieldException("passwordRepeat", "Password do not match.");
        }
        if (!StringUtil.trimAllSpace(userRegisterDTO.getNickName())
            .equals(userRegisterDTO.getNickName())) {
          throw new FieldException("nickName", "Nick name should not have useless blank.");
        }
        if (userService.getUserDTOByUserName(userRegisterDTO.getUserName()) != null) {
          throw new FieldException("userName", "User name has been used!");
        }
        if (userService.getUserDTOByEmail(userRegisterDTO.getEmail()) != null) {
          throw new FieldException("email", "Email has benn used!");
        }
        if (departmentService.getDepartmentName(userRegisterDTO.getDepartmentId()) == null)
          throw new FieldException("departmentId", "Please choose a validate department.");

        UserDTO userDTO = UserDTO.builder()
            .setUserName(userRegisterDTO.getUserName())
            .setStudentId(userRegisterDTO.getStudentId())
            .setPassword(StringUtil.encodeSHA1(userRegisterDTO.getPassword()))
            .setSchool(userRegisterDTO.getSchool())
            .setNickName(userRegisterDTO.getNickName())
            .setEmail(userRegisterDTO.getEmail())
            .setSolved(0)
            .setTried(0)
            .setType(Global.AuthenticationType.NORMAL.ordinal())
            .setDepartmentId(userRegisterDTO.getDepartmentId())
            .setLastLogin(new Timestamp(new Date().getTime() / 1000 * 1000))
            .setDepartmentName(departmentService.getDepartmentName(
                userRegisterDTO.getDepartmentId()))
            .build();
        userService.createNewUser(userDTO);

        userDTO = userService.getUserDTOByUserName(userRegisterDTO.getUserName());
        if (userDTO == null)
          throw new AppException("Register failed, please try again.");
        session.setAttribute("currentUser", userDTO);
        json.put("result", "success");
      } catch (FieldException e) {
        putFieldErrorsIntoBindingResult(e, validateResult);
        json.put("result", "field_error");
        json.put("field", validateResult.getFieldErrors());
      } catch (AppException e) {
        json.put("result", "error");
        json.put("error_msg", e.getMessage());
      }
    }
    return json;
  }

  /**
   * User list
   *
   * @param model model map
   * @return userList view
   */
  @RequestMapping("list")
  @LoginPermit(NeedLogin = false)
  public String list(ModelMap model) {
    model.put("departmentList", departmentService.getDepartmentList());
    model.put("authenticationTypeList", globalService.getAuthenticationTypeList());
    return "user/userList";
  }

  /**
   * Search user
   *
   * @param userCondition condition
   * @return
   * <ul>
   * <li>
   * For success: {"result":"ok", "pageInfo":<strong>PageInfo object</strong>, "condition",
   * <strong>UserCondition entity</strong>, "userList":<strong>query result</strong>}</li>
   * <li>
   * For error: {"result":"error", "error_msg":<strong>error message</strong>}</li>
   * </ul>
   */
  @RequestMapping("search")
  @LoginPermit(NeedLogin = false)
  public @ResponseBody
  Map<String, Object> search(@RequestBody UserCondition userCondition) {
    Map<String, Object> json = new HashMap<>();
    try {
      Long count = userService.count(userCondition);
      PageInfo pageInfo = buildPageInfo(count, userCondition.currentPage,
          Global.RECORD_PER_PAGE, "", null);
      List<UserSummaryDTO> userList = userService.search(userCondition, pageInfo);

      json.put("pageInfo", pageInfo.getHtmlString());
      json.put("result", "success");
      json.put("userList", userList);
    }  catch (AppException e) {
      json.put("result", "error");
      json.put("error_msg", e.getMessage());
    } catch (Exception e) {
      json.put("result", "error");
      e.printStackTrace();
      json.put("error_msg", "Unknown exception occurred.");
    }
    return json;
  }

  /**
   * Go to the user center
   *
   * @param userName user name, specified in url
   * @param model model
   * @return userCenter page if this user exist, other wise return 404 error page.
   */
  @RequestMapping("center/{userName}")
  @LoginPermit(NeedLogin = false)
  public String center(@PathVariable("userName") String userName,
                           ModelMap model) {
    try {
      UserCenterDTO userCenterDTO = userService.getUserCenterDTOByUserName(userName);
      if (userCenterDTO == null) {
        throw new AppException("No such user!");
      }

      model.put("departmentList", departmentService.getDepartmentList());
      model.put("targetUser", userCenterDTO);
    } catch (AppException e) {
      return "error/404";
    }
    return "user/userCenter";
  }

  /**
   * Update user information
   * @param session session
   * @param userEditDTO form information
   * @param validateResult validate result
   * @return <ul>
   *         <li>For success: {"result":"success"}</li>
   *         <li>For error: {"result":"error", "error_msg":<strong>error message</strong>}</li>
   *         <li>For validation error: {"result":"field_error","field":<strong>Field
   *         errors</strong>}</li>
   *         </ul>
   */
  @RequestMapping("edit")
  @LoginPermit(NeedLogin = true)
  public @ResponseBody
  Map<String, Object> edit(HttpSession session,
                           @RequestBody @Valid UserEditDTO userEditDTO,
                           BindingResult validateResult) {
    Map<String, Object> json = new HashMap<>();
    if (validateResult.hasErrors()) {
      json.put("result", "field_error");
      json.put("field", validateResult.getFieldErrors());
    } else {
      try {
        UserDTO currentUser = (UserDTO)session.getAttribute("currentUser");

        if (!currentUser.getUserId().equals(userEditDTO.getUserId())) {
          throw new AppException("You can only edit your information.");
        }
        UserDTO userDTO = userService.getUserDTOByUserId(userEditDTO.getUserId());
        if (userDTO == null) {
          throw new AppException("No such user.");
        }
        if (!StringUtil.encodeSHA1(userEditDTO.getOldPassword())
            .equals(currentUser.getPassword())) {
          throw new FieldException("oldPassword", "Your passowrd is wrong, please try again.");
        }
        if (userEditDTO.getNewPassword() != null) {
          if (userEditDTO.getNewPasswordRepeat() == null) {
            throw new FieldException("newPasswordRepeat", "Please repeat your new password.");
          }
          if (!userEditDTO.getNewPassword().equals(userEditDTO.getNewPasswordRepeat())) {
            throw new FieldException("newPasswordRepeat", "Password do not match.");
          }
          userDTO.setPassword(StringUtil.encodeSHA1(userEditDTO.getNewPassword()));
        }

        userDTO.setNickName(userEditDTO.getNickName());
        userDTO.setSchool(userEditDTO.getSchool());
        userDTO.setDepartmentId(userEditDTO.getDepartmentId());
        userDTO.setStudentId(userEditDTO.getStudentId());

        userService.updateUser(userDTO);
        session.setAttribute("currentUser", userDTO);
        json.put("result", "success");
      } catch (FieldException e) {
        putFieldErrorsIntoBindingResult(e, validateResult);
        json.put("result", "field_error");
        json.put("field", validateResult.getFieldErrors());
      } catch (AppException e) {
        json.put("result", "error");
        json.put("error_msg", e.getMessage());
      }
    }
    return json;
  }

  /**
   * Find all problem that target user passed or failed.
   *
   * @param userName user name
   * @return <ul>
   * <li>
   * For success: {"result":"success", "problemStatus":<strong>ProblemStatus object</strong>,
   * "problemCount":<strong>Tot problems</strong>}</li>
   * <li>
   * For error: {"result":"error", "error_msg":<strong>error message</strong>}</li>
   * </ul>
   */
  @RequestMapping("status/{userName}")
  @LoginPermit(NeedLogin = false)
  public @ResponseBody
  Map<String, Object> status(@PathVariable("userName") String userName) {
    Map<String, Object> json = new HashMap<>();
    try {
      UserDTO userDTO = userService.getUserDTOByUserName(userName);
      if (userDTO == null)
        throw new AppException("No such user!");

      Map<Integer, Global.AuthorStatusType> problemStatus = new HashMap<>();

      List<Integer> results = problemService.getAllVisibleProblemIds();
      for (Integer result : results)
        problemStatus.put(result, Global.AuthorStatusType.NONE);

      results = statusService.findAllUserTriedProblemIds(userDTO.getUserId());
      for (Integer result : results)
        if (problemStatus.containsKey(result))
          problemStatus.put(result, Global.AuthorStatusType.FAIL);

      results = statusService.findAllUserAcceptedProblemIds(userDTO.getUserId());
      for (Integer result : results)
        if (problemStatus.containsKey(result))
          problemStatus.put(result, Global.AuthorStatusType.PASS);

      json.put("status", problemStatus);
      json.put("result", "success");
    } catch (AppException e) {
      json.put("result", "error");
      json.put("error_msg", e.getMessage());
    }
    return json;
  }

  /**
   * Send serial key to user
   *
   * @param userName user name
   * @return <ul>
   * <li>
   * For success: {"result":"success"}</li>
   * <li>
   * For error: {"result":"error", "error_msg":<strong>error message</strong>}</li>
   * </ul>
   */
  @RequestMapping("sendSerialKey/{userName}")
  @LoginPermit(NeedLogin = false)
  public @ResponseBody
  Map<String, Object> sendSerialKey(@PathVariable("userName") String userName) {
    Map<String, Object> json = new HashMap<>();
    try {
      UserDTO userDTO = userService.getUserDTOByUserName(userName);
      if (userDTO == null)
        throw new AppException("No such user!");
      UserSerialKeyDTO userSerialKeyDTO = userSerialKeyService.generateUserSerialKey(userDTO.getUserId());

      if (emailService.sendUserSerialKey(userSerialKeyDTO))
        json.put("result", "success");
      else
        json.put("result", "failed");
    } catch (AppException e) {
      System.out.println(e.getMessage());
      json.put("result", "error");
      json.put("error_msg", e.getMessage());
    }
    return json;
  }

  /**
   * Go to activate page
   *
   * @param userName user name in path
   * @param serialKey serial key in path
   * @param model model map
   * @return activate page
   */
  @RequestMapping("activate/{userName}/{serialKey}")
  @LoginPermit(NeedLogin = false)
  public String activate(@PathVariable("userName") String userName,
                         @PathVariable("serialKey") String serialKey,
                         ModelMap model) {
    model.addAttribute("userName", userName);
    model.addAttribute("serialKey", serialKey);
    return "user/activate";
  }

  /**
   * Reset password
   *
   * @param userActivateDTO user name and serial key collected form form.
   * @param validateResult validate result
   * @return <ul>
   *         <li>For success: {"result":"success"}</li>
   *         <li>For error: {"result":"error", "error_msg":<strong>error message</strong>}</li>
   *         <li>For validation error: {"result":"field_error","field":<strong>Field
   *         errors</strong>}</li>
   *         </ul>
   */
  @RequestMapping("resetPassword")
  @LoginPermit(NeedLogin = false)
  public @ResponseBody
  Map<String, Object> resetPassword(@RequestBody @Valid UserActivateDTO userActivateDTO,
                                    BindingResult validateResult) {
    Map<String, Object> json = new HashMap<>();
    if (validateResult.hasErrors()) {
      json.put("result", "field_error");
      json.put("field", validateResult.getFieldErrors());
    } else {
      try {
        if (userActivateDTO.getPassword() == null) {
          throw new FieldException("password", "Please enter your password.");
        }
        if (userActivateDTO.getPasswordRepeat() == null) {
          throw new FieldException("passwordRepeat", "Please repeat your password.");
        }
        if (!userActivateDTO.getPassword().equals(userActivateDTO.getPasswordRepeat())) {
          throw new FieldException("passwordRepeat", "Password do not match.");
        }
        UserDTO userDTO = userService.getUserDTOByUserName(userActivateDTO.getUserName());
        if (userDTO == null)
          throw new FieldException("userName", "No such user.");
        UserSerialKeyDTO userSerialKeyDTO = userSerialKeyService.findUserSerialKeyDTOByUserId(
            userDTO.getUserId());
        if (userSerialKeyDTO == null ||
            new Date().getTime() - userSerialKeyDTO.getTime().getTime() > 1800000)
          throw new FieldException("serialKey",
              "Serial Key exceed time limit! Please regenerate a new key.");
        if (!StringUtil.encodeSHA1(userSerialKeyDTO.getSerialKey())
            .equals(userActivateDTO.getSerialKey()))
          throw new FieldException("serialKey", "Serial Key is wrong!");

        userDTO.setPassword(StringUtil.encodeSHA1(userActivateDTO.getPassword()));
        userService.updateUser(userDTO);

        userSerialKeyDTO.setSerialKey("This key has been used.");
        userSerialKeyService.updateUserSerialKey(userSerialKeyDTO);
        json.put("result", "success");
      } catch (FieldException e) {
        putFieldErrorsIntoBindingResult(e, validateResult);
        json.put("result", "field_error");
        json.put("field", validateResult.getFieldErrors());
      } catch (AppException e) {
        json.put("result", "error");
        json.put("error_msg", e.getMessage());
      }
    }
    return json;
  }
}
