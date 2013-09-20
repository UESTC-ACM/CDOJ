package cn.edu.uestc.acmicpc.oj.controller.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.uestc.acmicpc.db.dto.impl.UserDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.UserLoginDTO;
import cn.edu.uestc.acmicpc.ioc.service.UserServiceAware;
import cn.edu.uestc.acmicpc.oj.controller.base.BaseController;
import cn.edu.uestc.acmicpc.oj.service.iface.UserService;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.FieldException;

/**
 * Description TODO
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController implements UserServiceAware {

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
  Map<String, Object> toLogin(HttpSession session,
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
        putFieldErrosIntoBindingResult(e, validateResult);
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
  Map<String, Object> toLogout(HttpSession session) {
    Map<String, Object> json = new HashMap<>();
    session.removeAttribute("currentUser");
    json.put("result", "success");
    return json;
  }

  @RequestMapping("register")
  @LoginPermit(NeedLogin = false)
  public @ResponseBody
  Map<String, Object> toRegister(HttpSession session,
      @RequestBody @Valid UserDTO userDTO,
      BindingResult validateResult) {
    Map<String, Object> json = new HashMap<>();
    if (validateResult.hasErrors()) {
      json.put("result", "field_error");
      json.put("field", validateResult.getFieldErrors());
    } else {
      try {
        userService.register(userDTO);
        json.put("result", "success");
      } catch (FieldException e) {
        putFieldErrosIntoBindingResult(e, validateResult);
        json.put("result", "field_error");
        json.put("field", validateResult.getFieldErrors());
      } catch (AppException e) {
        json.put("result", "error");
        json.put("error_msg", e.getMessage());
      }
    }
    return json;
  }

  @Autowired
  private UserService userService;

  @Override
  public void setUserService(UserService userService) {
    this.userService = userService;
  }
}
