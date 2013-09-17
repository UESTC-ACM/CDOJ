package cn.edu.uestc.acmicpc.oj.controller.user;

import cn.edu.uestc.acmicpc.db.dto.impl.UserDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.UserLoginDTO;
import cn.edu.uestc.acmicpc.ioc.service.UserServiceAware;
import cn.edu.uestc.acmicpc.oj.controller.base.BaseController;
import cn.edu.uestc.acmicpc.oj.service.iface.UserService;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Description TODO
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController implements UserServiceAware {

  /**
   * Login controller, TODO need test to check weather validation works.
   *
   * @param userLoginDTO User DTO
   * @param validateResult Validation result
   * @return
   * <ul>
   * <li>For success: {"result":"ok"}</li>
   * <li>For error: {"result":"error", "error_msg":<strong>error message</strong>}</li>
   * <li>For validation error: {"result":"field_error","field":<strong>Field errors</strong>}</li>
   * </ul>
   */
  @RequestMapping("login")
  @LoginPermit(NeedLogin = false)
  public @ResponseBody
  Map<String, Object> toLogin(HttpSession session,
                              @RequestBody @Valid UserLoginDTO userLoginDTO,
                              BindingResult validateResult){
    Map<String, Object> json = new HashMap<>();
    if (validateResult.hasErrors()) {
      json.put("result", "field_error");
      json.put("field", validateResult.getFieldErrors());
    } else {
      try {
        UserDTO userDTO = userService.login(userLoginDTO);
        if (userDTO != null) {
          session.setAttribute("currentUser", userDTO);
          json.put("result", "success");
        } else {
          validateResult.addError(new FieldError("password", "password", "User or password is wrong, please try again"));
          json.put("result", "field_error");
          json.put("field", validateResult.getFieldErrors());
        }
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
