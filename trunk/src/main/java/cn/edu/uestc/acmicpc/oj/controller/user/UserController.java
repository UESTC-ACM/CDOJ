package cn.edu.uestc.acmicpc.oj.controller.user;

import cn.edu.uestc.acmicpc.ioc.service.UserServiceAware;
import cn.edu.uestc.acmicpc.oj.controller.base.BaseController;
import cn.edu.uestc.acmicpc.oj.service.iface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
  public @ResponseBody Map<String, Object> toLogin(@RequestParam String userName, @RequestParam String password) {
    System.out.println(userName);
    System.out.println(password);

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
