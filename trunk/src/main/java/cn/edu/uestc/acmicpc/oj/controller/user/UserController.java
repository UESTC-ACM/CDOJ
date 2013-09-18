package cn.edu.uestc.acmicpc.oj.controller.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.uestc.acmicpc.db.dto.impl.UserDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.UserLoginDTO;
import cn.edu.uestc.acmicpc.ioc.service.UserServiceAware;
import cn.edu.uestc.acmicpc.oj.controller.base.BaseController;
import cn.edu.uestc.acmicpc.oj.service.iface.UserService;
import cn.edu.uestc.acmicpc.util.ObjectUtil;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Description TODO
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController implements UserServiceAware {

  private static final Logger LOGGER = LogManager.getLogger(UserController.class);

  /**
   * Login controller, TODO need test to check weather validation works.
   *
   * @param userLoginDTO User DTO
   * @param validateResult Validation result
   * @return <ul>
   *         <li>For success: {"result":"ok"}</li>
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
    LOGGER.debug("call toLogin successfully");
    LOGGER.debug(ObjectUtil.toString(userLoginDTO));
    Map<String, Object> json = new HashMap<>();
    if (validateResult.hasErrors()) {
      json.put("result", "field_error");
      json.put("field", validateResult.getFieldErrors());
    } else {
      try {
        UserDTO userDTO = userService.login(userLoginDTO);
        if (userDTO != null) {
          LOGGER.debug("set attribute");
          session.setAttribute("currentUser", userDTO);
          json.put("result", "success");
        } else {
          validateResult.addError(new FieldError("password", "password",
              "User or password is wrong, please try again"));
          json.put("result", "field_error");
          json.put("field", validateResult.getFieldErrors());
        }
      } catch (AppException e) {
        json.put("result", "error");
        json.put("error_msg", e.getMessage());
      }
    }
    LOGGER.debug(json);
    return json;
  }

  @Autowired
  private UserService userService;

  @Override
  public void setUserService(UserService userService) {
    System.err.println(userService.getClass());
    this.userService = userService;
  }
}
