package cn.edu.uestc.acmicpc.web.oj.controller.user;

import cn.edu.uestc.acmicpc.db.condition.impl.UserCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.UserSerialKeyDto;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserActivateDto;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserAdminEditDto;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserCenterDto;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDto;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserEditDto;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserEditorDto;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserListDto;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserLoginDto;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserProblemStatusDto;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserRegisterDto;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserTypeAheadDto;
import cn.edu.uestc.acmicpc.service.iface.DepartmentService;
import cn.edu.uestc.acmicpc.service.iface.EmailService;
import cn.edu.uestc.acmicpc.service.iface.ProblemService;
import cn.edu.uestc.acmicpc.service.iface.StatusService;
import cn.edu.uestc.acmicpc.service.iface.UserSerialKeyService;
import cn.edu.uestc.acmicpc.service.iface.UserService;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.enums.AuthenticationType;
import cn.edu.uestc.acmicpc.util.enums.ProblemSolveStatusType;
import cn.edu.uestc.acmicpc.util.enums.ProblemType;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;
import cn.edu.uestc.acmicpc.util.exception.FieldException;
import cn.edu.uestc.acmicpc.util.helper.StringUtil;
import cn.edu.uestc.acmicpc.util.settings.Settings;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;
import cn.edu.uestc.acmicpc.web.oj.controller.base.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

  private final UserService userService;
  private final ProblemService problemService;
  private final StatusService statusService;
  private final UserSerialKeyService userSerialKeyService;
  private final EmailService emailService;
  private final DepartmentService departmentService;
  private final Settings settings;

  @Autowired
  public UserController(UserService userService,
      ProblemService problemService,
      StatusService statusService,
      UserSerialKeyService userSerialKeyService,
      EmailService emailService,
      DepartmentService departmentService,
      Settings settings) {
    this.userService = userService;
    this.problemService = problemService;
    this.statusService = statusService;
    this.userSerialKeyService = userSerialKeyService;
    this.emailService = emailService;
    this.departmentService = departmentService;
    this.settings = settings;
  }

  @RequestMapping("login")
  @LoginPermit(NeedLogin = false)
  public @ResponseBody Map<String, Object> login(HttpSession session,
      @RequestBody @Valid UserLoginDto userLoginDto,
      BindingResult validateResult) {
    Map<String, Object> json = new HashMap<>();
    if (validateResult.hasErrors()) {
      json.put("result", "field_error");
      json.put("field", validateResult.getFieldErrors());
    } else {
      try {
        UserDto userDto = userService.getUserDtoByUserName(userLoginDto.getUserName());
        if (userDto == null || !Objects.equals(userLoginDto.getPassword(), userDto.getPassword())) {
          throw new FieldException("password", "User or password is wrong, please try again");
        }
        userDto.setLastLogin(new Timestamp(new Date().getTime() / 1000 * 1000));
        userService.updateUser(userDto);

        session.setAttribute("currentUser", userDto);
        json.put("userName", userDto.getUserName());
        json.put("type", userDto.getType());
        json.put("email", userDto.getEmail());
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
  public @ResponseBody Map<String, Object> logout(HttpSession session) {
    Map<String, Object> json = new HashMap<>();
    // Clear sessions
    session.invalidate();
    json.put("result", "success");
    return json;
  }

  @RequestMapping("register")
  @LoginPermit(NeedLogin = false)
  public @ResponseBody Map<String, Object> register(HttpSession session,
      @RequestBody @Valid UserRegisterDto userRegisterDto,
      BindingResult validateResult) {
    Map<String, Object> json = new HashMap<>();
    if (validateResult.hasErrors()) {
      json.put("result", "field_error");
      json.put("field", validateResult.getFieldErrors());
    } else {
      try {
        if (userRegisterDto.getPassword() == null) {
          throw new FieldException("password", "Please enter your password.");
        }
        if (userRegisterDto.getPasswordRepeat() == null) {
          throw new FieldException("passwordRepeat", "Please repeat your password.");
        }
        if (!Objects.equals(userRegisterDto.getPassword(), userRegisterDto.getPasswordRepeat())) {
          throw new FieldException("passwordRepeat", "Password do not match.");
        }
        if (!Objects.equals(StringUtil.trimAllSpace(userRegisterDto.getNickName()),
            userRegisterDto.getNickName())) {
          throw new FieldException("nickName", "Nick name should not have useless blank.");
        }
        if (userService.getUserDtoByUserName(userRegisterDto.getUserName()) != null) {
          throw new FieldException("userName", "User name has been used!");
        }
        if (userService.getUserDtoByEmail(userRegisterDto.getEmail()) != null) {
          throw new FieldException("email", "Email has benn used!");
        }
        if (departmentService.getDepartmentName(userRegisterDto.getDepartmentId()) == null) {
          throw new FieldException("departmentId", "Please choose a validate department.");
        }

        UserDto userDto = UserDto.builder()
            .setUserName(userRegisterDto.getUserName())
            .setStudentId(userRegisterDto.getStudentId())
            .setPassword(userRegisterDto.getPassword())
            .setSchool(userRegisterDto.getSchool())
            .setNickName(userRegisterDto.getNickName())
            .setEmail(userRegisterDto.getEmail())
            .setSolved(0)
            .setTried(0)
            .setType(AuthenticationType.NORMAL.ordinal())
            .setDepartmentId(userRegisterDto.getDepartmentId())
            .setLastLogin(new Timestamp(new Date().getTime() / 1000 * 1000))
            .setMotto(userRegisterDto.getMotto())
            .setDepartmentName(departmentService.getDepartmentName(
                userRegisterDto.getDepartmentId()))
            .setName(userRegisterDto.getName())
            .setSex(userRegisterDto.getSex())
            .setGrade(userRegisterDto.getGrade())
            .setPhone(userRegisterDto.getPhone())
            .setSize(userRegisterDto.getSize())
            .build();
        userService.createNewUser(userDto);

        userDto = userService.getUserDtoByUserName(userRegisterDto.getUserName());
        if (userDto == null) {
          throw new AppException("Register failed, please try again.");
        }
        session.setAttribute("currentUser", userDto);
        json.put("userName", userDto.getUserName());
        json.put("type", userDto.getType());
        json.put("email", userDto.getEmail());
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

  @RequestMapping("search")
  @LoginPermit(NeedLogin = false)
  public @ResponseBody Map<String, Object> search(@RequestBody UserCondition userCondition) {
    Map<String, Object> json = new HashMap<>();
    try {
      Long count = userService.count(userCondition);
      PageInfo pageInfo = buildPageInfo(count, userCondition.currentPage,
          settings.RECORD_PER_PAGE, null);
      List<UserListDto> userList = userService.getUserListDtoList(userCondition, pageInfo);

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

  @RequestMapping("typeAheadItem/{userName}")
  public @ResponseBody Map<String, Object> typeAheadResult(@PathVariable("userName") String userName) {
    Map<String, Object> json = new HashMap<>();
    try {
      UserDto userDto = userService.getUserDtoByUserName(userName);
      AppExceptionUtil.assertNotNull(userDto, "No such user!");
      json.put("result", "success");
      json.put("user", UserTypeAheadDto.builder()
          .setUserId(userDto.getUserId())
          .setUserName(userDto.getUserName())
          .setNickName(userDto.getNickName())
          .setEmail(userDto.getEmail())
          .build());
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

  @RequestMapping("typeAheadList")
  @LoginPermit(NeedLogin = false)
  public @ResponseBody Map<String, Object> typeAheadList(@RequestBody UserCondition userCondition) {
    Map<String, Object> json = new HashMap<>();
    try {
      Long count = userService.count(userCondition);
      PageInfo pageInfo = buildPageInfo(count, 1L, 6L, null);
      List<UserTypeAheadDto> userList =
          userService.getUserTypeAheadDtoList(userCondition, pageInfo);

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

  @RequestMapping("userCenterData/{userName}")
  @LoginPermit(NeedLogin = false)
  public @ResponseBody Map<String, Object> userCenterData(HttpSession session,
      @PathVariable("userName") String userName) {
    Map<String, Object> json = new HashMap<>();
    try {
      UserCenterDto userCenterDto = userService.getUserCenterDtoByUserName(userName);
      if (userCenterDto == null) {
        throw new AppException("No such user!");
      }
      Map<Integer, ProblemSolveStatusType> problemStatus = new TreeMap<>();

      List<Integer> results = problemService.getAllProblemIds(true, ProblemType.NORMAL);
      for (Integer result : results) {
        problemStatus.put(result, ProblemSolveStatusType.NONE);
      }
      results = statusService.findAllUserTriedProblemIds(userCenterDto.getUserId(),
          isAdmin(session));
      for (Integer result : results) {
        if (problemStatus.containsKey(result)) {
          problemStatus.put(result, ProblemSolveStatusType.FAIL);
        }
      }
      results = statusService.findAllUserAcceptedProblemIds(userCenterDto.getUserId(),
          isAdmin(session));
      for (Integer result : results) {
        if (problemStatus.containsKey(result)) {
          problemStatus.put(result, ProblemSolveStatusType.PASS);
        }
      }

      List<UserProblemStatusDto> problemStatusList = new LinkedList<>();
      for (Map.Entry<Integer, ProblemSolveStatusType> status : problemStatus.entrySet()) {
        problemStatusList
            .add(new UserProblemStatusDto(status.getKey(), status.getValue().ordinal()));
      }

      json.put("problemStatus", problemStatusList);
      json.put("targetUser", userCenterDto);
      json.put("result", "success");
    } catch (AppException e) {
      json.put("result", "error");
      json.put("error_msg", e.getMessage());
    }
    return json;
  }

  @RequestMapping("edit")
  @LoginPermit(NeedLogin = true)
  public @ResponseBody Map<String, Object> edit(HttpSession session,
      @RequestBody @Valid UserEditDto userEditDto,
      BindingResult validateResult) {
    Map<String, Object> json = new HashMap<>();
    if (validateResult.hasErrors()) {
      json.put("result", "field_error");
      json.put("field", validateResult.getFieldErrors());
    } else {
      try {
        UserDto currentUser = (UserDto) session.getAttribute("currentUser");

        if (!Objects.equals(currentUser.getUserName(), userEditDto.getUserName())) {
          throw new AppException("You can only edit your information.");
        }
        UserDto userDto = userService.getUserDtoByUserName(userEditDto.getUserName());
        if (userDto == null) {
          throw new AppException("No such user.");
        }
        if (userDto.getType() == AuthenticationType.CONSTANT.ordinal()) {
          throw new AppException("Permission denied!");
        }
        if (!Objects.equals(userEditDto.getOldPassword(), currentUser.getPassword())) {
          throw new FieldException("oldPassword", "Your password is wrong, please try again.");
        }
        if (userEditDto.getNewPassword() != null) {
          if (userEditDto.getNewPasswordRepeat() == null) {
            throw new FieldException("newPasswordRepeat", "Please repeat your new password.");
          }
          if (!Objects.equals(userEditDto.getNewPassword(), userEditDto.getNewPasswordRepeat())) {
            throw new FieldException("newPasswordRepeat", "Password do not match.");
          }
          userDto.setPassword(userEditDto.getNewPassword());
        }

        userDto.setNickName(userEditDto.getNickName());
        userDto.setSchool(userEditDto.getSchool());
        userDto.setDepartmentId(userEditDto.getDepartmentId());
        userDto.setStudentId(userEditDto.getStudentId());
        userDto.setMotto(userEditDto.getMotto());
        userDto.setName(userEditDto.getName());
        userDto.setSex(userEditDto.getSex());
        userDto.setGrade(userEditDto.getGrade());
        userDto.setPhone(userEditDto.getPhone());
        userDto.setSize(userEditDto.getSize());

        userService.updateUser(userDto);
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
  @LoginPermit(AuthenticationType.ADMIN)
  public @ResponseBody Map<String, Object> adminEdit(
      @RequestBody @Valid UserAdminEditDto userAdminEditDto,
      BindingResult validateResult) {
    Map<String, Object> json = new HashMap<>();
    if (validateResult.hasErrors()) {
      json.put("result", "field_error");
      json.put("field", validateResult.getFieldErrors());
    } else {
      try {
        UserDto userDto = userService.getUserDtoByUserName(userAdminEditDto.getUserName());
        if (userDto == null) {
          throw new AppException("No such user.");
        }
        if (userAdminEditDto.getNewPassword() != null) {
          if (userAdminEditDto.getNewPasswordRepeat() == null) {
            throw new FieldException("newPasswordRepeat", "Please repeat your new password.");
          }
          if (!Objects.equals(userAdminEditDto.getNewPassword(),
              userAdminEditDto.getNewPasswordRepeat())) {
            throw new FieldException("newPasswordRepeat", "Password do not match.");
          }
          userDto.setPassword(userAdminEditDto.getNewPassword());
        }

        userDto.setNickName(userAdminEditDto.getNickName());
        userDto.setSchool(userAdminEditDto.getSchool());
        userDto.setDepartmentId(userAdminEditDto.getDepartmentId());
        userDto.setStudentId(userAdminEditDto.getStudentId());
        userDto.setMotto(userAdminEditDto.getMotto());
        userDto.setType(userAdminEditDto.getType());
        userDto.setName(userAdminEditDto.getName());
        userDto.setSex(userAdminEditDto.getSex());
        userDto.setGrade(userAdminEditDto.getGrade());
        userDto.setPhone(userAdminEditDto.getPhone());
        userDto.setSize(userAdminEditDto.getSize());

        userService.updateUser(userDto);
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
  public @ResponseBody Map<String, Object> sendSerialKey(@PathVariable("userName") String userName) {
    Map<String, Object> json = new HashMap<>();
    try {
      UserDto userDto = userService.getUserDtoByUserName(userName);
      if (userDto == null) {
        throw new AppException("No such user!");
      }
      UserSerialKeyDto userSerialKeyDto = userSerialKeyService.generateUserSerialKey(userDto
          .getUserId());

      if (emailService.sendUserSerialKey(userSerialKeyDto)) {
        json.put("result", "success");
      } else {
        json.put("result", "failed");
      }
    } catch (AppException e) {
      json.put("result", "error");
      json.put("error_msg", e.getMessage());
    }
    return json;
  }

  @RequestMapping("profile/{userName}")
  @LoginPermit(NeedLogin = true)
  public @ResponseBody Map<String, Object> profile(@PathVariable("userName") String userName,
      HttpSession session) {
    Map<String, Object> json = new HashMap<>();
    try {
      UserDto currentUser = (UserDto) session.getAttribute("currentUser");

      if (currentUser.getType() != AuthenticationType.ADMIN.ordinal()) {
        if (!Objects.equals(currentUser.getUserName(), userName)) {
          throw new AppException("You can only view your information.");
        }
      }
      UserEditorDto userEditorDto = userService.getUserEditorDtoByUserName(userName);
      if (userEditorDto == null) {
        throw new AppException("No such user.");
      }
      json.put("user", userEditorDto);
      json.put("result", "success");
    } catch (AppException e) {
      json.put("result", "error");
      json.put("error_msg", e.getMessage());
    }
    return json;
  }

  @RequestMapping("resetPassword")
  @LoginPermit(NeedLogin = false)
  public @ResponseBody Map<String, Object> resetPassword(
      @RequestBody @Valid UserActivateDto userActivateDto,
      BindingResult validateResult) {
    Map<String, Object> json = new HashMap<>();
    if (validateResult.hasErrors()) {
      json.put("result", "field_error");
      json.put("field", validateResult.getFieldErrors());
    } else {
      try {
        if (userActivateDto.getPassword() == null) {
          throw new FieldException("password", "Please enter your password.");
        }
        if (userActivateDto.getPasswordRepeat() == null) {
          throw new FieldException("passwordRepeat", "Please repeat your password.");
        }
        if (!Objects.equals(userActivateDto.getPassword(), userActivateDto.getPasswordRepeat())) {
          throw new FieldException("passwordRepeat", "Password do not match.");
        }
        UserDto userDto = userService.getUserDtoByUserName(userActivateDto.getUserName());
        if (userDto == null) {
          throw new FieldException("userName", "No such user.");
        }
        UserSerialKeyDto userSerialKeyDto = userSerialKeyService.findUserSerialKeyDtoByUserId(
            userDto.getUserId());
        if (userSerialKeyDto == null ||
            new Date().getTime() - userSerialKeyDto.getTime().getTime() > 1800000) {
          throw new FieldException("serialKey",
              "Serial Key exceed time limit! Please regenerate a new key.");
        }
        if (!Objects.equals(StringUtil.encodeSHA1(userSerialKeyDto.getSerialKey()),
            userActivateDto.getSerialKey())) {
          throw new FieldException("serialKey", "Serial Key is wrong or has been used!");
        }

        userDto.setPassword(userActivateDto.getPassword());
        userService.updateUser(userDto);

        userSerialKeyDto.setSerialKey("This key has been used.");
        userSerialKeyService.updateUserSerialKey(userSerialKeyDto);
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
