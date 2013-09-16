package cn.edu.uestc.acmicpc.oj.controller.user;

import cn.edu.uestc.acmicpc.db.dto.impl.UserDTO;
import cn.edu.uestc.acmicpc.ioc.service.UserServiceAware;
import cn.edu.uestc.acmicpc.oj.controller.base.BaseController;
import cn.edu.uestc.acmicpc.oj.service.iface.UserService;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * Description TODO
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController implements UserServiceAware {

  @RequestMapping("login")
  public @ResponseBody
  Map<String, Object> toLogin(@RequestBody @Valid UserDTO userDTO,
                              BindingResult result){
    System.out.println(userDTO.getPassword() + "|" + userDTO.getUserName());
    if (result.hasErrors()) {
      json.put("result", "error");
      json.put("filed", result.getFieldErrors());
    } else
      json.put("result", "ok");
    return json;
  }

  @Autowired
  private UserService userService;
  @Override
  public void setUserService(UserService userService) {
    this.userService = userService;
  }
}
