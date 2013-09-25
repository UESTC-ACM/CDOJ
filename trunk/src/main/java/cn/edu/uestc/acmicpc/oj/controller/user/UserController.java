package cn.edu.uestc.acmicpc.oj.controller.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import cn.edu.uestc.acmicpc.db.condition.impl.UserCondition;
import cn.edu.uestc.acmicpc.db.view.impl.UserView;
import cn.edu.uestc.acmicpc.oj.view.PageInfo;
import cn.edu.uestc.acmicpc.service.iface.GlobalService;
import cn.edu.uestc.acmicpc.util.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.uestc.acmicpc.db.dto.impl.UserDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.UserLoginDTO;
import cn.edu.uestc.acmicpc.oj.controller.base.BaseController;
import cn.edu.uestc.acmicpc.oj.service.iface.UserService;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.FieldException;

/**
 * User controller
 * <ul>
 *   <li><strong>/user/login</strong> Upload user's name and password in json, and do login operation on server</li>
 *   <li><strong>/user/logout</strong> Logout current user</li>
 *   <li><strong>/user/register</strong> Upload user's information and generate a new user</li>
 *   <li><strong>/user/sendSerialKey/{userName}</strong></li>
 *   <li><strong>/user/active/{userName}/{serialKey}</strong></li> TODO
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

  @Autowired
  public UserController(UserService userService,
                        GlobalService globalService) {
    this.userService = userService;
    this.globalService = globalService;
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
        UserDTO userDTO = userService.login(userLoginDTO);
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
   * @param userDTO User DTO
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
      @RequestBody @Valid UserDTO userDTO,
      BindingResult validateResult) {
    Map<String, Object> json = new HashMap<>();
    if (validateResult.hasErrors()) {
      json.put("result", "field_error");
      json.put("field", validateResult.getFieldErrors());
    } else {
      try {
        userService.register(userDTO);
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
    model.put("departmentList", globalService.getDepartmentList());
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
      PageInfo pageInfo = buildPageInfo(count, userCondition.getCurrentPage(), Global.RECORD_PER_PAGE, "", null);
      List<UserView> userViewList = userService.search(userCondition, pageInfo);

      json.put("pageInfo", pageInfo.getHtmlString());
      json.put("result", "success");
      json.put("userList", userViewList);
    }  catch (AppException e) {
      json.put("result", "error");
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
      UserView userView = userService.getUserViewByUserName(userName);
      model.put("departmentList", globalService.getDepartmentList());
      model.put("targetUser", userView);
    } catch (AppException e) {
      return "error/404";
    }
    return "user/userCenter";
  }

  /**
   * Update user information
   * @param session session
   * @param userDTO form information
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
                           @RequestBody @Valid UserDTO userDTO,
                           BindingResult validateResult) {
    Map<String, Object> json = new HashMap<>();
    if (validateResult.hasErrors()) {
      json.put("result", "field_error");
      json.put("field", validateResult.getFieldErrors());
    } else {
      try {
        userService.edit(userDTO, (UserDTO)session.getAttribute("currentUser"));
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
   * For success: {"result":"ok", "problemStatus":<strong>ProblemStatus object</strong>,
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
      Map<Integer, Global.AuthorStatusType> status = userService.getUserProblemStatus(userName);
      json.put("status", status);
      json.put("result", "success");
    } catch (AppException e) {
      json.put("result", "error");
      json.put("error_msg", e.getMessage());
    }
    return json;
  }

  @RequestMapping("sendSerialKey/{userName}")
  @LoginPermit(NeedLogin = false)
  public @ResponseBody
  Map<String, Object> sendSerialKey(@PathVariable("userName") String userName) {
    Map<String, Object> json = new HashMap<>();
    try {
      if (userService.sendSerialKey(userName))
        json.put("result", "success");
      else
        json.put("result", "failed");
    } catch (AppException e) {
      json.put("result", "error");
      json.put("error_msg", e.getMessage());
    }
    return json;
  }

  @RequestMapping("active/{userName}/{serialKey}")
  @LoginPermit(NeedLogin = false)
  public String activate(@PathVariable("userName") String userName,
                         @PathVariable("serialKey") String serialKey,
                         ModelMap model) {
    model.addAttribute("userName", userName);
    model.addAttribute("serialKey", serialKey);
    return "user/activate";
  }

  private UserService userService;
  private GlobalService globalService;
}
