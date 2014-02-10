package cn.edu.uestc.acmicpc.web.oj.controller.user;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserAdminEditDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserCenterDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserEditDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserEditorDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserListDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserLoginDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserRegisterDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.userSerialKey.UserSerialKeyDTO;
import cn.edu.uestc.acmicpc.service.iface.DepartmentService;
import cn.edu.uestc.acmicpc.service.iface.EmailService;
import cn.edu.uestc.acmicpc.service.iface.ProblemService;
import cn.edu.uestc.acmicpc.service.iface.StatusService;
import cn.edu.uestc.acmicpc.service.iface.UserSerialKeyService;
import cn.edu.uestc.acmicpc.service.iface.UserService;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.FieldException;
import cn.edu.uestc.acmicpc.util.helper.StringUtil;
import cn.edu.uestc.acmicpc.util.settings.Global;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;
import cn.edu.uestc.acmicpc.web.oj.controller.base.BaseController;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

  private UserService userService;
  private ProblemService problemService;
  private StatusService statusService;
  private UserSerialKeyService userSerialKeyService;
  private EmailService emailService;
  private DepartmentService departmentService;

  @Autowired
  public UserController(UserService userService, ProblemService problemService, StatusService statusService, UserSerialKeyService userSerialKeyService, EmailService emailService, DepartmentService departmentService) {
    this.userService = userService;
    this.problemService = problemService;
    this.statusService = statusService;
    this.userSerialKeyService = userSerialKeyService;
    this.emailService = emailService;
    this.departmentService = departmentService;
  }

  @RequestMapping("login")
  @LoginPermit(NeedLogin = false)
  public
  @ResponseBody
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
        if (userDTO == null || !userLoginDTO.getPassword().equals(userDTO.getPassword())) {
          throw new FieldException("password", "User or password is wrong, please try again");
        }
        userDTO.setLastLogin(new Timestamp(new Date().getTime() / 1000 * 1000));
        userService.updateUser(userDTO);

        session.setAttribute("currentUser", userDTO);
        json.put("userName", userDTO.getUserName());
        json.put("type", userDTO.getType());
        json.put("email", userDTO.getEmail());
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

  @RequestMapping("logout")
  @LoginPermit(NeedLogin = true)
  public
  @ResponseBody
  Map<String, Object> logout(HttpSession session) {
    Map<String, Object> json = new HashMap<>();
    session.removeAttribute("currentUser");
    json.put("result", "success");
    return json;
  }

  @RequestMapping("register")
  @LoginPermit(NeedLogin = false)
  public
  @ResponseBody
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
        if (departmentService.getDepartmentName(userRegisterDTO.getDepartmentId()) == null) {
          throw new FieldException("departmentId", "Please choose a validate department.");
        }

        UserDTO userDTO = UserDTO.builder()
            .setUserName(userRegisterDTO.getUserName())
            .setStudentId(userRegisterDTO.getStudentId())
            .setPassword(userRegisterDTO.getPassword())
            .setSchool(userRegisterDTO.getSchool())
            .setNickName(userRegisterDTO.getNickName())
            .setEmail(userRegisterDTO.getEmail())
            .setSolved(0)
            .setTried(0)
            .setType(Global.AuthenticationType.NORMAL.ordinal())
            .setDepartmentId(userRegisterDTO.getDepartmentId())
            .setLastLogin(new Timestamp(new Date().getTime() / 1000 * 1000))
            .setMotto(userRegisterDTO.getMotto())
            .setDepartmentName(departmentService.getDepartmentName(
                userRegisterDTO.getDepartmentId()))
            .build();
        userService.createNewUser(userDTO);

        userDTO = userService.getUserDTOByUserName(userRegisterDTO.getUserName());
        if (userDTO == null) {
          throw new AppException("Register failed, please try again.");
        }
        session.setAttribute("currentUser", userDTO);
        json.put("userName", userDTO.getUserName());
        json.put("type", userDTO.getType());
        json.put("email", userDTO.getEmail());
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

  @RequestMapping("list")
  @LoginPermit(NeedLogin = false)
  public String list() {
    return "user/userList";
  }

  @RequestMapping("search")
  @LoginPermit(NeedLogin = false)
  public
  @ResponseBody
  Map<String, Object> search(@RequestBody UserCondition userCondition) {
    Map<String, Object> json = new HashMap<>();
    try {
      Long count = userService.count(userCondition);
      PageInfo pageInfo = buildPageInfo(count, userCondition.currentPage,
          Global.RECORD_PER_PAGE, null);
      List<UserListDTO> userList = userService.getUserListDTOList(userCondition, pageInfo);

      json.put("pageInfo", pageInfo);
      json.put("result", "success");
      json.put("list", userList);
    } catch (AppException e) {
      json.put("result", "error");
      json.put("error_msg", e.getMessage());
    } catch (Exception e) {
      json.put("result", "error");
      e.printStackTrace();
      json.put("error_msg", "Unknown exception occurred.");
    }
    return json;
  }

  @RequestMapping("center/{userName}")
  @LoginPermit(NeedLogin = false)
  public String center(@PathVariable("userName") String userName,
                       ModelMap model) {
    try {
      UserCenterDTO userCenterDTO = userService.getUserCenterDTOByUserName(userName);
      if (userCenterDTO == null) {
        throw new AppException("No such user!");
      }
      Map<Integer, Global.AuthorStatusType> problemStatus = new TreeMap<>();

      List<Integer> results = problemService.getAllVisibleProblemIds();
      for (Integer result : results) {
        problemStatus.put(result, Global.AuthorStatusType.NONE);
      }
      results = statusService.findAllUserTriedProblemIds(userCenterDTO.getUserId());
      for (Integer result : results) {
        if (problemStatus.containsKey(result)) {
          problemStatus.put(result, Global.AuthorStatusType.FAIL);
        }
      }
      results = statusService.findAllUserAcceptedProblemIds(userCenterDTO.getUserId());
      for (Integer result : results) {
        if (problemStatus.containsKey(result)) {
          problemStatus.put(result, Global.AuthorStatusType.PASS);
        }
      }
      model.put("problemStatus", problemStatus);
      model.put("targetUser", userCenterDTO);
    } catch (AppException e) {
      return "error/404";
    }
    return "user/userCenter";
  }

  @RequestMapping("edit")
  @LoginPermit(NeedLogin = true)
  public
  @ResponseBody
  Map<String, Object> edit(HttpSession session,
                           @RequestBody @Valid UserEditDTO userEditDTO,
                           BindingResult validateResult) {
    Map<String, Object> json = new HashMap<>();
    if (validateResult.hasErrors()) {
      json.put("result", "field_error");
      json.put("field", validateResult.getFieldErrors());
    } else {
      try {
        UserDTO currentUser = (UserDTO) session.getAttribute("currentUser");

        if (!currentUser.getUserName().equals(userEditDTO.getUserName())) {
          throw new AppException("You can only edit your information.");
        }
        UserDTO userDTO = userService.getUserDTOByUserName(userEditDTO.getUserName());
        if (userDTO == null) {
          throw new AppException("No such user.");
        }
        if (!userEditDTO.getOldPassword().equals(currentUser.getPassword())) {
          throw new FieldException("oldPassword", "Your password is wrong, please try again.");
        }
        if (userEditDTO.getNewPassword() != null) {
          if (userEditDTO.getNewPasswordRepeat() == null) {
            throw new FieldException("newPasswordRepeat", "Please repeat your new password.");
          }
          if (!userEditDTO.getNewPassword().equals(userEditDTO.getNewPasswordRepeat())) {
            throw new FieldException("newPasswordRepeat", "Password do not match.");
          }
          userDTO.setPassword(userEditDTO.getNewPassword());
        }

        userDTO.setNickName(userEditDTO.getNickName());
        userDTO.setSchool(userEditDTO.getSchool());
        userDTO.setDepartmentId(userEditDTO.getDepartmentId());
        userDTO.setStudentId(userEditDTO.getStudentId());
        userDTO.setMotto(userEditDTO.getMotto());

        userService.updateUser(userDTO);
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

  @RequestMapping("adminEdit")
  @LoginPermit(Global.AuthenticationType.ADMIN)
  public
  @ResponseBody
  Map<String, Object> adminEdit(@RequestBody @Valid UserAdminEditDTO userAdminEditDTO,
                                BindingResult validateResult) {
    Map<String, Object> json = new HashMap<>();
    if (validateResult.hasErrors()) {
      json.put("result", "field_error");
      json.put("field", validateResult.getFieldErrors());
    } else {
      try {
        UserDTO userDTO = userService.getUserDTOByUserName(userAdminEditDTO.getUserName());
        if (userDTO == null) {
          throw new AppException("No such user.");
        }
        if (userAdminEditDTO.getNewPassword() != null) {
          if (userAdminEditDTO.getNewPasswordRepeat() == null) {
            throw new FieldException("newPasswordRepeat", "Please repeat your new password.");
          }
          if (!userAdminEditDTO.getNewPassword().equals(userAdminEditDTO.getNewPasswordRepeat())) {
            throw new FieldException("newPasswordRepeat", "Password do not match.");
          }
          userDTO.setPassword(userAdminEditDTO.getNewPassword());
        }

        userDTO.setNickName(userAdminEditDTO.getNickName());
        userDTO.setSchool(userAdminEditDTO.getSchool());
        userDTO.setDepartmentId(userAdminEditDTO.getDepartmentId());
        userDTO.setStudentId(userAdminEditDTO.getStudentId());
        userDTO.setMotto(userAdminEditDTO.getMotto());
        userDTO.setType(userAdminEditDTO.getType());

        userService.updateUser(userDTO);
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

  @RequestMapping("sendSerialKey/{userName}")
  @LoginPermit(NeedLogin = false)
  public
  @ResponseBody
  Map<String, Object> sendSerialKey(@PathVariable("userName") String userName) {
    Map<String, Object> json = new HashMap<>();
    try {
      UserDTO userDTO = userService.getUserDTOByUserName(userName);
      if (userDTO == null) {
        throw new AppException("No such user!");
      }
      UserSerialKeyDTO userSerialKeyDTO = userSerialKeyService.generateUserSerialKey(userDTO.getUserId());

      if (emailService.sendUserSerialKey(userSerialKeyDTO)) {
        json.put("result", "success");
      } else {
        json.put("result", "failed");
      }
    } catch (AppException e) {
      System.out.println(e.getMessage());
      json.put("result", "error");
      json.put("error_msg", e.getMessage());
    }
    return json;
  }

  @RequestMapping("profile/{userName}")
  @LoginPermit(NeedLogin = true)
  public
  @ResponseBody
  Map<String, Object> profile(@PathVariable("userName") String userName,
                              HttpSession session) {
    Map<String, Object> json = new HashMap<>();
    try {
      UserDTO currentUser = (UserDTO) session.getAttribute("currentUser");

      if (currentUser.getType() != Global.AuthenticationType.ADMIN.ordinal()) {
        if (!currentUser.getUserName().equals(userName)) {
          throw new AppException("You can only view your information.");
        }
      }
      UserEditorDTO userEditorDTO = userService.getUserEditorDTOByUserName(userName);
      if (userEditorDTO == null) {
        throw new AppException("No such user.");
      }
      json.put("user", userEditorDTO);
      json.put("result", "success");
    } catch (AppException e) {
      json.put("result", "error");
      json.put("error_msg", e.getMessage());
    }
    return json;
  }

  @RequestMapping("activate/{userName}/{serialKey}")
  @LoginPermit(NeedLogin = false)
  public String activate(@PathVariable("userName") String userName,
                         @PathVariable("serialKey") String serialKey,
                         ModelMap model) {
    model.addAttribute("userName", userName);
    model.addAttribute("serialKey", serialKey);
    return "user/activate";
  }

  @RequestMapping("resetPassword")
  @LoginPermit(NeedLogin = false)
  public
  @ResponseBody
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
        if (userDTO == null) {
          throw new FieldException("userName", "No such user.");
        }
        UserSerialKeyDTO userSerialKeyDTO = userSerialKeyService.findUserSerialKeyDTOByUserId(
            userDTO.getUserId());
        if (userSerialKeyDTO == null ||
            new Date().getTime() - userSerialKeyDTO.getTime().getTime() > 1800000) {
          throw new FieldException("serialKey",
              "Serial Key exceed time limit! Please regenerate a new key.");
        }
        if (!StringUtil.encodeSHA1(userSerialKeyDTO.getSerialKey()).equals(userActivateDTO.getSerialKey())) {
          throw new FieldException("serialKey", "Serial Key is wrong!");
        }

        userDTO.setPassword(userActivateDTO.getPassword());
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
